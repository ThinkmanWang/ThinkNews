package com.thinkman.thinknews;

import android.app.Application;
import android.util.Log;

import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wangx on 2016/5/22.
 */
public class MyApp extends Application {
    private static final String TAG = "JPush";

    @Override
    public void onCreate() {
        Log.d(TAG, "[ExampleApplication] onCreate");
        super.onCreate();

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        PlatformConfig.setWeixin("wxad8f4f480c2310bb", "f7970cef1547d76f2e4b7d995df53fae");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");
    }
}
