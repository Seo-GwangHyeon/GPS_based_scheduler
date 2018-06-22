package com.example.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "gps_based_scheduler";
    private static final String frontOfSchedule ="create table if not exists ";

    //private static final String backOfSchedule
    private static final String backOfSchedule ="(" +
            "_id integer primary key autoincrement, " +
            "content text," +
            "address text,"+
            "latitude double,"+
            "longtitude double);";
    private static final String baseSchedule="schedule";
    private static final String favoriteSchedule1="favorite1";
    private static final String favoriteSchedule2="favorite2";
    private static final String favoriteSchedule3="favorite3";
    private static final String favorites="create table if not exists favorites"+"(" +
            "_id integer primary key, " +
            "placename text,"+
            "address text,"+
            "latitude double,"+
            "longtitude double);";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(frontOfSchedule+baseSchedule+backOfSchedule);

        db.execSQL(frontOfSchedule+favoriteSchedule1+backOfSchedule);
        db.execSQL(frontOfSchedule+favoriteSchedule2+backOfSchedule);
        db.execSQL(frontOfSchedule+favoriteSchedule3+backOfSchedule);

        db.execSQL(favorites);
        db.execSQL("");
        //db.insert
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists schedule");
        db.execSQL("drop table if exists favorite1");
        db.execSQL("drop table if exists favorite2");
        db.execSQL("drop table if exists favorite3");

        onCreate(db);
    }
}
