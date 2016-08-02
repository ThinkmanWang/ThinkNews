package com.thinkman.thinkutils.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by wangx on 2016/8/2.
 */
public class ThinkRecyclerViewScrollable extends ThinkRecyclerView {
    public ThinkRecyclerViewScrollable(Context context) {
        super(context);
    }


    public ThinkRecyclerViewScrollable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public ThinkRecyclerViewScrollable(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
