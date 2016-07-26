package com.thinkman.thinkutils.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.thinkman.thinkutils.R;

import java.util.HashSet;
import java.util.Set;

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
    private Button m_btnDelRight = null;
    private HashSet<View> m_setViews = new HashSet<View>();

    Handler mHandler = new Handler();

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

        m_hsvScrollView = new ThinkHorizontalScrollView(mContext);
        m_setViews.add(m_hsvScrollView);
        m_hsvScrollView.setScrollViewListener(this);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(m_hsvScrollView, params);

        m_llContent = new LinearLayout(mContext);
        m_setViews.add(m_llContent);
        m_hsvScrollView.addView(m_llContent, params);

        m_btnDelLeft = new Button(mContext);
        m_btnDelLeft.setText("删除");
        m_btnDelLeft.setGravity(Gravity.CENTER);
        m_btnDelLeft.setTextColor(mContext.getResources().getColor(R.color.black));
        m_setViews.add(m_btnDelLeft);
        m_btnDelLeft.setBackgroundColor(mContext.getResources().getColor(R.color.text_red));

        m_btnDelRight = new Button(mContext);
        m_btnDelRight.setText("删除");
        m_btnDelRight.setGravity(Gravity.CENTER);
        m_btnDelRight.setTextColor(mContext.getResources().getColor(R.color.black));
        m_setViews.add(m_btnDelRight);
        m_btnDelRight.setBackgroundColor(mContext.getResources().getColor(R.color.text_red));
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (m_setViews.contains(child)) {
            return;
        }

        setView(child);
    }

    public void setView(View view) {
        setView(view, null, SwipeType.SWIPE_TO_LEFT);
    }

    public void setView(View view, SwipeType type) {
        setView(view, null, type);
    }

    public void setView(View view, LayoutParams params) {
        setView(view, params, SwipeType.SWIPE_TO_LEFT);
    }

    public void setView(View view, LayoutParams params, SwipeType type) {
        mChild = view;
        LayoutParams _params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        m_llContent.removeAllViews();
        m_llContent.addView(m_btnDelLeft, _params);

        if(view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        view.setMinimumWidth(m_hsvScrollView.getMeasuredWidth());
        if (null == params) {
            LayoutParams __params = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
            m_llContent.addView(view, __params);
        } else {
            m_llContent.addView(view, params);
        }
        m_llContent.addView(m_btnDelRight, _params);

        setSwipeType(type);
    }

    public void setSwipeType(SwipeType type) {
        m_nSwipeType = type;

        m_btnDelLeft.setVisibility(GONE);
        m_btnDelRight.setVisibility(GONE);
        if (SwipeType.SWIPE_TO_RIGHT == m_nSwipeType) {
            m_btnDelRight.setVisibility(GONE);
            m_btnDelLeft.setVisibility(VISIBLE);
        } else {
            m_btnDelLeft.setVisibility(GONE);
            m_btnDelRight.setVisibility(VISIBLE);
        }
    }

    public void onScrollChanged(ThinkHorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SwipeType.SWIPE_TO_LEFT == m_nSwipeType) {
                    m_hsvScrollView.scrollTo(0, 0);
                } else {
                    m_hsvScrollView.scrollTo(m_btnDelLeft.getWidth(), 0);
                }
            }
        }, 3000);
    }

    public void setOnDeleteListener(View.OnClickListener listener) {
        m_btnDelLeft.setOnClickListener(listener);
        m_btnDelRight.setOnClickListener(listener);
    }

}
