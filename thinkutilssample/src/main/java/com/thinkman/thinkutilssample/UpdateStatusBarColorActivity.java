package com.thinkman.thinkutilssample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.thinkman.thinkutils.activity.BaseActivity;
import com.thinkman.thinkutils.activity.BaseActivityWithTranslucentBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateStatusBarColorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status_bar_color);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_green)
    public void onGreenClick() {
        this.initTranslucentBarColor(getResources().getIdentifier("holo_green_light", "color", "android"));
    }

    @OnClick(R.id.btn_orange)
    public void onOrangeClick() {
        this.initTranslucentBarColor(getResources().getIdentifier("holo_orange_dark", "color", "android"));
    }

    @OnClick(R.id.btn_red)
    public void onRedClick() {
        this.initTranslucentBarColor(getResources().getIdentifier("holo_red_light", "color", "android"));
    }
}
