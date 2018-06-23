package com.example.termproject;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;



public class SelFavorPlacenamePopup extends AppCompatActivity implements Button.OnClickListener{

    EditText fpNameEdit;
    Button fpNameButton;
    Intent intent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_edit_favorite_pname);

        fpNameEdit=(EditText) findViewById(R.id.edit_fp_name);
        fpNameButton=(Button) findViewById(R.id.button_fp_name);
      //  fpNameEdit.setOnClickListener(this);
        fpNameButton.setOnClickListener(this);

        intent=getIntent();

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
            case R.id.button_fp_name: {
                int a = intent.getIntExtra("favorite_num", 0);
                String tempString = String.valueOf(fpNameEdit.getText());
                ContentValues values = new ContentValues();
                values.put("placename", tempString);
                String numStr2;
                numStr2 = String.valueOf(a);
                MainActivity.db.update("favorites", values, "_id=?", new String[]{numStr2});
                fpNameEdit.setText("");
                finish();
            }//end case

        }
    }
}

