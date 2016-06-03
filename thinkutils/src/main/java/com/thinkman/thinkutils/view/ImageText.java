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
 * Created by wangx on 2016/5/26.
 */
public class ImageText extends LinearLayout {

    private View contentView = null;
    private ImageView m_ivIcon = null;
    private TextView m_tvLabel = null;

    public ImageText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ImageText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ImageText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_circleimage_text, this, true);
        m_ivIcon = (ImageView) contentView.findViewById(R.id.iv_icon);
        m_tvLabel = (TextView) contentView.findViewById(R.id.tv_label);
    }

    public void setIcon(int nResId) {
        m_ivIcon.setImageResource(nResId);
    }

    public void setLabel(int nResId) {
        m_tvLabel.setText(nResId);
    }

    public void setLabel(String szLabel) {
        m_tvLabel.setText(szLabel);
    }
}
