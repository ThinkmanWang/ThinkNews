package com.thinkman.thinkutils.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.thinkman.thinkutils.R;

/**
 * Created by wangx on 2016/7/13.
 */
public class ThinkBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ThinkActivityManager.getInstance().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThinkActivityManager.getInstance().delectActivity(this);
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
    }

    public void finishAllActivity() {
        ThinkActivityManager.getInstance().finishAllActivity();
    }

    private boolean m_bDoubleBackExit = false;
    public void setDoubleBackExit(boolean isBackExit) {
        this.m_bDoubleBackExit = isBackExit;
    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (m_bDoubleBackExit) {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序",
                            Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    this.finish();
                }
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
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
