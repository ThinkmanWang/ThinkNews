package com.thinkman.thinkutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkman.thinkutils.R;

/**
 * Created by wangx on 2016/5/27.
 */
public class CommonMultiTextBar extends LinearLayout {

    private View contentView = null;
    private ImageView m_ivLeftIcon = null;
    private TextView m_tvLabel1 = null;
    private TextView m_tvLabel2 = null;
    private ImageView m_ivRightIcon = null;

    public CommonMultiTextBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CommonMultiTextBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CommonMultiTextBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_common_multi_text_bar, this, true);

        m_ivLeftIcon = (ImageView) contentView.findViewById(R.id.iv_left_icon);
        m_tvLabel1 = (TextView) contentView.findViewById(R.id.tv_label1);
        m_tvLabel2 = (TextView) contentView.findViewById(R.id.tv_label2);
        m_ivRightIcon = (ImageView) contentView.findViewById(R.id.iv_right_icon);
    }

    public void setLeftIcon(int nResId) {
        m_ivLeftIcon.setImageResource(nResId);
    }
    public void setRightIcon(int nResId) {
        m_ivRightIcon.setImageResource(nResId);
    }

    public void setLabel1(int nResId) {
        m_tvLabel1.setText(nResId);
    }

    public void setLabel1(String szLabel) {
        m_tvLabel1.setText(szLabel);
    }

    public void setLabel2(int nResId) {
        m_tvLabel2.setText(nResId);
    }

    public void setLabel2(String szLabel) {
        m_tvLabel2.setText(szLabel);
    }
}
