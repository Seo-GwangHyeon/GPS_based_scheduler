package com.example.termproject;



import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.*;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class AddScheduleActivity extends AppCompatActivity  implements Button.OnClickListener
{
    public Button SaveButton,CancelButton,AddLocationButton;
    public EditText SchedultText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add);

        SaveButton =(Button) findViewById(R.id.save);
        CancelButton=(Button) findViewById(R.id.cancel);
        SchedultText=(EditText) findViewById(R.id.add_schedule);
        AddLocationButton=(Button) findViewById(R.id.add_location);


        SaveButton.setOnClickListener(this);
        CancelButton.setOnClickListener(this);
        AddLocationButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        // TextView textView1 = (TextView) findViewById(R.id.schedule_add_layout);
        switch (view.getId())
        {
            case R.id.save :
                //저장부분
                MainActivity.db = MainActivity.helper.getWritableDatabase();

                ContentValues values=new ContentValues();
                values.put("content", String.valueOf(SchedultText.getText()));
                values.put("address","부산시");
                values.put("latitude", MapActivity.Glatitude);
                values.put("longtitude", MapActivity.Glongtitude);


                MainActivity.db.insert("schedule",null,values);

                //세팅부
                MainActivity.helper = new DBHelper(AddScheduleActivity.this,"schedule.db",null,1);
                Cursor c = MainActivity.db.query("schedule",null,null,null,null,null,null,null);
                MainActivity.adapter = new SimpleCursorAdapter(AddScheduleActivity.this, android.R.layout.simple_list_item_2, c,
                        new String[] {"content","address"} , new int[] {android.R.id.text1, android.R.id.text2},0);

                MainActivity.list.setAdapter(MainActivity.adapter);

                Log.v("lcation", (String) values.get("latitude"));

                Toast.makeText(this, String.valueOf(SchedultText.getText())+"저장됨", Toast.LENGTH_SHORT).show();

                finish();
                break ;
            case R.id.cancel :

                finish();
                break ;
            case R.id.add_location :
                Intent intent= new Intent(AddScheduleActivity.this, MapActivity.class);
                startActivity(intent);

                break;
            default:
                Toast.makeText(this, "나머지", Toast.LENGTH_SHORT).show();
                finish();
                break;

        }
        return;
    }

}