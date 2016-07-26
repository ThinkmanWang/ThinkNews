package com.thinkman.thinkutils.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.ActionBar;

import com.thinkman.thinkutils.activity.actionbar.CommonActionBar;

/**
 * Created by wangx on 2016/7/13.
 */
public class BaseActivityWithActionBar extends ThinkBaseActivity {

    CommonActionBar mCommonActionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCommonActionBar = new CommonActionBar(this);

    }

    public CommonActionBar getCustomActionBar(){
        return mCommonActionBar;
    }

    public void setOnActionBarLeftClickListener(View.OnClickListener listener) {
        mCommonActionBar.getLeftCtv().setOnClickListener(listener);
    }

    public void setOnActionBarTitleClickListener(View.OnClickListener listener) {
        mCommonActionBar.getTitleCtv().setOnClickListener(listener);
    }

    public void setOnActionBarRightClickListener(View.OnClickListener listener) {
        mCommonActionBar.getRightCtv().setOnClickListener(listener);
    }

    public void setActionBarTitleColor(int nColor) {
        mCommonActionBar.getTitleCtv().setTextColor(getResources().getColor(nColor));
    }

    public void setActionBar(int nResTitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar.setTitleText(nResTitle);
        mCommonActionBar.showTitleCtv();

        mCommonActionBar.hiddenLeftCtv();
        mCommonActionBar.hiddenRightCtv();
    }

    public void setActionBar(int nResLeft, String szTitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar.setLeftDrawable(getResources().getDrawable(nResLeft));
        mCommonActionBar.showLeftCtv();

        mCommonActionBar.setTitleText(szTitle);
        mCommonActionBar.showTitleCtv();

        mCommonActionBar.hiddenRightCtv();
    }

    public void setActionBar(int nResLeft, int nResTitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar.setLeftDrawable(getResources().getDrawable(nResLeft));
        mCommonActionBar.showLeftCtv();

        mCommonActionBar.setTitleText(nResTitle);
        mCommonActionBar.showTitleCtv();

        mCommonActionBar.hiddenRightCtv();
    }

    public void setActionBar(int nResLeft, int nResTitle, int nResRight) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar.setLeftDrawable(getResources().getDrawable(nResLeft));
        mCommonActionBar.showLeftCtv();

        mCommonActionBar.setTitleText(nResTitle);
        mCommonActionBar.showTitleCtv();

        mCommonActionBar.setRightDrawable(getResources().getDrawable(nResRight));
        mCommonActionBar.showRightCtv();
    }

    public void setActionBar(int nResLeft, int nResTitle, String szTxtRight) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar.setLeftDrawable(getResources().getDrawable(nResLeft));
        mCommonActionBar.showLeftCtv();

        mCommonActionBar.setTitleText(nResTitle);
        mCommonActionBar.showTitleCtv();

        mCommonActionBar.setRightText(szTxtRight);
        mCommonActionBar.showRightCtv();
    }

    public void setActionBarLeftIconVisible(boolean bVisible) {
        if (bVisible) {
            mCommonActionBar.showLeftCtv();
        } else {
            mCommonActionBar.hiddenLeftCtv();
        }
    }

    public void setActionBarTitleVisible(boolean bVisible) {
        if (bVisible) {
            mCommonActionBar.showTitleCtv();
        } else {
            mCommonActionBar.hiddenTitleCtv();
        }
    }

    public void setActionBarRightIconVisible(boolean bVisible) {
        if (bVisible) {
            mCommonActionBar.showRightCtv();
        } else {
            mCommonActionBar.hiddenRightCtv();
        }
    }

    public void setActionBarBG(int nResID) {
        mCommonActionBar.setBackgroundResource(nResID);
    }

    public void setActionBarBGColor(int nResID) {
        mCommonActionBar.setBackgroundColor(nResID);
    }
}
