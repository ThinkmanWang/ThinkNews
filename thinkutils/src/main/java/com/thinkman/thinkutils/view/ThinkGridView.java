package com.thinkman.thinkutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by wangx on 2016/5/30.
 */
public class ThinkGridView extends GridView {
    public ThinkGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ThinkGridView(Context context) {
        super(context);
    }
    public ThinkGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}