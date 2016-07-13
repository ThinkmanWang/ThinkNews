package com.thinkman.thinkutilssample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thinkman.thinkutils.activity.ThinkBaseActivity;
import com.thinkman.thinkutils.layout.ProgressLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProgressActivity extends ThinkBaseActivity {

    @BindView(R.id.pl_content)
    ProgressLayout m_plMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

    }

    @NonNull
    @OnClick(R.id.loading_btn)
    protected void onLoadingClick() {
        this.m_plMainLayout.showLoading();
    }

    @NonNull
    @OnClick(R.id.content_btn)
    protected void onContentClick() {
        this.m_plMainLayout.showContent();
    }

    @NonNull
    @OnClick(R.id.none_btn)
    protected void onNoneClick() {
        this.m_plMainLayout.showNone(retryListener);
    }

    @OnClick(R.id.net_error_btn)
    protected void onNetErrorClick() {
        this.m_plMainLayout.showNetError(retryListener);
    }

    @NonNull
    @OnClick(R.id.failed_btn)
    protected void onFailedClick() {
        this.m_plMainLayout.showFailed(retryListener);
    }

    private View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProgressActivity.this.m_plMainLayout.showLoading();
        }
    };
}
