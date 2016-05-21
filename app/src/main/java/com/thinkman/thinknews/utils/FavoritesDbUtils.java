package com.thinkman.thinknews.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thinkman.thinknews.db.DbHelper;
import com.thinkman.thinknews.models.NewsModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangx on 2016/5/21.
 */
public class FavoritesDbUtils {
    public static long insertFavorite(Context context, NewsModel news) {

        if (isExist(context, news)) {
            return -2;
        }

        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = null;
        long nRet = -2;

        try {
            db = helper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DbHelper.COLUMN_CTIME, news.getCtime());
            contentValues.put(DbHelper.COLUMN_TITLE, news.getTitle());
            contentValues.put(DbHelper.COLUMN_DESCRIPTION, news.getDescription());
            contentValues.put(DbHelper.COLUMN_PIC_URL, news.getPicUrl());
            contentValues.put(DbHelper.COLUMN_URL, news.getUrl());

            nRet = db.insert(DbHelper.TABLE_NAME_FAVORITES, null, contentValues);
        } catch (Exception ex) {

        } finally {
            db.close();

            return nRet;
        }
    }

    public List<NewsModel> getFavorite(Context context, int nOffset, int nLimit) {

        ArrayList<NewsModel> listNews = new ArrayList<NewsModel>();

        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        boolean bRet = false;

        try {
            db = helper.getReadableDatabase();
            cursor = db.rawQuery("select * from " + DbHelper.TABLE_NAME_FAVORITES + " order by " +DbHelper.COLUMN_ID+ " desc limit ? offset ?"
                    , new String[]{""+nLimit, ""+nOffset});

            if (null == cursor) {
                return listNews;
            }

            cursor.moveToFirst();
            while (false == cursor.isAfterLast()) {
                NewsModel news = new NewsModel();

                news.setCtime(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_CTIME)));
                news.setTitle(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_TITLE)));
                news.setDescription(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_DESCRIPTION)));
                news.setPicUrl(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_PIC_URL)));
                news.setUrl(cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_URL)));

                listNews.add(news);
                cursor.moveToNext();
            }

        } catch (Exception ex) {

        } finally {
            if (null != cursor) {
                cursor.close();
            }

            if (null != db) {
                db.close();
            }

            return listNews;
        }
    }

    public static boolean isExist(Context context, NewsModel news) {

        DbHelper helper = new DbHelper(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        boolean bRet = false;

        try {

            db = helper.getReadableDatabase();
            cursor = db.rawQuery("select * from " + DbHelper.TABLE_NAME_FAVORITES + " where url=?", new String[]{news.getUrl()});
            if (cursor.getCount() > 0) {
                bRet = true;
            } else {
                bRet = false;
            }

        } catch (Exception ex) {

        } finally {
            if (null != cursor) {
                cursor.close();
            }

            if (null != db) {
                db.close();
            }

            return bRet;
        }
    }

    public static long delete(Context context, NewsModel news) {

        return -1;
    }
}
