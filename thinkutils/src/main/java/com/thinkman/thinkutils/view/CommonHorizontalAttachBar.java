package com.thinkman.thinkutils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.thinkman.thinkutils.R;

/**
 * Created by wangx on 2016/7/7.
 */
public class CommonHorizontalAttachBar extends HorizontalScrollView implements View.OnClickListener{

    Context mContent = null;
    View m_ContentView = null;
    LinearLayout m_llContent = null;

    private int m_nTextColor;
    private float m_fLabelTextSize;

    public CommonHorizontalAttachBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CommonHorizontalAttachBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CommonHorizontalAttachBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContent = context;
        m_ContentView = LayoutInflater.from(context).inflate(R.layout.bar_custom_horizontal_attach, this, true);
        m_llContent = (LinearLayout) m_ContentView.findViewById(R.id.ll_content);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CommonHorizontalAttachBar, defStyle, 0);

        if (a.hasValue(R.styleable.CommonHorizontalAttachBar_labelColor)) {
            m_nTextColor = a.getInt(R.styleable.CommonHorizontalAttachBar_labelColor, getResources().getColor(R.color.text_dark_666));
        } else {
            m_nTextColor = getResources().getColor(R.color.text_dark_666);
        }

        if (a.hasValue(R.styleable.CommonHorizontalAttachBar_labelTextSize)) {
            m_fLabelTextSize = a.getDimension(R.styleable.CommonHorizontalAttachBar_labelTextSize, getResources().getDimension(R.dimen.text_size_small));
        } else {
            m_fLabelTextSize = getResources().getDimension(R.dimen.text_size_small);
        }
    }

    public void addItem(String szUrl, String szText) {
        addItem(szUrl, szText
                , (int) mContent.getResources().getDimension(R.dimen.avatar_size)
                , (int) mContent.getResources().getDimension(R.dimen.avatar_size));
    }

    public void addItem(String szUrl, String szText, int nWidth, int nHeight) {
        CircleImageText citItem = new CircleImageText(mContent);
        citItem.getTextView().setTextColor(m_nTextColor);
        citItem.getTextView().setTextSize(TypedValue.COMPLEX_UNIT_PX, m_fLabelTextSize);
        citItem.getTextView().setText(szText);
        citItem.setOnClickListener(this);

        Glide.with(mContent)
                .load(szUrl)
                .override(nWidth, nHeight)
                .into(citItem.getImageView());

        addItem(citItem);
    }

    private void addItem(View view) {
        if (null == view) {
            return;
        }

        LinearLayout.LayoutParams layoutParams
           = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins((int) mContent.getResources().getDimension(R.dimen.horizontal_attach_bar_margin)
                , 0
                , (int) mContent.getResources().getDimension(R.dimen.horizontal_attach_bar_margin)
                , 0);

        m_llContent.addView(view, layoutParams);
    }

    public void clear() {
        m_llContent.removeAllViews();
    }

    public int getCount() {
        return m_llContent.getChildCount();
    }

    public void removeAt(int nPosition) {
        if (nPosition < 0 || nPosition >= m_llContent.getChildCount()) {
            return;
        }

        m_llContent.removeViewAt(nPosition);
    }

    public CircleImageText getItem(int nPosition) {
        if (nPosition < 0 || nPosition >= m_llContent.getChildCount()) {
            return null;
        }

        return (CircleImageText)m_llContent.getChildAt(nPosition);
    }

    private OnItemClickedListener mOnItemClickListener = null;
    public interface OnItemClickedListener {
        void onItemClick(int nPostion, CircleImageText view);
    }

    public void setOnItemClickListener(OnItemClickedListener listener) {
        mOnItemClickListener = listener;
    }

    public void onClick(View v) {
        for (int i = 0; i < m_llContent.getChildCount(); ++i) {
            if (v == m_llContent.getChildAt(i) && mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(i, (CircleImageText)v);
                break;
            }
        }
    }
}
