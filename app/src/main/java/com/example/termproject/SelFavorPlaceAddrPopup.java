package com.example.termproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SelFavorPlaceAddrPopup extends AppCompatActivity implements Button.OnClickListener{
    TextView ViewAddr;
    Button EditAddrMap;
    Button EditAddrComplete;
    Intent intent;
    String address;
    int tempInt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_edit_favorite_p_addr);


        ViewAddr=(TextView) findViewById(R.id.edit_fp_name);
        EditAddrMap=(Button) findViewById(R.id.button_fp_addr);
        EditAddrComplete=(Button) findViewById(R.id.button_fp_addr_complete);

        EditAddrMap.setOnClickListener(this);
        EditAddrComplete.setOnClickListener(this);
        intent=getIntent();

        tempInt= intent.getIntExtra("favorite_addr_num",0);


        String numStr2 = String.valueOf(tempInt);
        String SQL="select placename, address from favorites where _id = ?";
        String[] args = new String[] {numStr2};
        Cursor c = MainActivity.db.rawQuery(SQL,args);
        c.moveToNext();
        address =c.getString(1);


        //일단 TextView로 데이터베이스
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewAddr.setText(address);

    }

    @Override


    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_fp_addr:
            {//수정
                Intent mapintent= new Intent(SelFavorPlaceAddrPopup.this, MapActivity.class);
                startActivity(mapintent);
                address=MapActivity.Gaddress;

                break;
            }//end case
            case R.id.button_fp_addr_complete:
            {//수정완료
                ContentValues values = new ContentValues();
                values.put("address", address);
                values.put("longtitude",MapActivity.Glongtitude);
                values.put("latitude", MapActivity.Glatitude);
                String numStr2;
                numStr2 = String.valueOf(tempInt);
                MainActivity.db.update("favorites", values, "_id=?", new String[]{numStr2});

                MapActivity.Glongtitude=0;
                MapActivity.Glatitude=0;

                finish();

                break;
            }//end case

        }
    }
}

