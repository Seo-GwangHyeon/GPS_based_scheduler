package com.example.termproject;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, Button.OnClickListener {

  GoogleMap googleMap;
  Button LocationInputBtn;
  MapFragment mapFragment;
  public static double Glatitude;
  public static double Glongtitude;
    LocationManager manager;
    GPSListener gpsListener;

    protected void onCreate(Bundle savedInstanceState) {
        Glatitude=0;
        Glongtitude=0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        LocationInputBtn=(Button) findViewById(R.id.location_input_button);
        LocationInputBtn.setOnClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},0);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},0);
        }

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        startLocationService();

    }


    public void onMapReady(GoogleMap map) {
        googleMap = map;
       // Toast.makeText(this, "set map", Toast.LENGTH_SHORT).show();
        setUpMap();

    }

    public void setUpMap() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }
    public void startLocationService() {
        // 위치 관리자 객체 참조
         manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 위치 정보를 받을 리스너 생성
        gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );

        }
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

            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }

        //   Toast.makeText(getApplicationContext(), "위치 확인이 시작되었습니다. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.location_input_button :
                // 버튼누르겨 화면 끄고 나서
                // 더블로 롱티듀드 래티듀드 받아옴
                //mapFragment.onStop();
               // Toast.makeText(this, "제대로", Toast.LENGTH_SHORT).show();
                manager.removeUpdates(gpsListener);
                super.onStop();
                finish();
                break;
            default:
                Toast.makeText(this, "나머지", Toast.LENGTH_SHORT).show();
                finish();
                break;

        }
        return;
    }

    public class GPSListener implements LocationListener {
        //위치 정보가 확인될 때 자동 호출되는 메소드

        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "Latitude : " + latitude + "\nLongitude:" + longitude;
            //Log.v("GPSListener", msg);
            Glatitude=latitude;
            Glongtitude=longitude;
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            showCurrentlocation(latitude, longitude);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    private void showCurrentlocation(Double latitude, Double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        //마커추가
       MarkerOptions optFirst = new MarkerOptions();
        optFirst.position(curPoint);// 위도 • 경도
        optFirst.title("현재 위치");// 제목 미리보기
        optFirst.snippet("Snippet");
        //optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_back));
        googleMap.addMarker(optFirst).showInfoWindow();


    }

    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            googleMap.setMyLocationEnabled(true);
        }
    }

    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            googleMap.setMyLocationEnabled(false);
        }


    }
}


