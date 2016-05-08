package com.thinkman.thinknews.models;

import java.util.List;

/**
 * Created by wangx on 2016/5/8.
 */


public class NewsListModel {
    private int code;

    private String msg;

    private List<NewsModel> newslist ;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setNewslist(List<NewsModel> newslist){
        this.newslist = newslist;
    }
    public List<NewsModel> getNewslist(){
        return this.newslist;
    }

}


