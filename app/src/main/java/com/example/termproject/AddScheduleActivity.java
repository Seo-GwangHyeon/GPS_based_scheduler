package com.example.termproject;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class AddScheduleActivity extends AppCompatActivity  implements Button.OnClickListener
{
    public Button SaveButton,CancelButton,AddLocationButton;
    public EditText SchedultText;//ㅇ
    TextView textView;
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
        textView=(TextView) findViewById(R.id.location_view);
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

                MainActivity.db.insert("schedule",null,values);



                //세팅부
                MainActivity.helper = new DBHelper(AddScheduleActivity.this,"schedule.db",null,1);
                Cursor c = MainActivity.db.query("schedule",null,null,null,null,null,null,null);
                MainActivity.adapter = new SimpleCursorAdapter(AddScheduleActivity.this, android.R.layout.simple_list_item_2, c,
                        new String[] {"content","address"} , new int[] {android.R.id.text1, android.R.id.text2},0);

                MainActivity.list.setAdapter(MainActivity.adapter);


                Toast.makeText(this, String.valueOf(SchedultText.getText())+"저장됨", Toast.LENGTH_SHORT).show();
                finish();
                break ;
            case R.id.cancel :

                finish();
                break ;
            case R.id.add_location :
                //여기에 장소 추가
               // Toast.makeText(this, "장소추가 버튼 클릭됨", Toast.LENGTH_SHORT).show();
                startLocationService();

                break;
            default:
                Toast.makeText(this, "나머지", Toast.LENGTH_SHORT).show();
                finish();
                break;

        }
        return;
    }
    public void startLocationService() {
        // 위치 관리자 객체 참조
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 위치 정보를 받을 리스너 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;

        try {
            // GPS를 이용한 위치 요청
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    minTime, minDistance, gpsListener);

            // 네트워크를 이용한 위치 요청
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    minTime, minDistance, gpsListener);

            // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();

                textView.setText("내 위치 : " + latitude + ", " + longitude);
                Toast.makeText(getApplicationContext(), "Last Known Location : " + "Latitude : " + latitude + "\nLongitude:" + longitude, Toast.LENGTH_LONG).show();
            }
        } catch(SecurityException ex) {
            ex.printStackTrace();
        }

     //   Toast.makeText(getApplicationContext(), "위치 확인이 시작되었습니다. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();

    }

    public class GPSListener implements LocationListener {
        /**
         * 위치 정보가 확인될 때 자동 호출되는 메소드
         */
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
            Log.v("GPSListener", msg);

            textView.setText("내 위치 : " + latitude + ", " + longitude);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }
}