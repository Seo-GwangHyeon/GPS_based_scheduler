package com.example.termproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, Button.OnClickListener {

    GoogleMap googleMap;
    Button LocationInputBtn;
    MapFragment mapFragment;
    public static double Glatitude;
    public static double Glongtitude;
    public static String Gaddress;
    public static Geocoder geocoder;
    LocationManager manager;
    GPSListener gpsListener;
    LatLng curPoint;
    Location lastLocation;
    MarkerOptions optFirst;
    int mark_count;
    EditText AddressInput;
 //   Button SearchButton;
    int gpsEnable;
    Marker temp;

    final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Glatitude = 0;
        Glongtitude = 0;
        gpsEnable = 1;

        mark_count = 0;
        LocationInputBtn = (Button) findViewById(R.id.location_input_button);
        LocationInputBtn.setOnClickListener(this);
        AddressInput = (EditText) findViewById(R.id.address_text);
        //SearchButton = (Button) findViewById(R.id.search_button);
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
        curPoint = new LatLng(37.555744, 126.970431);
        optFirst = new MarkerOptions();
        optFirst.position(curPoint);
        gpsListener = new GPSListener();
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        startLocationService();

        AddressInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(MapActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

                    //intent.

                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                Gaddress = (String) place.getAddress();
                Glatitude = place.getLatLng().latitude;
                Glongtitude = place.getLatLng().longitude;

                MarkerOptions mOption = new MarkerOptions();
                mOption.title((String)place.getName());
                mOption.position(place.getLatLng());
                mOption.snippet(Gaddress);
                temp.remove();
                optFirst = mOption;
                optFirst.draggable(true);

                temp = googleMap.addMarker(optFirst);
                temp.showInfoWindow();

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));

                //Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                //Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled operation.
            }
        }
    }

    public boolean startLocationService() {

        long minTime = 10;
        float minDistance = 0;
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
            return false;
        }
        else {
            try {

                // GPS를 이용한 위치 요청
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        minTime, minDistance, gpsListener);

                // 네트워크를 이용한 위치 요청
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        minTime, minDistance, gpsListener);
                // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인

                lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastLocation != null) {
                    Glatitude = lastLocation.getLatitude();
                    Glongtitude = lastLocation.getLongitude();


                }
            } catch (SecurityException ex) {
                ex.printStackTrace();
            }
            return true;
            //   Toast.makeText(getApplicationContext(), "위치 확인이 시작되었습니다. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();
        }
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
    }

    public class GPSListener implements LocationListener {
        //위치 정보가 확인될 때 자동 호출되는 메소드

        public void onLocationChanged(Location location) {
            if(gpsEnable==1) {
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();

                String msg = "Latitude : " + latitude + "\nLongitude:" + longitude;
                //Log.v("GPSListener", msg);
                Glatitude = latitude;
                Glongtitude = longitude;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                showCurrentlocation(latitude, longitude);
                gpsEnable=0;

            }
            else
            {
                manager.removeUpdates(gpsListener);
            }
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {


        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }

    private void showCurrentlocation(Double latitude, Double longitude) {
        curPoint = new LatLng(latitude, longitude);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        //마커추가
        List<Address> addressList = null;
        try {
            // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
            addressList =  geocoder.getFromLocation(latitude,longitude, 10); // 최대 검색 결과 개수
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // System.out.println(addressList.get(0).toString());
        // 콤마를 기준으로 split

        Address a=addressList.get(0);
        String []splitStr = a.toString().split(",");
        int countCountry=a.getCountryName().length();
        Gaddress = splitStr[0].substring(splitStr[0].indexOf("\"") +countCountry+2,splitStr[0].length() - 2); // 주소
        String feature = a.getFeatureName();

        optFirst.position(curPoint);// 위도 • 경도
        optFirst.title(feature);// 제목 미리보기
        optFirst.snippet(Gaddress);
        temp=googleMap.addMarker(optFirst);
        temp.showInfoWindow();
        //optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_back));

    }
    public void onMapReady(GoogleMap map) {//맵의 onCreate 쯤된다.
        googleMap = map;
        geocoder = new Geocoder(this);

        // Toast.makeText(this, "set map", Toast.LENGTH_SHORT).show();

        setUpMap();

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {

                // 마커 타이틀
                Glatitude = point.latitude; // 위도
                Glongtitude = point.longitude; // 경도

                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocation(Glatitude, Glongtitude, 10); // 최대 검색 결과 개수
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // System.out.println(addressList.get(0).toString());
                // 콤마를 기준으로 split
                Address a = addressList.get(0);
                String[] splitStr = a.toString().split(",");
                int countCountry = a.getCountryName().length();
                Gaddress = splitStr[0].substring(splitStr[0].indexOf("\"") + countCountry + 2, splitStr[0].length() - 2); // 주소
                String feature = a.getFeatureName();

                MarkerOptions tempMark = new MarkerOptions();
                tempMark.title(feature);
                // 마커의 스니펫(간단한 텍스트) 설정
                tempMark.snippet(Gaddress);
                // LatLng: 위도 경도 쌍을 나타냄
                tempMark.position(point);
                // 마커(핀) 추가
                //if(optFirst.)
                //curPoint.
                //googleMap.clear();
                temp.remove();
                optFirst = tempMark;
                optFirst.draggable(true);

                temp = googleMap.addMarker(optFirst);
                temp.showInfoWindow();
            }
        });




    }


    public void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);

        }
        else {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
        }
    }
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},0);
            // googleMap.setMyLocationEnabled(true);
        }
    }

    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},0);
            // googleMap.setMyLocationEnabled(false);
        }


    }
    public void onBackPressed()
    {
        Glatitude=0;
        Gaddress="";
        Glongtitude=0;


        super.onBackPressed();
    }
}