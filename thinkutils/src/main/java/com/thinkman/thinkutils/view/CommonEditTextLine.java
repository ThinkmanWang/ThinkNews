package com.thinkman.thinkutils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkman.thinkutils.R;

/**
 * Created by wangx on 2016/6/4.
 */
public class CommonEditTextLine extends LinearLayout {

    private Context mContext = null;
    private View contentView = null;

    private TextView m_tvLabel = null;
    private EditText m_etContent = null;

    private boolean hasUnderBar = true;
    private boolean isPassword = true;
    private String labelText;
    private float labelTextSize;
    private int inputType;

    // Content View Elements
    private int labelColor;
    private String contentText;
    private float contentTextSize;
    private int contentColor;
    private int hintColor;
    private String hintText;

    public CommonEditTextLine(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CommonEditTextLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CommonEditTextLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_common_edittext_line, this, true);

        m_tvLabel = (TextView) contentView.findViewById(R.id.tv_label);
        m_etContent = (EditText) contentView.findViewById(R.id.et_content);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CommonEditTextLine, defStyle, 0);

        inputType = a.getInt(R.styleable.CommonEditTextLine_android_inputType, EditorInfo.TYPE_NULL);
        if (inputType != EditorInfo.TYPE_NULL) {
            m_etContent.setInputType(inputType);
        }

        if (a.hasValue(R.styleable.CommonEditTextLine_labelText)) {
            labelText = a.getString(R.styleable.CommonEditTextLine_labelText);
            m_tvLabel.setText(labelText);
        }
        if (a.hasValue(R.styleable.CommonEditTextLine_labelTextSize)) {
            labelTextSize = a.getDimension(R.styleable.CommonEditTextLine_labelTextSize, 12);
            m_tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelTextSize);
        }

        if (a.hasValue(R.styleable.CommonEditTextLine_labelColor)) {
            labelColor = a.getInt(R.styleable.CommonEditTextLine_labelColor, getResources().getColor(android.R.color.black));
            m_tvLabel.setTextColor(labelColor);
        }
        contentText = a.getString(R.styleable.CommonEditTextLine_contentText);
        m_etContent.setText(contentText);

        if (a.hasValue(R.styleable.CommonEditTextLine_contentTextSize)) {
            contentTextSize = a.getDimension(R.styleable.CommonEditTextLine_contentTextSize, 12);
            m_etContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
        }

        if (a.hasValue(R.styleable.CommonEditTextLine_contentColor)) {
            contentColor = a.getInt(R.styleable.CommonEditTextLine_contentColor, getResources().getColor(android.R.color.black));
            m_etContent.setTextColor(contentColor);
        }
        if (a.hasValue(R.styleable.CommonEditTextLine_hintColor)) {
            hintColor = a.getColor(R.styleable.CommonEditTextLine_hintColor, getResources().getColor(android.R.color.black));
            m_etContent.setHintTextColor(hintColor);
        }
        if (a.hasValue(R.styleable.CommonEditTextLine_hintText)) {
            hintText = a.getString(R.styleable.CommonEditTextLine_hintText);
            m_etContent.setHint(hintText);
        }

        isPassword = a.getBoolean(R.styleable.CommonEditTextLine_isPassword, false);
        if(isPassword){
            m_etContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        a.recycle();
    }

    public void setM_tvLabel(int nResId) {
        m_tvLabel.setText(nResId);
    }

    public void setLabel1Color(int nResId) {
        m_tvLabel.setTextColor(mContext.getResources().getColor(nResId));
    }

    public String getContent() {
        return m_etContent.getText().toString();
    }

    public EditText getEditText() {
        return m_etContent;
    }

}
