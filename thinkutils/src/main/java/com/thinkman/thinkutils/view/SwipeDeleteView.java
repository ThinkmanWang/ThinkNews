package com.thinkman.thinkutils.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.thinkman.thinkutils.R;

/**
 * Created by wangx on 2016/7/26.
 */
public class SwipeDeleteView extends LinearLayout implements ThinkHorizontalScrollView.OnScrollChangeListener {

    View mContentView = null;
    View mChild = null;
    private Context mContext = null;

    public enum SwipeType {
        SWIPE_TO_LEFT, SWIPE_TO_RIGHT
    };

    private SwipeType m_nSwipeType = SwipeType.SWIPE_TO_LEFT;

    private ThinkHorizontalScrollView m_hsvScrollView = null;
    private LinearLayout m_llContent = null;
    private Button m_btnDelLeft = null;
    private Button m_btnRight = null;

    public SwipeDeleteView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SwipeDeleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SwipeDeleteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;
        mContentView = LayoutInflater.from(context).inflate(R.layout.layout_swipe_delete_view, this, true);

        m_hsvScrollView = (ThinkHorizontalScrollView) mContentView.findViewById(R.id.hsv_scroll_view);
        m_hsvScrollView.setScrollViewListener(this);

        m_llContent = (LinearLayout) mContentView.findViewById(R.id.ll_content);
        m_btnDelLeft = (Button) mContentView.findViewById(R.id.btn_del_left);
        m_btnRight = (Button) mContentView.findViewById(R.id.btn_del_right);
        m_btnDelLeft.setVisibility(GONE);
        m_btnRight.setVisibility(GONE);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        setView(child);
    }

    public void setView(View view) {
        setView(view, SwipeType.SWIPE_TO_LEFT);
    }

    public void setView(View view, SwipeType type) {
        mChild = view;
        m_llContent.addView(view);

        setSwipeType(type);
    }

    public void setSwipeType(SwipeType type) {
        m_nSwipeType = type;

        m_btnDelLeft.setVisibility(GONE);
        m_btnRight.setVisibility(GONE);
        if (SwipeType.SWIPE_TO_RIGHT == m_nSwipeType) {
            m_btnRight.setVisibility(GONE);
            m_btnDelLeft.setVisibility(VISIBLE);
        } else {
            m_btnDelLeft.setVisibility(GONE);
            m_btnRight.setVisibility(VISIBLE);
        }
    }

    public void onScrollChanged(ThinkHorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {

    }

    public void setOnDeleteListener(View.OnClickListener listener) {
        m_btnDelLeft.setOnClickListener(listener);
        m_btnRight.setOnClickListener(listener);
    }

}
