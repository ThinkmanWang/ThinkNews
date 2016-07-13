package com.thinkman.thinkutilssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thinkman.thinkutils.activity.ThinkBaseActivity;
import com.thinkman.thinkutils.commonutils.DisplayUtil;
import com.thinkman.thinkutils.view.CustomBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentActivity extends ThinkBaseActivity {

    @BindView(R.id.bar_1)
    CustomBar m_bar1 = null;

    @BindView(R.id.bar_2)
    CustomBar m_bar2 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.bar_1)
    public void onBar1Click() {
        if (DisplayUtil.isDevicePad(this)) {

        } else {
            Intent intent = new Intent(FragmentActivity.this, DetailActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.bar_2)
    public void onBar2Click() {
        if (DisplayUtil.isDevicePad(this)) {

        } else {
            Intent intent = new Intent(FragmentActivity.this, DetailActivity.class);
            startActivity(intent);
        }
    }
}
