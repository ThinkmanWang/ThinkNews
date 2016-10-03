package com.thinkman.thinkutils.commonutils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;

/**
 * Created by wangx on 2016/5/9.
 */
public class DisplayUtil {

    public static int getStatusBarHeight(Context context) {
        int res = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int height = 0;
        if (res != 0) {
            height = context.getResources().getDimensionPixelSize(res);
        }

        return height;
    }

    public static int getActionBarHeight(Context contect) {
        final TypedArray styledAttributes = contect.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });

        return (int) styledAttributes.getDimension(0, 0);
    }
    /**
     * 2014年10月24日 下午9:59:08
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     *
     * 2014年10月24日 下午9:59:53
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     *
     * 2014年10月24日 下午10:00:36
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     *
     * 2014年10月24日 下午10:01:01
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static boolean isDevicePad(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static int getScreenHeight(Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().heightPixels;
        return (int) (fontScale);
    }

    public static int getScreenWidth(Context context) {
        final float fontScale = context.getResources().getDisplayMetrics().widthPixels;
        return (int) (fontScale);
    }
}
