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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},0);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},0);

        }

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

                //adress 추가
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList =  MapActivity.geocoder.getFromLocation(MapActivity.Glatitude,MapActivity.Glongtitude, 10); // 최대 검색 결과 개수
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                Address a=addressList.get(0);
                String []splitStr = a.toString().split(",");
                int countCountry=a.getCountryName().length();
                //Toast.makeText(this, countCountry, Toast.LENGTH_SHORT).show();
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") +countCountry+2,splitStr[0].length() - 2); // 주소

                //-----------address 출력부

                ContentValues values=new ContentValues();
                values.put("content", String.valueOf(SchedultText.getText()));
                values.put("address",address);
                values.put("latitude", MapActivity.Glatitude);
                values.put("longtitude", MapActivity.Glongtitude);


                Log.v("addlocation","value에 넣기");

                MainActivity.db.insert("schedule",null,values);


                //세팅부
                MainActivity.helper = new DBHelper(AddScheduleActivity.this,"schedule.db",null,1);
                Cursor c = MainActivity.db.query("schedule",null,null,null,null,null,null,null);
                MainActivity.adapter = new SimpleCursorAdapter(AddScheduleActivity.this, android.R.layout.simple_list_item_2, c,
                        new String[] {"content","address"} , new int[] {android.R.id.text1, android.R.id.text2},0);

                MainActivity.list.setAdapter(MainActivity.adapter);

                //Log.v("location", (String) values.get("latitude"));
                Log.v("addlocation","세팅도 됨");

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