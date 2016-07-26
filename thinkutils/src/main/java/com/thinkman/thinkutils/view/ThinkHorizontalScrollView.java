package com.thinkman.thinkutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by wangx on 2016/7/26.
 */
public class ThinkHorizontalScrollView extends HorizontalScrollView {

    private OnScrollChangeListener scrollViewListener = null;

    public ThinkHorizontalScrollView(Context context) {
        super(context);
    }

    public ThinkHorizontalScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ThinkHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(OnScrollChangeListener onScrollChangeListener) {
        this.scrollViewListener = onScrollChangeListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface OnScrollChangeListener {

        void onScrollChanged(ThinkHorizontalScrollView scrollView, int x, int y, int oldx, int oldy);

    }
}
