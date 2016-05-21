package com.thinkman.thinkutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Stack;

public class ProgressWebView extends WebView implements View.OnClickListener {
    private ProgressBar mProgressbar;

    public ProgressWebView(Context context) {
        super(context);
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        addProgressBar();
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(mWebViewClient);

        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().setBlockNetworkImage(false);
    }

    WebViewClient mWebViewClient = new WebViewClient() {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return false;
        }

        public void onPageFinished(WebView view, String url) {

        }
    };

    @SuppressWarnings("deprecation")
    private void addProgressBar() {
        mProgressbar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        mProgressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 30, 0, 0));
        addView(mProgressbar);
    }

    @Override
    public void onClick(View v) {

    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressbar.setVisibility(GONE);
            } else {
                if (mProgressbar.getVisibility() == GONE)
                    mProgressbar.setVisibility(VISIBLE);
                mProgressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

//    private Stack<String> mHistory = new Stack<String>();
//    public boolean canGoBack() {
//        return (mHistory.size() > 1);
//    }
//
//    public void goBack() {
//        mHistory.pop();  //current page
//
//        loadUrl(mHistory.pop());
//    }

}