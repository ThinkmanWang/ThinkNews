package com.thinkman.thinkutils.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangx on 2016/8/2.
 */
public class ThinkRecyclerViewScrollable extends RecyclerView {

    private Context mContext = null;

    public ThinkRecyclerViewScrollable(Context context) {
        super(context);
        mContext = context;
    }


    public ThinkRecyclerViewScrollable(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }


    public ThinkRecyclerViewScrollable(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public void setAdapter(Adapter adapter) {
        // bail out if layout is frozen
        super.setAdapter(adapter);
    }

}
