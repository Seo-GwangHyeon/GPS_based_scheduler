package com.example.termproject;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.*;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddScheduleActivity extends AppCompatActivity  implements Button.OnClickListener
{
    public Button SaveButton,CancelButton,AddLocationButton;
    public EditText SchedultText;
    int address_used;
    int i=0;


    LocationManager locationManager;
    static public ArrayList<PendingIntent> pendingList =new ArrayList<PendingIntent>();


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

        if(!MainActivity.nowDB.equals("schedule"))
            AddLocationButton.setEnabled(false);

        SaveButton.setOnClickListener(this);
        CancelButton.setOnClickListener(this);
        AddLocationButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        // TextView textView1 = (TextView) findViewById(R.id.schedule_add_layout);
        if(MainActivity.nowDB == "schedule"){
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

                    // 근접 경보용------------------

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    PendingIntent intent1 = register(i,MapActivity.Glatitude,MapActivity.Glongtitude,String.valueOf(SchedultText.getText()),address,200,-1);
                    i++;


                    if(intent1!=null){
                        pendingList.add(intent1);
                    }

                    //---------------------

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
        }
        else{
            switch (view.getId())
            {
                case R.id.save : {  //저장부분
                    MainActivity.db = MainActivity.helper.getWritableDatabase();


                    String address = " ";
                    /*
                    if (address_used == 1) {

                        address = MapActivity.Gaddress;
                    }
                    else
                        address=" ";
                    */

                    ContentValues values = new ContentValues();
                    values.put("content", String.valueOf(SchedultText.getText()));

                    if(MainActivity.nowDB.equals("favorite1")){
                        address = setAdd(1);
                    }
                    else if(MainActivity.nowDB.equals("favorite2")){
                        address = setAdd(2);
                    }
                    else
                        address = setAdd(3);

                    values.put("address", address);
                    values.put("latitude", MapActivity.Glatitude);
                    values.put("longtitude", MapActivity.Glongtitude);

                    Log.v("addlocation", "value에 넣기");

                    // 근접 경보용------------------

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    PendingIntent intent1 = register(i,MapActivity.Glatitude,MapActivity.Glongtitude,String.valueOf(SchedultText.getText()),address,200,-1);
                    i++;


                    if(intent1!=null){
                        pendingList.add(intent1);
                    }

                    //---------------------

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

                    break;
                default:
                    Toast.makeText(this, "나머지", Toast.LENGTH_SHORT).show();
                    finish();
                    break;

            }
        }
        return;
    }

    /*
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

                // 근접 경보용------------------

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                PendingIntent intent1 = register(i,MapActivity.Glatitude,MapActivity.Glongtitude,String.valueOf(SchedultText.getText()),address,200,-1);
                i++;


                if(intent1!=null){
                    pendingList.add(intent1);
                }

                //---------------------

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
    */

    private PendingIntent register(int id, double latitude, double longtitude, String content,String address, float radius
            , long expiration) {

        Intent intent = new Intent("proximityAlert");
        intent.putExtra("id", id);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longtitude", longtitude);
        intent.putExtra("content", content);
        intent.putExtra("address",address);


        //인텐트 대기
        //조건에 맞을 때 실행
        // 여기서는 근접했을 때 실행
        //PendingIntent.FLAG_CANCEL_CURRENT 은 현재 등록된 것이 있으면 등록된것을
        //취소하고 현재 이것을 실행
        //조건이 맞으면 내가 받겠다.
        PendingIntent pendingIntent = PendingIntent.
                getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        locationManager.addProximityAlert(latitude, longtitude, radius, expiration, pendingIntent);


        return pendingIntent;
    }

    String setAdd(int a){
        String numStr2 = String.valueOf(a);
        String[] args = new String[] {numStr2};
        Cursor c = MainActivity.db.rawQuery("select address from favorites where _id = ?",args);
        c.moveToNext();
        String add =c.getString(0);
        return add;
    }




}