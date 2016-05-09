package com.thinkman.thinknews.fragment;


import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by nick on 2016/1/29.
 */
public class BaseFragment extends Fragment {
    private View mContentView = null;

    public void setContentView(View view) {
        mContentView = view;
    }

    public View getContentView() {
        return mContentView;
    }
}
