package com.example.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



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
       /* switch (oldVersion) {
            case 1 :
                try {
                    db.beginTransaction();
                    db.execSQL("ALTER TABLE schedule ADD COLUMN lattitude double");
                    db.execSQL("ALTER TABLE schedule ADD COLUMN longtitude double");
                    db.setTransactionSuccessful();
                } catch (IllegalStateException e) {

                } finally {
                    db.endTransaction();
                };
                break;
        }*/
    }
}
