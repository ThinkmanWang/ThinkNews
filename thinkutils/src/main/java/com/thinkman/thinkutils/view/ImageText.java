package com.thinkman.thinkutils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkman.thinkutils.R;
import com.thinkman.thinkutils.commonutils.DisplayUtil;

/**
 * Created by wangx on 2016/5/26.
 */
public class ImageText extends LinearLayout {

    private Context mContext = null;
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
        mContext = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_image_text, this, true);
        m_ivIcon = (ImageView) contentView.findViewById(R.id.iv_icon);
        m_tvLabel = (TextView) contentView.findViewById(R.id.tv_label);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ImageText, defStyle, 0);

        float textSize = a.getDimension(R.styleable.ImageText_labelTextSize, getResources().getDimension(R.dimen.textsize_20));
        m_tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        int labelColor = a.getInt(R.styleable.ImageText_labelColor, getResources().getColor(R.color.text_dark_666));
        m_tvLabel.setTextColor(labelColor);

        if (a.hasValue(R.styleable.ImageText_labelText)) {
            String szLabel = a.getString(R.styleable.ImageText_labelText);
            m_tvLabel.setText(szLabel);
        }

        if (a.hasValue(R.styleable.ImageText_image)) {
            Drawable icon = a.getDrawable(R.styleable.ImageText_image);
            m_ivIcon.setImageDrawable(icon);
        }
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

    public void setLabelColor(int nResId) {
        m_tvLabel.setTextColor(mContext.getResources().getColor(nResId));
    }

    public ImageView getIcon() {
        return m_ivIcon;
    }

    public TextView getLabel() {
        return  m_tvLabel;
    }
}
