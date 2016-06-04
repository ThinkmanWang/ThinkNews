package com.thinkman.thinkutils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.util.ArrayList;

/**
 * Created by yhf on 2015/6/24.
 */
public class FragmentStackView extends ViewGroup {
    private static final String TAG = "FragmentStackView";
    static final boolean DEBUG = true;

    private static final int DEFAULT_SCRIM_COLOR = 0x77000000;

    private static final Interpolator sQuinticInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    private static final int MAX_SETTLE_DURATION = 600;

    public static final int STATE_IDLE = 0;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_SETTLING = 2;

    private int mDragState = STATE_IDLE;
    private final int mMaxVelocity;
    private final int mMinVelocity;
    private VelocityTracker mVelocityTracker;
    private final int mTouchSlop;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private float mLastMotionX;
    private boolean mIsUnableToDrag;
    private int mTouchMarginSize;

    private static final int INVALID_POINTER = -1;
    private int mActivePointerId = INVALID_POINTER;

    //遮罩
    private int mScrimColor = DEFAULT_SCRIM_COLOR;
    private Paint mScrimPaint = new Paint();

    //阴影
    private Drawable mShadow;

    /** 当前激活的上层View */
    private View aboveActiveView;
    /** 当前激活的下层View */
    private View behindActiveView;
    private float behindOffsetScale = 0.33f;

    private ViewScroller mViewScroller = new ViewScroller();
    //是否处于用户不可操作的动画中
    private boolean forcedAnimation;

    private FragmentManager fragmentManager;
    private ArrayList<Fragment> fragmentStack = new ArrayList<>();
    //当前behindView在栈中的index 默认-1为栈顶的下一个
    private int behindViewIndex = -1;

    public FragmentStackView(Context context) {
        this(context, null);
    }

    public FragmentStackView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FragmentStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();
        mMaxVelocity = vc.getScaledMaximumFlingVelocity();
        mMinVelocity = vc.getScaledMinimumFlingVelocity();
        final float density = getResources().getDisplayMetrics().density;
        mTouchMarginSize = (int) (20 * density);//20dp

        setWillNotDraw(true);
    }

    public int getStackSize() {
        return fragmentStack.size();
    }

    public void push(Fragment fragment, String tag) {
        push(fragment, tag, true);
    }

    /**
     * Fragment入栈
     * @param fragment 目标Fragment
     * @param tag tag
     * @param animate 是否动画过程
     */
    public void push(Fragment fragment, String tag, boolean animate) {
        if(isInAnimation()) {
            Log.w(TAG, "push isInAnimation");
            return;
        }
        fragmentStack.add(fragment);
        fragmentManager.beginTransaction()
                .add(getId(), fragment, tag)
                .commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
        if(animate) {
            forcedAnimation = true;
            setupTopActiveView();
            setActiveViewOffsetRatio(1);
            smoothScroll(false, 0);
        } else {
            dispatchOnPush();
        }
    }

    public void pop() {
        pop(true);
    }

    public void pop(boolean animate) {
        if(isInAnimation()) {
            Log.w(TAG, "push isInAnimation");
            return;
        }
        int size = fragmentStack.size();
        if(size == 0) {
            return;
        }
        if(animate) {
            forcedAnimation = true;
            setupTopActiveView();
            setActiveViewOffsetRatio(0);
            smoothScroll(true, 0);
        } else {
            dispatchOnPop();
        }
    }

    public void popByIndex(int index, boolean include, boolean animate) {
        if(isInAnimation()) {
            Log.w(TAG, "push isInAnimation");
            return;
        }
        int size = fragmentStack.size();
        if(size == 0) {
            return;
        }
        if(index < 0 || index >= size) {
            Log.e(TAG, "popByIndex invalid index");
            return;
        }
        int newBehindViewIndex = include ? index - 1 : index;
        if(newBehindViewIndex >= size - 1) {
            return;
        }
        behindViewIndex = newBehindViewIndex;
        if(animate) {
            forcedAnimation = true;
            setupTopActiveView();
            setActiveViewOffsetRatio(0);
            smoothScroll(true, 0);
        } else {
            dispatchOnPop();
        }
    }

    /**
     * 获取tag对应的fragment在栈中的index
     * @param tag fragment的tag
     * @return index -1不存在
     */
    public int getIndexByTag(String tag) {
        if(tag == null) {
            return -1;
        }
        for(int i = 0, len = fragmentStack.size(); i < len; ++i) {
            if(tag.equals(fragmentStack.get(i).getTag())) {
                return i;
            }
        }
        return -1;
    }

    public boolean isInAnimation() {
        Log.i(TAG, "isInAnimation forcedAnimation=" + forcedAnimation + " mDragState=" + mDragState);
        // TODO: 2015/6/30
        return forcedAnimation || mDragState != STATE_IDLE;
    }

    /**
     * 设置阴影
     */
    public void setShadow(Drawable shadowDrawable) {
        mShadow = shadowDrawable;
        invalidate();
    }

    /**
     * 设置阴影
     */
    public void setShadow(@DrawableRes int resId) {
        setShadow(ResourcesCompat.getDrawable(getResources(), resId, null));
    }

    /**
     * 处理push结束
     */
    private void dispatchOnPush() {
        int size = fragmentStack.size();
        if(size > 1) {
            Fragment behindFragment = fragmentStack.get(size - 2);
            if(behindFragment.isHidden()) {
                behindActiveView.setVisibility(View.GONE);
            } else {
                try {
                    fragmentManager.beginTransaction()
                            .hide(behindFragment)
                            .commitAllowingStateLoss();
                    fragmentManager.executePendingTransactions();
                } catch(Exception ig) {
                    Log.e(TAG, "dispatchOnPush", ig);
                }
            }
        }
        resetActiveView();
        forcedAnimation = false;
        ensureTopViewOffset();
    }

    /**
     * 处理pop结束
     */
    private void dispatchOnPop() {
        //Log.i(TAG, "dispatchOnPop1 " + getChildCount());
        int size = fragmentStack.size();
        if(size > 0) {
            int endIndex = behindViewIndex;
            if(endIndex < 0) {
                if(size > 1) {
                    endIndex = size - 2;
                } else {
                    endIndex = -1;
                }
            } else if(endIndex >= size - 1) {
                throw new IllegalStateException("behindViewIndex >= size - 1");
            }
            FragmentTransaction ft = fragmentManager.beginTransaction();
            try {
                for(int i = size - 1; i > endIndex; --i) {
                    ft.remove(fragmentStack.remove(i));
                }
                if(endIndex >= 0) {
                    ft.show(fragmentStack.get(endIndex));
                }
                ft.commitAllowingStateLoss();
                fragmentManager.executePendingTransactions();
            } catch(Exception ig) {
                Log.e(TAG, "dispatchOnPop", ig);
            }
        }
        //Log.i(TAG, "dispatchOnPop2 " + getChildCount());
        resetActiveView();
        forcedAnimation = false;
        ensureTopViewOffset();
    }

    /**
     * 设置栈顶的两个View为activeView
     */
    private void setupTopActiveView() {
        aboveActiveView = null;
        behindActiveView = null;
        int childCount = getChildCount();
        if(childCount > 0) {
            aboveActiveView = getChildAt(childCount - 1);
            if(childCount > 1) {
                if(behindViewIndex < 0) {
                    behindActiveView = getChildAt(childCount - 2);
                } else {
                    if(behindViewIndex >= childCount - 1) {
                        throw new IllegalStateException("behindViewIndex >= childCount - 1");
                    }
                    behindActiveView = getChildAt(behindViewIndex);
                }
                if(behindActiveView.getVisibility() != View.VISIBLE) {
                    behindActiveView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void ensureActiveView() {
        if(aboveActiveView == null) {
            setupTopActiveView();
            setActiveViewOffsetRatio(0);
        }
    }

    private void resetActiveView() {
        aboveActiveView = null;
        behindActiveView = null;
        behindViewIndex = -1;
    }

    //设置当前的偏移比率
    private void setActiveViewOffsetRatio(float aboveOffsetRatio) {
        if(aboveOffsetRatio < 0) {
            aboveOffsetRatio = 0;
        } else if(aboveOffsetRatio > 1) {
            aboveOffsetRatio = 1;
        }
        int newLeft = (int) (aboveOffsetRatio * getWidth());
        int dx = newLeft - aboveActiveView.getLeft();
        aboveActiveView.offsetLeftAndRight(dx);
        ((LayoutParams) aboveActiveView.getLayoutParams()).offsetRatio = aboveOffsetRatio;
        setBehindActiveViewOffset(aboveOffsetRatio);
        invalidate();
    }

    //偏移当前激活的View
    private void offsetActiveView(int dx) {
        if(dx == 0) {
            return;
        }
        aboveActiveView.offsetLeftAndRight(dx);
        float aboveOffsetRatio = (float) aboveActiveView.getLeft() / getWidth();
        ((LayoutParams) aboveActiveView.getLayoutParams()).offsetRatio = aboveOffsetRatio;
        setBehindActiveViewOffset(aboveOffsetRatio);
        invalidate();
    }

    //设置behindActiveView的offset 与aboveActiveView联动
    private void setBehindActiveViewOffset(float aboveOffsetRatio) {
        if(behindActiveView != null) {
            float behindOffsetRatio = (aboveOffsetRatio - 1) * behindOffsetScale;
            ((LayoutParams) behindActiveView.getLayoutParams()).offsetRatio = behindOffsetRatio;
            int newBehindLeft = (int) (behindOffsetRatio * getWidth());
            behindActiveView.offsetLeftAndRight(newBehindLeft - behindActiveView.getLeft());
        }
    }

    /**
     * 确保栈顶View的位置
     */
    private void ensureTopViewOffset() {
        int childCount = getChildCount();
        if(childCount == 0) {
            return;
        }
        View topView = getChildAt(childCount - 1);
        ((LayoutParams) topView.getLayoutParams()).offsetRatio = 0;
        int left = topView.getLeft();
        if(left != 0) {
            topView.offsetLeftAndRight(-left);
            invalidate();
        }
    }

    /**
     * 平滑滚动当前激活的view
     * @param open true: pop上层, false: 恢复或push上层
     * @param velocity 初速度
     */
    private void smoothScroll(boolean open, int velocity) {
        int startX = aboveActiveView.getLeft();
        int width = getWidth();
        int endX = open ? width : 0;
        float distanceRatio = Math.abs((float) (endX - startX) / width);
        int duration;
        if(velocity != 0) {
            final int halfWidth = width / 2;
            float f = distanceRatio - 0.5f;
            f *= 0.3f * Math.PI / 2.0f;
            f = (float) Math.sin(f);
            float distance = halfWidth + halfWidth * f;
            duration = 6 * Math.round(1000 * Math.abs(distance / velocity));
        } else {
            duration = (int) ((distanceRatio + 1) * 200);
        }
        if(DEBUG) Log.i(TAG, "duration=" + duration);
        duration = Math.min(duration, MAX_SETTLE_DURATION);
        mViewScroller.startScroll(startX, endX - startX, duration);
    }

    private void setDragState(int state) {
        if(mDragState == state) {
            return;
        }
        mDragState = state;
        if(state == STATE_IDLE && aboveActiveView != null) {
            LayoutParams lp = (LayoutParams) aboveActiveView.getLayoutParams();
            if(lp.offsetRatio > 0.5f) {
                dispatchOnPop();
            } else {
                dispatchOnPush();
            }
        } else if(state == STATE_DRAGGING) {
            ensureActiveView();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(DEBUG) Log.i(TAG, "onMeasure() called with " + "widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            //FragmentStackView必须固定宽高
            if(isInEditMode()) {
                //防止在界面编辑器中报错
                if(widthMode == MeasureSpec.AT_MOST) {
                    widthMode = MeasureSpec.EXACTLY;
                } else if(widthMode == MeasureSpec.UNSPECIFIED) {
                    widthMode = MeasureSpec.EXACTLY;
                    widthSize = 300;
                }
                if(heightMode == MeasureSpec.AT_MOST) {
                    heightMode = MeasureSpec.EXACTLY;
                } else if(heightMode == MeasureSpec.UNSPECIFIED) {
                    heightMode = MeasureSpec.EXACTLY;
                    heightSize = 300;
                }
            } else {
                throw new IllegalArgumentException("FragmentStackView must be measured with MeasureSpec.EXACTLY.");
            }
        }

        setMeasuredDimension(widthSize, heightSize);

        //测量子View
        final int childCount = getChildCount();
        for(int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);

            if(child.getVisibility() == GONE) {
                continue;
            }

            final LayoutParams lp = (LayoutParams) child.getLayoutParams();

            //不考虑子View lp.width,必须填充除margin外的父View区域
            final int contentWidthSpec = MeasureSpec.makeMeasureSpec(widthSize - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY);
            final int contentHeightSpec = MeasureSpec.makeMeasureSpec(heightSize - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY);
            child.measure(contentWidthSpec, contentHeightSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(DEBUG) Log.i(TAG, "onLayout() called with " + "changed = [" + changed + "], l = [" + l + "], t = [" + t + "], r = [" + r + "], b = [" + b + "]");
        final int width = r - l;
        final int childCount = getChildCount();
        for(int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);

            if(child.getVisibility() == GONE) {
                continue;
            }

            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int childLeft = Math.round(lp.offsetRatio * width);
            child.layout(childLeft, lp.topMargin, childLeft + child.getMeasuredWidth(), lp.topMargin + child.getMeasuredHeight());
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean result = super.drawChild(canvas, child, drawingTime);
        if(aboveActiveView != null) {
            float opacity = 1 - ((LayoutParams) aboveActiveView.getLayoutParams()).offsetRatio;
            if(opacity > 0) {
                if (child == behindActiveView) {
                    //在behindActiveView的上面盖上遮罩
                    final int baseAlpha = (mScrimColor & 0xff000000) >>> 24;
                    final int imag = (int) (baseAlpha * opacity);
                    final int color = imag << 24 | (mScrimColor & 0xffffff);
                    mScrimPaint.setColor(color);
                    canvas.drawRect(0, 0, getWidth(), getHeight(), mScrimPaint);
                }
                if(mShadow != null && child == aboveActiveView) {
                    //在aboveActiveView边缘画上阴影
                    int childLeft = child.getLeft();
                    mShadow.setBounds(childLeft - mShadow.getIntrinsicWidth(), child.getTop(), childLeft, child.getBottom());
                    mShadow.setAlpha((int) (0xff * opacity));
                    mShadow.draw(canvas);
                }
            }
        }
        return result;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(forcedAnimation) {
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(!canDrag()) {
            return false;
        }

        final int action = MotionEventCompat.getActionMasked(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            endTouch();
            return false;
        }

        if(action != MotionEvent.ACTION_DOWN && mIsUnableToDrag) {
            return false;
        }

        if(mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        switch(action) {
            case MotionEvent.ACTION_DOWN: {
                float initialMotionX = ev.getX();
                if(!isInMargin(initialMotionX)) {
                    mIsUnableToDrag = true;
                    break;
                }
                mLastMotionX = mInitialMotionX = initialMotionX;
                mInitialMotionY = ev.getY();
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsUnableToDrag = false;
                if(mDragState == STATE_SETTLING) {
                    mViewScroller.stop();
                    setDragState(STATE_DRAGGING);
                    requestParentDisallowInterceptTouchEvent(true);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int activeIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if(activeIndex < 0) {
                    if(DEBUG) Log.w(TAG, "ACTION_MOVE pointerIndex < 0");
                    break;
                }
                float x = MotionEventCompat.getX(ev, activeIndex);
                float y = MotionEventCompat.getY(ev, activeIndex);
                float dx = x - mInitialMotionX;
                float absDx = Math.abs(dx);
                float absDy = Math.abs(y - mInitialMotionY);
                if(dx > mTouchSlop && absDx > absDy * 2) {
                    mLastMotionX = dx > 0 ? mInitialMotionX + mTouchSlop : mInitialMotionX - mTouchSlop;
                    setDragState(STATE_DRAGGING);
                    requestParentDisallowInterceptTouchEvent(true);
                } else if(absDy > mTouchSlop) {
                    mIsUnableToDrag = true;
                }
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                onSecondaryPointerDown(ev);
                break;
            }
            case MotionEventCompat.ACTION_POINTER_UP: {
                onSecondaryPointerUp(ev);
                break;
            }
        }
        return mDragState == STATE_DRAGGING;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(mIsUnableToDrag || !canDrag()) {
            return false;
        }
        if(mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        final int action = MotionEventCompat.getActionMasked(ev);
        switch(action) {
            case MotionEvent.ACTION_DOWN: {
                mLastMotionX = mInitialMotionX = ev.getX();
                mInitialMotionY = ev.getY();
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int activeIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if(activeIndex < 0) {
                    if(DEBUG) Log.w(TAG, "ACTION_MOVE pointerIndex < 0");
                    break;
                }
                float x = MotionEventCompat.getX(ev, activeIndex);
                if(mDragState != STATE_DRAGGING) {
                    float dx = x - mInitialMotionX;
                    if(dx > mTouchSlop) {
                        mLastMotionX = dx > 0 ? mInitialMotionX + mTouchSlop : mInitialMotionX - mTouchSlop;
                        setDragState(STATE_DRAGGING);
                        requestParentDisallowInterceptTouchEvent(true);
                    }
                }
                if(mDragState == STATE_DRAGGING) {
                    performDrag(x);
                }
                mLastMotionX = x;
                break;
            }
            case MotionEvent.ACTION_UP: {
                if(mDragState == STATE_DRAGGING) {
                    VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                    int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, mActivePointerId);
                    if(Math.abs(initialVelocity) > mMinVelocity) {
                        smoothScroll(initialVelocity > 0, initialVelocity);
                    } else {
                        boolean open = ((LayoutParams) aboveActiveView.getLayoutParams()).offsetRatio > 0.5f;
                        smoothScroll(open, 0);
                    }
                }
                endTouch();
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                if(mDragState == STATE_DRAGGING) {
                    smoothScroll(false, 0);
                }
                endTouch();
                break;
            }
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                onSecondaryPointerDown(ev);
                break;
            }
            case MotionEventCompat.ACTION_POINTER_UP: {
                onSecondaryPointerUp(ev);
                break;
            }
        }
        return true;
    }

    //切换mActivePointerId
    private void onSecondaryPointerDown(MotionEvent ev) {
        int actionIndex = MotionEventCompat.getActionIndex(ev);
        mLastMotionX = mInitialMotionX = MotionEventCompat.getX(ev, actionIndex);
        mInitialMotionY = MotionEventCompat.getY(ev, actionIndex);
        mActivePointerId = MotionEventCompat.getPointerId(ev, actionIndex);
    }

    //切换mActivePointerId
    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        if (MotionEventCompat.getPointerId(ev, pointerIndex) == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionX = mInitialMotionX = MotionEventCompat.getX(ev, newPointerIndex);
            mInitialMotionY = MotionEventCompat.getY(ev, newPointerIndex);
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    private void endTouch() {
        mActivePointerId = INVALID_POINTER;
        mIsUnableToDrag = false;
        if(mVelocityTracker != null) {
            mVelocityTracker.clear();
        }
    }

    //x坐标是否处于边界
    private boolean isInMargin(float motionX) {
        return motionX < mTouchMarginSize;
    }

    private void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        final ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    private boolean canDrag() {
        return getChildCount() > 1;
    }

    /**
     * 执行drag
     */
    private void performDrag(float x) {
        float dx = x - mLastMotionX;
        if(dx == 0) {
            return;
        }

        float left = aboveActiveView.getLeft();
        float width = getWidth();
        float newX = left + dx;
        if(newX < 0) {
            dx = -left;
        } else if(newX > width) {
            dx = width - left;
        }
        if(dx == 0) {
            return;
        }
        offsetActiveView((int) dx);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if(checkLayoutParams(params)) {
            ((LayoutParams) params).offsetRatio = 0;
        }
        super.addView(child, index, params);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MarginLayoutParams ? new LayoutParams((MarginLayoutParams) p) : new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams && super.checkLayoutParams(p);
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public static class LayoutParams extends MarginLayoutParams {

        /**
         * 子View左侧相对于父View的偏移比例[-1, 1]
         * -1:左侧不可见
         * 0:一般状态
         * 1:右侧不可见
         */
        float offsetRatio;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    private class ViewScroller implements Runnable {

        private Scroller mScroller;
        private int lastX;

        public ViewScroller() {
            mScroller = new Scroller(getContext(), sQuinticInterpolator);
        }

        @Override
        public void run() {
            if(mScroller.computeScrollOffset()) {
                int x = mScroller.getCurrX();
                int dx = x - lastX;
                lastX = x;
                offsetActiveView(dx);
            }
            if(mScroller.isFinished()) {
                setDragState(STATE_IDLE);
            } else {
                postOnAnimation(this);
            }
        }

        public void startScroll(int startX, int dx, int duration) {
            setDragState(STATE_SETTLING);
            lastX = startX;
            mScroller.startScroll(startX, 0, dx, 0, duration);
            postOnAnimation(this);
        }

        public void stop() {
            removeCallbacks(this);
            mScroller.abortAnimation();
        }
    }

    public void debug() {
        Log.i(TAG, "fragmentStack:" + fragmentStack);
    }
}
