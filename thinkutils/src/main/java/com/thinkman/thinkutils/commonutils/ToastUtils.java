package com.thinkman.thinkutils.commonutils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by wangx on 2016/6/4.
 */
public class ToastUtils {

    private static Toast toast;

    public static void showToast(Context context,String message){
        showToast(context,message,false);
    }
    public static void showToast(Context context,String message,boolean isCenter){
        if(toast != null){
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        if(isCenter){
            toast.setGravity(Gravity.CENTER,0,0);
        }
        toast.show();
    }

}
