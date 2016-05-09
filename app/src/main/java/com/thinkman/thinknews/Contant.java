package com.thinkman.thinknews;

/**
 * Created by wangx on 2016/5/9.
 */
public class Contant {
    public static final String APPKEY = "0ea9db515854676cc3b8059966c77c2a";
    public static String[] URLS = new String[]{
        "http://api.huceo.com/wxnew/?key=($1)&num=($2)&page=($3)"
                ,"http://api.huceo.com/guonei/?key=($1)&num=($2)&page=($3)"
                ,"http://api.huceo.com/world/?key=($1)&num=($2)&page=($3)"
                ,"http://api.huceo.com/tiyu/?key=($1)&num=($2)&page=($3)"
                ,"http://api.huceo.com/keji/?key=($1)&num=($2)&page=($3)"
                ,"http://api.huceo.com/huabian/?key=($1)&num=($2)&page=($3)"};
    public static final int PAGE_SIZE = 20;
}
