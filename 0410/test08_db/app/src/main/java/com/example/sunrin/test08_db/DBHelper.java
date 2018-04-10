package com.example.sunrin.test08_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sunrin on 2018-04-10.
 */

public class DBHelper extends SQLiteOpenHelper {
    static String DB_Name = "music_db";
    static String TABLE_NAME = "music";
    static int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_Name, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTB = "create table " + TABLE_NAME
                + "(_id integer primary key autoincrement, "
                + "title text, singer text, song text)";
        try {
            sqLiteDatabase.execSQL(createTB);
        } catch (Exception e) {
            Log.e("DBHelper", "Exception in Create Table");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table " + TABLE_NAME + ";");
        onCreate(sqLiteDatabase);
    }
}
