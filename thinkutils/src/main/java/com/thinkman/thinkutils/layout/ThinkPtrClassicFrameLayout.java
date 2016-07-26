package com.thinkman.thinkutils.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by wangx on 2016/7/26.
 */
public class ThinkPtrClassicFrameLayout extends PtrClassicFrameLayout {
    public ThinkPtrClassicFrameLayout(Context context) {
        super(context);
    }

    public ThinkPtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThinkPtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private float xDistance, yDistance;
    private float xLast, yLast;

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
                if (xDistance > yDistance ) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
