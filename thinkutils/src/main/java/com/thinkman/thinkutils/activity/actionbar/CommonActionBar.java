package com.thinkman.thinkutils.activity.actionbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import com.thinkman.thinkutils.R;

/**
 * Common action bar left + center + right
 */
public class CommonActionBar extends RelativeLayout implements View.OnClickListener {
    private AppCompatCheckedTextView mTitleCtv;
    private AppCompatCheckedTextView mLeftCtv;
    private AppCompatCheckedTextView mRightCtv;
    private BGATitlebarDelegate mDelegate;

    public CommonActionBar(Context context) {
        this(context, null);
    }

    public CommonActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_common_actionbar, this);

        initView();
        setListener();
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    protected void initView() {
        mLeftCtv = getViewById(R.id.ctv_bgatitlebar_left);
        mRightCtv = getViewById(R.id.ctv_bgatitlebar_right);
        mTitleCtv = getViewById(R.id.ctv_bgatitlebar_title);
    }

    protected void setListener() {
        mLeftCtv.setOnClickListener(this);
        mTitleCtv.setOnClickListener(this);
        mRightCtv.setOnClickListener(this);
    }

    public void setLeftCtvMaxWidth(int maxWidth) {
        mLeftCtv.setMaxWidth(maxWidth);
    }

    public void setRightCtvMaxWidth(int maxWidth) {
        mRightCtv.setMaxWidth(maxWidth);
    }

    public void setTitleCtvMaxWidth(int maxWidth) {
        mTitleCtv.setMaxWidth(maxWidth);
    }

    public void hiddenLeftCtv() {
        mLeftCtv.setVisibility(GONE);
    }

    public void showLeftCtv() {
        mLeftCtv.setVisibility(VISIBLE);
    }

    public void setLeftText(@StringRes int resid) {
        setLeftText(getResources().getString(resid));
    }

    public void setLeftText(CharSequence text) {
        mLeftCtv.setText(text);
        showLeftCtv();
    }

    public void setLeftDrawable(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mLeftCtv.setCompoundDrawables(drawable, null, null, null);
        showLeftCtv();
    }

    public void hiddenTitleCtv() {
        mTitleCtv.setVisibility(GONE);
    }

    public void showTitleCtv() {
        mTitleCtv.setVisibility(VISIBLE);
    }

    public void setTitleText(CharSequence text) {
        mTitleCtv.setText(text);
        showTitleCtv();
    }

    public void setTitleText(@StringRes int resid) {
        setTitleText(getResources().getString(resid));
    }

    public void setTitleDrawable(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mTitleCtv.setCompoundDrawables(null, null, drawable, null);
        showTitleCtv();
    }

    public void hiddenRightCtv() {
        mRightCtv.setVisibility(GONE);
    }

    public void showRightCtv() {
        mRightCtv.setVisibility(VISIBLE);
    }

    public void setRightText(CharSequence text) {
        mRightCtv.setText(text);
        showRightCtv();
    }

    public void setRightText(@StringRes int resid) {
        setRightText(getResources().getString(resid));
    }

    public void setRightDrawable(Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        mRightCtv.setCompoundDrawables(null, null, drawable, null);
        showRightCtv();
    }

    public void setLeftCtvChecked(boolean checked) {
        mLeftCtv.setChecked(checked);
    }

    public void setTitleCtvChecked(boolean checked) {
        mTitleCtv.setChecked(checked);
    }

    public void setRightCtvChecked(boolean checked) {
        mRightCtv.setChecked(checked);
    }

    public AppCompatCheckedTextView getLeftCtv() {
        return mLeftCtv;
    }

    public AppCompatCheckedTextView getRightCtv() {
        return mRightCtv;
    }

    public AppCompatCheckedTextView getTitleCtv() {
        return mTitleCtv;
    }

    @Override
    public void onClick(View v) {
        if (mDelegate != null) {
            int id = v.getId();
            if (id == R.id.ctv_bgatitlebar_left) {
                mDelegate.onClickLeftCtv();
            } else if (id == R.id.ctv_bgatitlebar_title) {
                mDelegate.onClickTitleCtv();
            } else if (id == R.id.ctv_bgatitlebar_right) {
                mDelegate.onClickRightCtv();
            }
        }
    }

    public void setDelegate(BGATitlebarDelegate delegate) {
        mDelegate = delegate;
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据实际业务重写相应地方法
     */
    public static class BGATitlebarDelegate {
        public void onClickLeftCtv() {
        }

        public void onClickTitleCtv() {
        }

        public void onClickRightCtv() {
        }
    }
}