package com.example.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class RangePopupActivity extends AppCompatActivity implements Button.OnClickListener{


    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_range_popup);


            }


    public boolean onTouchEvent(MotionEvent event) {
            //바깥레이어 클릭시 안닫히게
            if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
            }
            return true;
            }

    public void onClick(View view) {
        /*switch (view.getId()) {
            case R.id.inperon_button:
            break;
            case R.id.range_button:


            break;
            case R.id.not_button:

            finish();
            break;


            }*/
    }
}