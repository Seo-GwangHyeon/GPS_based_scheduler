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

import java.io.IOException;
import java.util.List;

public class AddScheduleActivity extends AppCompatActivity  implements Button.OnClickListener
{
    public Button SaveButton,CancelButton,AddLocationButton;
    public EditText SchedultText;
    int address_used;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},0);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},0);

        }
        address_used=0;
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
            case R.id.save : {  //저장부분
                MainActivity.db = MainActivity.helper.getWritableDatabase();

                String address = " ";
                if (address_used == 1) {

                    address = MapActivity.Gaddress;
                }
                else
                    address=" ";

                ContentValues values = new ContentValues();
                values.put("content", String.valueOf(SchedultText.getText()));

                values.put("address", address);
                values.put("latitude", MapActivity.Glatitude);
                values.put("longtitude", MapActivity.Glongtitude);

                Log.v("addlocation", "value에 넣기");

                MainActivity.db.insert(MainActivity.nowDB, null, values);


                //세팅부
                MainActivity.helper = new DBHelper(AddScheduleActivity.this, MainActivity.nowDB+".db", null, 1);
                Cursor c = MainActivity.db.query(MainActivity.nowDB, null, null, null, null, null, null, null);
                MainActivity.adapter = new SimpleCursorAdapter(AddScheduleActivity.this, android.R.layout.simple_list_item_2, c,
                        new String[]{"content", "address"}, new int[]{android.R.id.text1, android.R.id.text2}, 0);

                MainActivity.list.setAdapter(MainActivity.adapter);

                //Log.v("location", (String) values.get("latitude"));
                //  Log.v("addlocation","세팅도 됨");

                Toast.makeText(this, String.valueOf(SchedultText.getText()) + "저장됨", Toast.LENGTH_SHORT).show();
                MapActivity.Glatitude = 0;
                MapActivity.Glongtitude = 0;
                finish();
                break;
            }
            case R.id.cancel :

                finish();
                break ;
            case R.id.add_location :
                address_used=1;
                Intent intent = new Intent(AddScheduleActivity.this, SelHowLocPopupActivity.class);
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