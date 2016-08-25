package com.thinkman.thinkutils.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.thinkman.thinkutils.R;

/**
 * Created by Thinkman on 2016/1/29.
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
