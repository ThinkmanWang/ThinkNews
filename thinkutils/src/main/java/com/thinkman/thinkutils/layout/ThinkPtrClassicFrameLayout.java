package com.thinkman.thinkutils.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.thinkman.thinkutils.commonutils.DisplayUtil;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by wangx on 2016/7/26.
 */
public class ThinkPtrClassicFrameLayout extends PtrClassicFrameLayout {

    private Context mContext = null;

    public ThinkPtrClassicFrameLayout(Context context) {
        super(context);
        mContext = context;
    }

    public ThinkPtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ThinkPtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    private float xDistance, yDistance;
    private float xLast, yLast;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (xDistance > DisplayUtil.getScreenWidth(mContext)/5) {
                    return false;
                }
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (xDistance > DisplayUtil.getScreenWidth(mContext)/5) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
