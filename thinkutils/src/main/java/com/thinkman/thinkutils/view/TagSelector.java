package com.thinkman.thinkutils.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.thinkman.thinkutils.R;
import com.thinkman.thinkutils.layout.FlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wangx on 2016/9/13.
 */
public class TagSelector extends FlowLayout {
    private Context mContext = null;
    private View mContentView = null;

    public TagSelector(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TagSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TagSelector(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;
    }

    public void setBackGround(Color nNormal, Color nSelected) {

    }

    public void setTextColor(Color nNormal, Color nSelected) {

    }

    HashMap<String, TextView> m_mapTags = new HashMap<>();
    HashMap<String, TextView> m_mapSelected = new HashMap<>();

    public void addTags(ArrayList<String> lstTag) {
        for (String szTag : lstTag) {
            addTag(szTag);
        }
    }

    public void addTag(String szTag) {
        if (m_mapTags.containsKey(szTag)) {
            return;
        }

        TextView tvTag = new TextView(mContext);
        tvTag.setText(szTag);
        tvTag.setTextColor(mContext.getResources().getColor(R.color.black));
        tvTag.setBackgroundColor(mContext.getResources().getColor(R.color.bg_grey));
        tvTag.setPadding(20, 20, 20, 20);
        tvTag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvTag = (TextView) v;
                if (m_mapSelected.containsKey(tvTag.getText().toString())) {
                    tvTag.setTextColor(mContext.getResources().getColor(R.color.black));
                    tvTag.setBackgroundColor(mContext.getResources().getColor(R.color.bg_grey));
                    m_mapSelected.remove(tvTag.getText().toString());
                } else {
                    tvTag.setTextColor(mContext.getResources().getColor(R.color.white));
                    tvTag.setBackgroundColor(mContext.getResources().getColor(R.color.blue_order));
                    m_mapSelected.put(tvTag.getText().toString(), tvTag);
                }

                if (m_onTagSelecteListener != null) {
                    m_onTagSelecteListener.onTagClicked(tvTag.getText().toString());
                }
            }
        });

        m_mapTags.put(szTag, tvTag);

        if (m_onTagSelecteListener != null) {
            m_onTagSelecteListener.onTagAdded(szTag);
        }

        addView(tvTag);
        invalidate();
    }

    public void removeTag(String szTag) {
        if (false == m_mapTags.containsKey(szTag)) {
            return;
        }

        this.removeView(m_mapTags.get(szTag));
        m_mapTags.remove(szTag);
        if (m_mapSelected.containsKey(szTag)) {
            m_mapSelected.remove(szTag);
        }

        invalidate();

        if (m_onTagSelecteListener != null) {
            m_onTagSelecteListener.onTagRemoved(szTag);
        }
    }

    public void clear() {
        m_mapTags.clear();
        m_mapSelected.clear();
    }

    private OnTagListener m_onTagSelecteListener = null;
    public void setOnTagSelecteListener(OnTagListener listener) {
        m_onTagSelecteListener = listener;
    }

    public interface OnTagListener {
        void onTagClicked(String szTag);
        void onTagAdded(String szTag);
        void onTagRemoved(String szTag);
    }

    public List<String> getAllTags() {
        ArrayList<String> lstTags = new ArrayList<>();

        for (String szTag : m_mapTags.keySet()) {
            lstTags.add(szTag);
        }

        return lstTags;
    }

    public List<String> geTagsSelected() {
        ArrayList<String> lstTags = new ArrayList<>();

        for (String szTag : m_mapSelected.keySet()) {
            lstTags.add(szTag);
        }

        return lstTags;
    }
}
