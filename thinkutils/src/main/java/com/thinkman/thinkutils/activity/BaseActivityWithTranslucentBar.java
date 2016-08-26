package com.thinkman.thinkutils.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.thinkman.thinkutils.R;

/**
 * Created by wangx on 2016/7/13.
 */
public class BaseActivityWithTranslucentBar extends ThinkBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTheme(R.style.AppThemeTranslucentBar);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTransparentStatusBar();
    }

    private int m_nStatusBarColor = Color.TRANSPARENT;
    public void setStatusBarColor(@ColorInt int color) {
        m_nStatusBarColor = color;
        setTransparentStatusBar();
    }

    public int getStatusBarColor() {
        return m_nStatusBarColor;
    }

    public void setTransparentStatusBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(m_nStatusBarColor);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        rootView.setFitsSystemWindows(false);
    }
}
