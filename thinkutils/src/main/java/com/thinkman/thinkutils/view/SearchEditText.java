package com.thinkman.thinkutils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.thinkman.thinkutils.R;


/**
 * Created by wangx on 2016/7/20.
 */
public class SearchEditText extends LinearLayout {

    private Context mContext = null;
    private View mContentView = null;

    private ImageView m_ivLeft = null;
    private EditText m_etContent = null;
    private ImageView m_ivRight = null;

    public SearchEditText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;
        mContentView = LayoutInflater.from(context).inflate(R.layout.layout_search_edittext, this, true);

        m_ivLeft = (ImageView) mContentView.findViewById(R.id.iv_icon_left);
        m_ivRight = (ImageView) mContentView.findViewById(R.id.iv_icon_right);
        m_etContent = (EditText) mContentView.findViewById(R.id.et_content);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.SearchEditText, defStyle, 0);

        int hintColor = a.getInt(R.styleable.SearchEditText_hintColor, getResources().getColor(android.R.color.black));
        m_etContent.setHintTextColor(hintColor);

        String szHint = a.getString(R.styleable.SearchEditText_hintText);
        m_etContent.setHint(szHint);

        int inputType = a.getInt(R.styleable.SearchEditText_android_inputType, EditorInfo.TYPE_NULL);
        if (inputType != EditorInfo.TYPE_NULL) {
            m_etContent.setInputType(inputType);
        }

        m_etContent.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        float labelTextSize = a.getDimension(R.styleable.SearchEditText_android_textSize, 12);
        m_etContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelTextSize);

        int textColor = a.getInt(R.styleable.SearchEditText_android_textColor, getResources().getColor(android.R.color.black));
        m_etContent.setTextColor(textColor);

    }

    public void setLeftIcon(int nResId) {
        m_ivLeft.setImageResource(nResId);
    }

    public void setRightIcon(int nResId) {
        m_ivRight.setImageResource(nResId);
    }

    public void setOnLeftClickListener(View.OnClickListener listener) {
        m_ivLeft.setOnClickListener(listener);
    }

    public void setOnRightClickListener(View.OnClickListener listener) {
        m_ivRight.setOnClickListener(listener);
    }

    public void setHint(String szHint) {
        m_etContent.setHint(szHint);
    }

    public void setHint(int nResId) {
        m_etContent.setHint(nResId);
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener l) {
        m_etContent.setOnEditorActionListener(l);
    }

    public void addTextChangedListener(TextWatcher watcher) {
        m_etContent.addTextChangedListener(watcher);
    }

    public EditText getEditText() {
        return m_etContent;
    }

}
