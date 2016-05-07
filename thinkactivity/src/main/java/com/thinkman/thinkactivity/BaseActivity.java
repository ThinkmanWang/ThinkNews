package com.thinkman.thinkactivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thinkman.actionbar.CommonActionBar1;

/**
 * Created by wangx on 2016/1/25.
 */
public class BaseActivity extends AppCompatActivity {

    //CommonActionBar mCommonActionBar = null;
    CommonActionBar1 mCommonActionBar1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //mCommonActionBar = new CommonActionBar(this);
        mCommonActionBar1 = new CommonActionBar1(this);
    }

    public CommonActionBar1 getCustomActionBar(){
        return mCommonActionBar1;
    }

    public void setOnActionBarLeftClickListener(View.OnClickListener listener) {
        mCommonActionBar1.getLeftCtv().setOnClickListener(listener);
    }

    public void setOnActionBarTitleClickListener(View.OnClickListener listener) {
        mCommonActionBar1.getTitleCtv().setOnClickListener(listener);
    }

    public void setOnActionBarLeftOfRightClickListener(View.OnClickListener listener) {
        mCommonActionBar1.getLeftOfRightCtv().setOnClickListener(listener);
    }

    public void setOnActionBarRightClickListener(View.OnClickListener listener) {
        mCommonActionBar1.getRightCtv().setOnClickListener(listener);
    }

    public void setActionBarTitleColor(int nColor) {
        mCommonActionBar1.getTitleCtv().setTextColor(getResources().getColor(nColor));
        mCommonActionBar1.getSubTitleCtv().setTextColor(getResources().getColor(nColor));
    }

    public void setCusomActionBar(int nResLayout) {

    }

    public void setActionBar(int nResTitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar1);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar1.setTitleText(nResTitle);
        mCommonActionBar1.showTitleCtv();

        mCommonActionBar1.hiddenLeftCtv();
        mCommonActionBar1.hiddenRightCtv();
    }

    public void setActionBar(int nResLeft, int nResTitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar1);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar1.setLeftDrawable(getResources().getDrawable(nResLeft));
        mCommonActionBar1.showLeftCtv();

        mCommonActionBar1.setTitleText(nResTitle);
        mCommonActionBar1.showTitleCtv();

        mCommonActionBar1.hiddenRightCtv();
    }

    public void setActionBar(int nResLeft, int nResTitle, int nResRight) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar1);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar1.setLeftDrawable(getResources().getDrawable(nResLeft));
        mCommonActionBar1.showLeftCtv();

        mCommonActionBar1.setTitleText(nResTitle);
        mCommonActionBar1.showTitleCtv();

        mCommonActionBar1.setRightDrawable(getResources().getDrawable(nResRight));
        mCommonActionBar1.showRightCtv();
    }

    public void setActionBar(int nResLeft, int nResTitle, String szTxtRight) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar1);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar1.setLeftDrawable(getResources().getDrawable(nResLeft));
        mCommonActionBar1.showLeftCtv();

        mCommonActionBar1.setTitleText(nResTitle);
        mCommonActionBar1.showTitleCtv();

        mCommonActionBar1.setRightText(szTxtRight);
        mCommonActionBar1.showRightCtv();
    }

    public void setActionBar(int nResLeft, int nResTitle, int nResSubTitle, int nResRight) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar1);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar1.setLeftDrawable(getResources().getDrawable(nResLeft));
        mCommonActionBar1.showLeftCtv();

        mCommonActionBar1.setTitleText(nResTitle);
        mCommonActionBar1.showTitleCtv();

        mCommonActionBar1.setSubTitleText(nResSubTitle);
        mCommonActionBar1.showSubTitleCtv();

        mCommonActionBar1.setRightDrawable(getResources().getDrawable(nResRight));
        mCommonActionBar1.showRightCtv();
    }

    /**
     * @param nResLeft     图片（左侧返回按钮）
     * @param nResTitle    标题
     * @param nLeftOfRight 右侧第一个图片
     * @param nResRight    最右侧文字
     */
    public void setActionBarShowLeftOfRightDrawable(int nResLeft, int nResTitle, int nLeftOfRight, int nResRight) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar1);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar1.setLeftDrawable(getResources().getDrawable(nResLeft));
        mCommonActionBar1.showLeftCtv();

        mCommonActionBar1.setTitleText(nResTitle);
        mCommonActionBar1.showTitleCtv();

        mCommonActionBar1.setLeftOfRightDrawable(getResources().getDrawable(nLeftOfRight));
        mCommonActionBar1.showLeftOfRightCtv();

        mCommonActionBar1.setRightText(nResRight);
        mCommonActionBar1.showRightCtv();
    }

//    /**
//     * @param nResLeft     图片（左侧返回按钮）
//     * @param nResTitle    标题
//     * @param nLeftOfRight 右侧第一个文字
//     * @param nResRight    最右侧文字
//     */
//    public void setActionBar(int nResLeft, int nResTitle, int nLeftOfRight, int nResRight) {
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setCustomView(mCommonActionBar1);
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayUseLogoEnabled(false);
//        actionBar.setDisplayShowCustomEnabled(true);
//
//        mCommonActionBar1.setLeftDrawable(getResources().getDrawable(nResLeft));
//        mCommonActionBar1.showLeftCtv();
//
//        mCommonActionBar1.setTitleText(nResTitle);
//        mCommonActionBar1.showTitleCtv();
//
//        mCommonActionBar1.setLeftOfRightText(nLeftOfRight);
//        mCommonActionBar1.showLeftOfRightCtv();
//
//        mCommonActionBar1.setRightDrawable(getResources().getDrawable(nResRight));
//        mCommonActionBar1.showRightCtv();
//    }

    /**
     * @param nResLeft     图片（左侧返回按钮）
     * @param nResTitle    标题
     * @param nLeftOfRight 右侧第一个图片
     * @param nResRight    最右侧图片
     */
    public void setActionBarShowRightDrawableWithLeft(int nResLeft, int nResTitle, int nLeftOfRight, int nResRight) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mCommonActionBar1);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        mCommonActionBar1.setLeftDrawable(getResources().getDrawable(nResLeft));
        mCommonActionBar1.showLeftCtv();

        mCommonActionBar1.setTitleText(nResTitle);
        mCommonActionBar1.showTitleCtv();

        mCommonActionBar1.setLeftOfRightText(nLeftOfRight);
        mCommonActionBar1.showLeftOfRightCtv();

        mCommonActionBar1.setLeftOfRightDrawable(getResources().getDrawable(nLeftOfRight));
        mCommonActionBar1.showLeftOfRightCtv();

        mCommonActionBar1.setRightDrawable(getResources().getDrawable(nResRight));
        mCommonActionBar1.showRightCtv();
    }

    public void setActionBarLeftIconVisible(boolean bVisible) {
        if (bVisible) {
            mCommonActionBar1.showLeftCtv();
        } else {
            mCommonActionBar1.hiddenLeftCtv();
        }
    }

    public void setActionBarTitleVisible(boolean bVisible) {
        if (bVisible) {
            mCommonActionBar1.showTitleCtv();
        } else {
            mCommonActionBar1.hiddenTitleCtv();
        }
    }

    public void setActionBarRightIconVisible(boolean bVisible) {
        if (bVisible) {
            mCommonActionBar1.showRightCtv();
        } else {
            mCommonActionBar1.hiddenRightCtv();
        }
    }

    public void setActionBarBG(int nResID) {
        mCommonActionBar1.setBackgroundResource(nResID);
    }

}
