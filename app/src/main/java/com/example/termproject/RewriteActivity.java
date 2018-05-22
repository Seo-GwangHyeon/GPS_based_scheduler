package com.example.termproject;



import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.util.List;

import static com.example.termproject.MainActivity.global_id;

public class RewriteActivity extends AppCompatActivity  implements Button.OnClickListener
{
    public Button CancelButton, EditCompleteButton,AddLocationButton;
    EditText editText1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewrite);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},0);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},0);

        }

        CancelButton=(Button) findViewById(R.id.cancel);
        EditCompleteButton=(Button) findViewById(R.id.edit_complete);
        AddLocationButton=(Button) findViewById(R.id.add_location);

        CancelButton.setOnClickListener(this);
        EditCompleteButton.setOnClickListener(this);
        AddLocationButton.setOnClickListener(this);

        editText1= (EditText) findViewById(R.id.edit_schedule);
        editText1.setText(DatacheckActivity.content);
    }

    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.cancel:
                editText1.setText("");
                finish();
                break ;
            case R.id.edit_complete:

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
                values.put("content", String.valueOf(editText1.getText()));
                values.put("address",address);
                values.put("latitude", MapActivity.Glatitude);
                values.put("longtitude", MapActivity.Glongtitude);

                String numStr2=String.valueOf(global_id);
                MainActivity.db.update("schedule",values,"_id=?", new String[] {numStr2});

                //로오딩
                MainActivity.helper = new DBHelper(RewriteActivity.this,"schedule.db",null,1);
                Cursor c = MainActivity.db.query("schedule",null,null,null,null,null,null,null);
                MainActivity.adapter = new SimpleCursorAdapter(RewriteActivity.this, android.R.layout.simple_list_item_2, c,
                        new String[] {"content","address"} , new int[] {android.R.id.text1, android.R.id.text2},0);
                MainActivity.list.setAdapter(MainActivity.adapter);
                editText1.setText("");
                String SQL="select content, address from schedule where _id = ?";
                String[] args = new String[] {numStr2};
                Cursor c1 = MainActivity.db.rawQuery(SQL,args);
                c1.moveToNext();
                DatacheckActivity.content =c1.getString(0);
                DatacheckActivity.address =c1.getString(1);

                DatacheckActivity.ContentView.setText(DatacheckActivity.content);
                DatacheckActivity.AddressView.setText(DatacheckActivity.address);
                MapActivity.Glatitude=0;
                MapActivity.Glongtitude=0;
                finish();
                break ;

            case R.id.add_location :

                Intent intent = new Intent(RewriteActivity.this, SelHowLocPopupActivity.class);
                startActivity(intent);

                break;
            default:
                Toast.makeText(this, "나머지", Toast.LENGTH_SHORT).show();
                finish();
                editText1.setText("");
                break;
        }
        return;
    }

}