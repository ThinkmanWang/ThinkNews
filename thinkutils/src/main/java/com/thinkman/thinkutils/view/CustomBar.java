package com.thinkman.thinkutils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkman.thinkutils.R;


/**
 * TODO: document your custom view class.
 */
public class CustomBar extends FrameLayout {

    private boolean hasLeftIcon = false;
    private boolean hasUnderBar = true;
    private Drawable leftIconDrawable = null;
    private boolean hasRightIcon = false;
    private Drawable rightIconDrawable = null;
    private String labelText;

    public float getLabelTextSize() {
        return labelTextSize;
    }

    public void setLabelTextSize(float labelTextSize) {
        this.labelTextSize = labelTextSize;
    }

    private float labelTextSize;
    private int labelColor;
    private String contentText;
    private float contentTextSize;
    private int contentColor;
    private String hintText = "HINT";
    private float hintTextSize;
    private int hintColor;
    private boolean clickable = true;

    // Content View Elements

    private ImageView iconLeft;
    private TextView label;
    private TextView content;
    private ImageView iconRight;
    private View contentView;

    public CustomBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CustomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CustomBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        contentView = LayoutInflater.from(context).inflate(R.layout.bar_custom, this, true);
        this.setBackgroundColor(Color.WHITE);
        iconLeft = (ImageView) contentView.findViewById(R.id.iconLeft);
        label = (TextView) contentView.findViewById(R.id.label);
        content = (TextView) contentView.findViewById(R.id.content);
        iconRight = (ImageView) contentView.findViewById(R.id.iconRight);

        //content.setHint("asdfasdf");
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CustomBar, defStyle, 0);

        hasLeftIcon = a.getBoolean(R.styleable.CustomBar_hasLeftIcon, false);

        if (a.hasValue(R.styleable.CustomBar_leftIconDrawable) &&
                ((a.hasValue(R.styleable.CustomBar_hasLeftIcon) && hasLeftIcon) || !a.hasValue(R.styleable.CustomBar_hasLeftIcon))) {

            hasLeftIcon = true;

            leftIconDrawable = a.getDrawable(R.styleable.CustomBar_leftIconDrawable);
            if (leftIconDrawable != null) {
                iconLeft.setImageDrawable(leftIconDrawable);
                iconLeft.setVisibility(VISIBLE);

            } else {
                iconLeft.setVisibility(GONE);
            }
        } else {
            hasLeftIcon = false;
        }

        hasRightIcon = a.getBoolean(R.styleable.CustomBar_hasRightIcon, false);

        if (a.hasValue(R.styleable.CustomBar_rightIconDrawable)) {
            rightIconDrawable = a.getDrawable(R.styleable.CustomBar_rightIconDrawable);
        }

        if (!hasRightIcon) {
            iconRight.setVisibility(INVISIBLE);
        } else {
            if (rightIconDrawable != null) {
                iconRight.setImageDrawable(rightIconDrawable);
            }
            iconRight.setVisibility(VISIBLE);
        }

        if (a.hasValue(R.styleable.CustomBar_labelText)) {
            labelText = a.getString(R.styleable.CustomBar_labelText);
            label.setText(labelText);
        }
        if (a.hasValue(R.styleable.CustomBar_labelTextSize)) {
            labelTextSize = a.getDimension(R.styleable.CustomBar_labelTextSize, getResources().getDimension(R.dimen.textsize_26));
            label.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelTextSize);
        }

        if (a.hasValue(R.styleable.CustomBar_labelColor)) {
            labelColor = a.getInt(R.styleable.CustomBar_labelColor, getResources().getColor(R.color.text_dark_666));
            label.setTextColor(labelColor);
        }

        if (a.hasValue(R.styleable.CustomBar_contentText)) {
            contentText = a.getString(R.styleable.CustomBar_contentText);

            content.setText(contentText);

            if (a.hasValue(R.styleable.CustomBar_contentTextSize)) {
                contentTextSize = a.getDimension(R.styleable.CustomBar_contentTextSize, getResources().getDimension(R.dimen.textsize_26));
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
            }

            if (a.hasValue(R.styleable.CustomBar_contentColor)) {
                contentColor = a.getInt(R.styleable.CustomBar_contentColor, getResources().getColor(R.color.text_dark_333));
                content.setTextColor(contentColor);
            }


        }
            if (a.hasValue(R.styleable.CustomBar_hintText)) {
                hintText = a.getString(R.styleable.CustomBar_hintText);
                content.setHint(hintText);
                content.setHintTextColor(getResources().getColor(R.color.text_dark_ccc));
            }

            if (a.hasValue(R.styleable.CustomBar_hintTextSize)) {
                hintTextSize = a.getDimension(R.styleable.CustomBar_hintTextSize, getResources().getDimension(R.dimen.textsize_26));
                content.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintTextSize);
            }

        hasUnderBar = a.getBoolean(R.styleable.CustomBar_hasUnderBar, true);
        if (hasUnderBar) {
            float startX = getResources().getDimension(R.dimen.underbar_marginleft_short);

            View view = new View(context);
            view.setBackgroundResource(R.color.line);
            LayoutParams layoutParams1
                    = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1, Gravity.BOTTOM);
            layoutParams1.setMargins((int) startX, 0, (int)startX, 0);
            addView(view, layoutParams1);

        }

        clickable = a.getBoolean(R.styleable.CustomBar_clickable, true);
        setClickable(clickable);
        a.recycle();
    }


    public String getLabelText() {
        return label.getText().toString();
    }

    public void setLabelText(String label) {
        this.label.setText(label);
    }

    public String getContentText() {
        String str = content.getText().toString();
        if(str.equals("请选择")||str.equals("(必选)")||str.equals("(必填)")){
            return "";
        }
        return str;
    }

    public TextView getContentTextView() {
        return content;
    }

    public void setContentText(String content) {
        this.content.setText(content);
    }


    public void setContentTextColor(int color) {
        this.contentColor = color;
        this.content.setTextColor(color);
    }
}
