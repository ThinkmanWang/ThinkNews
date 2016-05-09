package com.thinkman.thinkutils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class ProgressWebView extends WebView {
    private ProgressBar mProgressbar;

    public ProgressWebView(Context context) {
        super(context);
        addProgressBar();
        setWebChromeClient(new WebChromeClient());
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addProgressBar();
        setWebChromeClient(new WebChromeClient());
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addProgressBar();
        setWebChromeClient(new WebChromeClient());
    }

    @SuppressWarnings("deprecation")
    private void addProgressBar() {
        mProgressbar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        mProgressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 30, 0, 0));
        addView(mProgressbar);
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

}