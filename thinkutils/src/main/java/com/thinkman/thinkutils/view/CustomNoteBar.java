package com.thinkman.thinkutils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thinkman.thinkutils.R;

/**
 * TODO: document your custom view class.
 */
public class CustomNoteBar extends FrameLayout {

    View contentView;
    private boolean hasUnderBar = true;

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    private String labelText;
    private float labelTextSize;
    private int labelColor;
    private String noticeText;
    private float noticeTextSize;

    // Content View Elements

    // Content View Elements
    private int noticeColor;
    private TextView label;
    private TextView notice;

    // End Of Content View Elements
    private EditText note;
    private int hintColor;
    private String hintText;
    private int maxLength;

    public CustomNoteBar(Context context) {
        super(context);
        init(context, null, 0);
    }


    public CustomNoteBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }


    public CustomNoteBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        contentView = LayoutInflater.from(context).inflate(R.layout.bar_custom_note, this, true);
        this.setBackgroundColor(Color.WHITE);

        label = (TextView) contentView.findViewById(R.id.label);
        notice = (TextView) contentView.findViewById(R.id.notice);
        note = (EditText) contentView.findViewById(R.id.note);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CustomNoteBar, defStyle, 0);

        if (a.hasValue(R.styleable.CustomNoteBar_labelText)) {
            labelText = a.getString(R.styleable.CustomNoteBar_labelText);
            label.setText(labelText);
        }
        if (a.hasValue(R.styleable.CustomNoteBar_labelTextSize)) {
            labelTextSize = a.getDimension(R.styleable.CustomNoteBar_labelTextSize, getResources().getDimension(R.dimen.textsize_26));
            label.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelTextSize);
        }

        if (a.hasValue(R.styleable.CustomNoteBar_labelColor)) {
            labelColor = a.getInt(R.styleable.CustomNoteBar_labelColor, getResources().getColor(R.color.text_dark_666));
            label.setTextColor(labelColor);
        }

        if (a.hasValue(R.styleable.CustomNoteBar_noticeText)) {
            noticeText = a.getString(R.styleable.CustomNoteBar_noticeText);
            notice.setText(noticeText);

            if (a.hasValue(R.styleable.CustomNoteBar_noticeTextSize)) {
                noticeTextSize = a.getDimension(R.styleable.CustomNoteBar_noticeTextSize, getResources().getDimension(R.dimen.textsize_26));
                notice.setTextSize(TypedValue.COMPLEX_UNIT_PX, noticeTextSize);
            }

            if (a.hasValue(R.styleable.CustomNoteBar_noticeColor)) {
                noticeColor = a.getInt(R.styleable.CustomNoteBar_noticeColor, getResources().getColor(R.color.blue));
                notice.setTextColor(noticeColor);
            }
        }

        if (a.hasValue(R.styleable.CustomNoteBar_hintColor)) {
            hintColor = a.getColor(R.styleable.CustomNoteBar_hintColor, getResources().getColor(R.color.text_dark_ccc));
            note.setHintTextColor(hintColor);
        }

        if (a.hasValue(R.styleable.CustomNoteBar_hintText)) {
            hintText = a.getString(R.styleable.CustomNoteBar_hintText);
            note.setHint(hintText);
        }

        hasUnderBar = a.getBoolean(R.styleable.CustomNoteBar_hasUnderBar, true);
        if (hasUnderBar) {
            float startX;
            startX = getResources().getDimension(R.dimen.underbar_marginleft_short);

            View view = new View(context);
            view.setBackgroundResource(R.color.line);
            LayoutParams layoutParams1
                    = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1, Gravity.BOTTOM);
            layoutParams1.setMargins((int) startX, 0, 0, 0);
            addView(view, layoutParams1);
        }
        maxLength = a.getInteger(R.styleable.CustomNoteBar_maxLength, -1);
        if (maxLength != -1) {
            note.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }

        a.recycle();
    }

    public EditText getEditText() {
        return note;
    }


    public TextView getNotice() {
        return notice;
    }

}
