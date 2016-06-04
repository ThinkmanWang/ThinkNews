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
 * Created by wangx on 2016/6/4.
 */
public class CommonTextBar extends LinearLayout {

    private Context mContext = null;
    private View contentView = null;

    private ImageView m_ivLeftIcon = null;
    private TextView m_tvLabel = null;
    private ImageView m_ivRightIcon = null;


    public CommonTextBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CommonTextBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CommonTextBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_common_text_bar, this, true);

        m_ivLeftIcon = (ImageView) contentView.findViewById(R.id.iv_left_icon);
        m_tvLabel = (TextView) contentView.findViewById(R.id.tv_label);
        m_ivRightIcon = (ImageView) contentView.findViewById(R.id.iv_right_icon);
    }

    public void setLeftIcon(int nResId) {
        m_ivLeftIcon.setImageResource(nResId);
    }
    public void setRightIcon(int nResId) {
        m_ivRightIcon.setImageResource(nResId);
    }

    public void setLabel1(int nResId) {
        m_tvLabel.setText(nResId);
    }

    public void setLabel1(String szLabel) {
        m_tvLabel.setText(szLabel);
    }

    public void setLabel1Color(int nResId) {
        m_tvLabel.setTextColor(mContext.getResources().getColor(nResId));
    }

}
