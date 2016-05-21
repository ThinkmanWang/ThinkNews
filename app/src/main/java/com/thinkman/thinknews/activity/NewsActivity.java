package com.thinkman.thinknews.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.thinkman.thinkactivity.BaseActivity;
import com.thinkman.thinknews.R;
import com.thinkman.thinknews.models.NewsModel;
import com.thinkman.thinknews.utils.FavoritesDbUtils;
import com.thinkman.thinkutils.view.ProgressWebView;

import java.util.Timer;
import java.util.TimerTask;

public class NewsActivity extends BaseActivity {

    private ProgressWebView mProgressWebView = null;

    private NewsModel mNews = new NewsModel();

    //for webview content
    private String mUrl = null;
    private String mTitle = null;

    public static final String CTIME = "ctime";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PIC_URL = "picUrl";
    public static final String URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        mNews.setCtime(getIntent().getStringExtra(CTIME));
        mNews.setTitle(getIntent().getStringExtra(TITLE));
        mNews.setDescription(getIntent().getStringExtra(DESCRIPTION));
        mNews.setPicUrl(getIntent().getStringExtra(PIC_URL));
        mNews.setUrl(getIntent().getStringExtra(URL));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long nRet = FavoritesDbUtils.insertFavorite(NewsActivity.this, mNews);
                if (-2 == nRet) {
                    Snackbar.make(view, "Favorites already exists", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Add to Favorites success!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        mTitle = getIntent().getStringExtra(TITLE);
        this.setActionBar(R.mipmap.ic_arrow_back_white, mTitle);
        this.setOnActionBarLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsActivity.this.finish();
            }
        });

        mProgressWebView = (ProgressWebView)findViewById(R.id.mian_webview);
        mProgressWebView.getSettings().setJavaScriptEnabled(true);
        mProgressWebView.getSettings().setDomStorageEnabled(true);
        mProgressWebView.getSettings().setBlockNetworkImage(false);

        mUrl = getIntent().getStringExtra(URL);
        if (false == TextUtils.isEmpty(mUrl)) {
            mProgressWebView.loadUrl(mUrl);
        }
    }

    private static boolean mBackKeyPressed = false;
    @Override
    public void onBackPressed() {

        if(!mBackKeyPressed){
            //Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 1000);
        } else {
            super.onBackPressed();
        }

        if (mProgressWebView.canGoBack()) {
            mProgressWebView.goBack();
            return;
        }

        super.onBackPressed();
    }
}
