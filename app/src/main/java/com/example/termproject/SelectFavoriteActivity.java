package com.example.termproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SelectFavoriteActivity extends AppCompatActivity {
    public static ListView list;
    DBHelper helper;
    SQLiteDatabase db;
    SimpleCursorAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_favorite);

  //      list= (ListView) findViewById(R.id.schedule_list);
/*
        adapter=null;
        helper=new DBHelper(SelectFavoriteActivity.this,"favorites.db",null,1);
        db=helper.getWritableDatabase();
        helper.onCreate(db);

        db.execSQL("select count(*) from favorites");
*/
// DB에다가 Default로 장소 들의 초기 값을 넣어줘야한다. 어떻게 넣지..
     /*   ContentValues values = new ContentValues();
        values.put("_id", 1);
        values.put("content", String.valueOf(SchedultText.getText()));
        values.put("address", address);
        values.put("latitude", MapActivity.Glatitude);
        values.put("longtitude", MapActivity.Glongtitude);
        MainActivity.db.insert(MainActivity.nowDB, null, values);
*/
        Cursor c = db.query("favorites",null,null,null,null,null,null,null);
        adapter = new SimpleCursorAdapter(SelectFavoriteActivity.this, android.R.layout.list_content, c,
                new String[] {"name","address"} , new int[] {android.R.id.text1, android.R.id.text2},0);
        list.setAdapter(adapter);
    }
}
