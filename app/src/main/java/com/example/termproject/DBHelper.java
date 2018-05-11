package com.example.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 사용자 on 2018-04-20.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,name, factory,version);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table if not exists schedule(" +
                "_id integer primary key autoincrement, " +
                "content text," +
                "address text,"+
                "latitude double,"+
                "longtitude double);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="drop table if exists schedule";
        System.out.println(sql);

    }


}
