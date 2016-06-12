package com.thinkman.thinkutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/1/19.
 */
public class ListViewScrollable extends ListView {
    public ListViewScrollable(Context context) {
        super(context);
    }

    public ListViewScrollable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewScrollable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
