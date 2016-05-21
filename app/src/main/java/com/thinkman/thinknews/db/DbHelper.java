package com.thinkman.thinknews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangx on 2016/5/17.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "favorites.db";
    public static final String TABLE_NAME_FAVORITES = "favorites";
    public static final int DB_VERSION = 1;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CTIME = "ctime";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PIC_URL = "picUrl";
    public static final String COLUMN_URL = "url";

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME , null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + TABLE_NAME_FAVORITES
                        +"("+ COLUMN_ID +" integer primary key AUTOINCREMENT" +
                        "," + COLUMN_CTIME + " text" +
                        "," + COLUMN_TITLE + " text" +
                        "," + COLUMN_DESCRIPTION + " text" +
                        ", " + COLUMN_PIC_URL + " text" +
                        ", " + COLUMN_URL + " text)"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FAVORITES);
        onCreate(db);
    }


}
