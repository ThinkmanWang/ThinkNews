package com.thinkman.thinknews.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.thinkman.thinkactivity.BaseActivity;
import com.thinkman.thinknews.R;
import com.thinkman.thinknews.models.NewsModel;
import com.thinkman.thinknews.utils.FavoritesDbUtils;
import com.thinkman.thinkutils.view.ProgressWebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import java.util.Timer;
import java.util.TimerTask;

public class NewsActivity extends BaseActivity {

    private ProgressWebView mProgressWebView = null;

    FloatingActionsMenu menuMultipleActions = null;
    FloatingActionButton m_fabFavorite = null;
    FloatingActionButton m_fabShare = null;

    private NewsModel mNews = new NewsModel();

    //for webview content
    private String mUrl = null;
    private String mTitle = null;

    public static final String CTIME = "ctime";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PIC_URL = "picUrl";
    public static final String URL = "url";
    public static final String SHOW_FAVORITE = "show_favorite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_fabFavorite = (FloatingActionButton) findViewById(R.id.action_b);
        m_fabShare = (FloatingActionButton) findViewById(R.id.action_a);

        mNews.setCtime(getIntent().getStringExtra(CTIME));
        mNews.setTitle(getIntent().getStringExtra(TITLE));
        mNews.setDescription(getIntent().getStringExtra(DESCRIPTION));
        mNews.setPicUrl(getIntent().getStringExtra(PIC_URL));
        mNews.setUrl(getIntent().getStringExtra(URL));

        m_fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuMultipleActions.collapse();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long nRet = FavoritesDbUtils.insertFavorite(NewsActivity.this, mNews);
                        if (-2 == nRet) {
                            mHandler.sendEmptyMessage(MSG_FAVORITE_ALREADY_EXISTS);
                        } else if (-1 == nRet || 0 == nRet) {
                            mHandler.sendEmptyMessage(MSG_ADD_FAVORITE_FAILED);
                        } else {
                            mHandler.sendEmptyMessage(MSG_ADD_FAVORITE_SUCCESS);
                        }
                    }
                }).start();
            }
        });

        m_fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuMultipleActions.collapse();

                menuMultipleActions.collapse();
                UMImage image = new UMImage(NewsActivity.this, mNews.getPicUrl());
                new ShareAction(NewsActivity.this).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL)
                        .withText(mNews.getUrl())
                        .withTitle(mNews.getTitle())
                        .withTargetUrl(mNews.getUrl())
                        .withMedia(image)
                        .setCallback(umShareListener)
                        .open();
            }
        });

        boolean bShowFavorite = getIntent().getBooleanExtra(SHOW_FAVORITE, true);
        if (bShowFavorite) {
            m_fabFavorite.setVisibility(View.VISIBLE);
        } else {
            m_fabFavorite.setVisibility(View.GONE);
        }

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

        initFloatActionMenu();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            if(platform.name().equals("WEIXIN_FAVORITE")){
                Toast.makeText(NewsActivity.this,platform + " 收藏成功啦",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(NewsActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(NewsActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(NewsActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    private void initFloatActionMenu() {
        menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
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

        if (menuMultipleActions.isExpanded()) {
            menuMultipleActions.collapse();
            return;
        }

        if (mProgressWebView.canGoBack()) {
            mProgressWebView.goBack();
            return;
        }

        super.onBackPressed();
    }

    public static final int MSG_ADD_FAVORITE_SUCCESS = 1;
    public static final int MSG_ADD_FAVORITE_FAILED = 2;
    public static final int MSG_FAVORITE_ALREADY_EXISTS = 3;
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ADD_FAVORITE_SUCCESS:
                    Snackbar.make(m_fabFavorite, "Add to Favorites success!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                case MSG_ADD_FAVORITE_FAILED:
                    Snackbar.make(m_fabFavorite, "Add to Favorites failed!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                case MSG_FAVORITE_ALREADY_EXISTS:
                    Snackbar.make(m_fabFavorite, "Favorites already exists", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
            }
        }
    };
}
