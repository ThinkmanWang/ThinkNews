package com.thinkman.thinkutilssample;

import android.os.Bundle;

import com.thinkman.thinkutils.activity.ThinkBaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateStatusBarColorActivity extends ThinkBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status_bar_color);

        ButterKnife.bind(this);
    }

//    @OnClick(R.id.btn_green)
//    public void onGreenClick() {
//        this.initTranslucentBarColor(getResources().getIdentifier("holo_green_light", "color", "android"));
//    }
//
//    @OnClick(R.id.btn_orange)
//    public void onOrangeClick() {
//        this.initTranslucentBarColor(getResources().getIdentifier("holo_orange_dark", "color", "android"));
//    }
//
//    @OnClick(R.id.btn_red)
//    public void onRedClick() {
//        this.initTranslucentBarColor(getResources().getIdentifier("holo_red_light", "color", "android"));
//    }
}
