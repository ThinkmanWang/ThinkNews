package com.thinkman.thinknews.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.thinkman.thinkactivity.BaseActivity;
import com.thinkman.thinknews.R;
import com.thinkman.thinkutils.view.ProgressWebView;

public class NewsActivity extends BaseActivity {

    private ProgressWebView mProgressWebView = null;

    //for webview content
    private String mUrl = null;
    private String mTitle = null;

    public static final String TITLE = "title";
    public static final String URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTitle = getIntent().getStringExtra(TITLE);
        this.setActionBar(R.mipmap.ic_launcher, mTitle);

        mUrl = getIntent().getStringExtra(URL);

        mProgressWebView = (ProgressWebView)findViewById(R.id.mian_webview);
        mProgressWebView.getSettings().setJavaScriptEnabled(true);
        mProgressWebView.getSettings().setDomStorageEnabled(true);
        mProgressWebView.getSettings().setBlockNetworkImage(false);

        if (false == TextUtils.isEmpty(mUrl)) {
            mProgressWebView.loadUrl(mUrl);
        }
    }

}
