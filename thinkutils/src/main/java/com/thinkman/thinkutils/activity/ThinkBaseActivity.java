package com.thinkman.thinkutils.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thinkman.thinkutils.R;
import com.thinkman.thinkutils.commonutils.DisplayUtil;

/**
 * Created by wangx on 2016/7/13.
 */
public class ThinkBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        //overridePendingTransition(R.anim.out_to_right, R.anim.in_from_left);

    }

//    public void initTranslucentBarColor(int nResBgColor) {
//        initTranslucentBar(nResBgColor, -1);
//    }
//
//    public void initTranslucentBarImage(int nResImg) {
//        initTranslucentBar(-1, nResImg);
//    }
//
//    private void initTranslucentBar(int nResBgColor, int nResBgImg) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//
//        ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
//        if (contentView.getChildCount() > 1) {
//            contentView.removeViewAt(1);
//        }
//
//        ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
//        rootView.setFitsSystemWindows(true);
//
//        // get status bar height
//        int height = DisplayUtil.getStatusBarHeight(this);
//
//        // create new imageview and set resource id
//        ImageView image = new ImageView(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
//        image.setLayoutParams(params);
//        if (nResBgImg > 0) {
//            image.setBackgroundResource(nResBgImg);
//        }
//        if (nResBgColor > 0) {
//            image.setBackgroundColor(getResources().getColor(nResBgColor));
//        }
//        image.setScaleType(ImageView.ScaleType.FIT_XY);
//
//        // add image view to content view
//        contentView.addView(image);
//        // rootView.setFitsSystemWindows(true);
//
//    }
    
}
