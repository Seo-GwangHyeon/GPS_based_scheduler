package com.example.termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

public class SelHowLocPopupActivity extends AppCompatActivity implements Button.OnClickListener{
    Button InpersonButton,RangeButton, NotButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sel_how_loc_popup_layout);

        InpersonButton = (Button) findViewById(R.id.inperon_button);
        RangeButton = (Button) findViewById(R.id.range_button);
        NotButton = (Button) findViewById(R.id.not_button);

        InpersonButton.setOnClickListener(this);
        RangeButton.setOnClickListener(this);
        NotButton.setOnClickListener(this);

    }


    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inperon_button:
                startActivity(new Intent(this,MapActivity.class));
                break;
            case R.id.range_button:


                break;
            case R.id.not_button:

                finish();
                break;


        }
    }

        }
