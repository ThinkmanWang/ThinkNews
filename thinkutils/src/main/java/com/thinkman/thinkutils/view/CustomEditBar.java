package com.thinkman.thinkutils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkman.thinkutils.R;

/**
 * TODO: document your custom view class.
 */
public class CustomEditBar extends FrameLayout {

    View contentView;
    private boolean hasUnderBar = true;
    private boolean isPassword = true;
    private String labelText;
    private float labelTextSize;
    private int inputType;

    // Content View Elements

    // Content View Elements
    private int labelColor;
    private String contentText;
    private float contentTextSize;
    private int contentColor;
    private int hintColor;
    private String hintText;
    private TextView label;
    private ImageView iconRight;
    private ImageView iconLeft;
    private Drawable rightIconDrawable = null;
    private Drawable leftIconDrawable = null;

    // End Of Content View Elements
    private EditText content;

    public CustomEditBar(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CustomEditBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CustomEditBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(final Context context, AttributeSet attrs, int defStyle) {
        contentView = LayoutInflater.from(context).inflate(R.layout.bar_custom_edit, this, true);
        this.setBackgroundColor(Color.WHITE);

        label = (TextView) contentView.findViewById(R.id.label);
        content = (EditText) contentView.findViewById(R.id.edittext);
        iconLeft = (ImageView) contentView.findViewById(R.id.iv_icon_left);
        iconRight = (ImageView) contentView.findViewById(R.id.iv_icon_right);

        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CustomEditBar, defStyle, 0);

        inputType = a.getInt(R.styleable.CustomEditBar_android_inputType, EditorInfo.TYPE_NULL);
        if (inputType != EditorInfo.TYPE_NULL) {
            content.setInputType(inputType);
        }

        if (a.hasValue(R.styleable.CustomEditBar_labelText)) {
            labelText = a.getString(R.styleable.CustomEditBar_labelText);
            label.setText(labelText);
        }
        if (a.hasValue(R.styleable.CustomEditBar_labelTextSize)) {
            labelTextSize = a.getDimension(R.styleable.CustomEditBar_labelTextSize, getResources().getDimension(R.dimen.textsize_26));
            label.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelTextSize);
        }

        if (a.hasValue(R.styleable.CustomEditBar_labelColor)) {
            labelColor = a.getInt(R.styleable.CustomEditBar_labelColor, getResources().getColor(R.color.text_dark_666));
            label.setTextColor(labelColor);
        }
        contentText = a.getString(R.styleable.CustomEditBar_contentText);
        content.setText(contentText);

        if (a.hasValue(R.styleable.CustomEditBar_contentTextSize)) {
            contentTextSize = a.getDimension(R.styleable.CustomEditBar_contentTextSize, getResources().getDimension(R.dimen.textsize_26));
            content.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
        }

        if (a.hasValue(R.styleable.CustomEditBar_contentColor)) {
            contentColor = a.getInt(R.styleable.CustomEditBar_contentColor, getResources().getColor(R.color.text_dark_333));
            content.setTextColor(contentColor);
        }
        if (a.hasValue(R.styleable.CustomEditBar_hintColor)) {
            hintColor = a.getColor(R.styleable.CustomEditBar_hintColor, getResources().getColor(R.color.text_dark_ccc));
            content.setHintTextColor(hintColor);
        }
        if (a.hasValue(R.styleable.CustomEditBar_hintText)) {
            hintText = a.getString(R.styleable.CustomEditBar_hintText);
            content.setHint(hintText);
        }

        isPassword = a.getBoolean(R.styleable.CustomEditBar_isPassword, false);
        if(isPassword){
            content.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        hasUnderBar = a.getBoolean(R.styleable.CustomEditBar_hasUnderBar, true);
        if (hasUnderBar) {
            float startX;
            startX = getResources().getDimension(R.dimen.underbar_marginleft_short);

            View view = new View(context);
            view.setBackgroundResource(R.color.line);
            LayoutParams layoutParams1
                    = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1, Gravity.BOTTOM);
            layoutParams1.setMargins((int) startX, 0, (int) startX, 0);
            addView(view, layoutParams1);
        }

        boolean hasRightIcon = a.getBoolean(R.styleable.CustomEditBar_hasRightIcon, false);
        if (a.hasValue(R.styleable.CustomEditBar_rightIconDrawable)) {
            rightIconDrawable = a.getDrawable(R.styleable.CustomEditBar_rightIconDrawable);
        }

        if (!hasRightIcon) {
            if (iconRight != null) {
                iconRight.setVisibility(INVISIBLE);
            }
        } else {
            if (rightIconDrawable != null && iconRight != null) {
                iconRight.setImageDrawable(rightIconDrawable);
            }
            if (iconRight != null) {
                iconRight.setVisibility(VISIBLE);
            }
        }

        leftIconDrawable = a.getDrawable(R.styleable.CustomEditBar_leftIconDrawable);
        if (leftIconDrawable != null) {
            iconLeft.setImageDrawable(leftIconDrawable);
            iconLeft.setVisibility(VISIBLE);
        } else {
            iconLeft.setVisibility(GONE);
        }

        a.recycle();

    }

    public EditText getEditText() {
        return content;
    }
    public String getTextString() {
        return content.getText().toString();
    }
    public String getHintString() {
        return content.getHint().toString();
    }

    public ImageView getLeftImageView() {
        return iconLeft;
    }

    public ImageView getRightImageView() {
        return iconRight;
    }
}
