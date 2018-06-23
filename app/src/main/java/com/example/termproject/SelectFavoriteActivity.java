package com.example.termproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class SelectFavoriteActivity extends AppCompatActivity implements Button.OnClickListener {

    TextView favorite_name1, favorite_name2 , favorite_name3;
    Button favorite_name1_button, favorite_name2_button, favorite_name3_button;
    TextView favorite_address1,  favorite_address2,  favorite_address3;
    Button favorite_address1_button, favorite_address2_button, favorite_address3_button;
    String[] placename, address;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_favorite);

        placename= new String[4];
        address=new String[4];

        favorite_name1 = (TextView) findViewById(R.id.favorite_name1);
        favorite_name2 = (TextView) findViewById(R.id.favorite_name2);
        favorite_name3 = (TextView) findViewById(R.id.favorite_name3);

        favorite_name1_button = (Button) findViewById(R.id.favorite_name1_button);
        favorite_name2_button = (Button) findViewById(R.id.favorite_name2_button);
        favorite_name3_button = (Button) findViewById(R.id.favorite_name3_button);

        favorite_name1_button.setOnClickListener(this);
        favorite_name2_button.setOnClickListener(this);
        favorite_name3_button.setOnClickListener(this);

         favorite_address1 = (TextView) findViewById(R.id.favorite_address1);
         favorite_address2 = (TextView) findViewById(R.id.favorite_address2);
         favorite_address3 = (TextView) findViewById(R.id.favorite_address3);

         favorite_address1_button = (Button) findViewById(R.id.favorite_address1_button);
         favorite_address2_button = (Button) findViewById(R.id.favorite_address2_button);
         favorite_address3_button = (Button) findViewById(R.id.favorite_address3_button);

        favorite_address1_button.setOnClickListener(this);
        favorite_address2_button.setOnClickListener(this);
        favorite_address3_button.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setNameAddr(1);
        setNameAddr(2);
        setNameAddr(3);

        favorite_name1.setText(placename[1]);
        favorite_name2.setText(placename[2]);
        favorite_name3.setText(placename[3]);

        favorite_address1.setText(address[1]);
        favorite_address2.setText(address[2]);
        favorite_address3.setText(address[3]);


    }

    public void onClick(View view) {
        // TextView textView1 = (TextView) findViewById(R.id.schedule_add_layout);
        int sel =view.getId();
        if(sel == R.id.favorite_name1_button || sel == R.id.favorite_name2_button || sel == R.id.favorite_name3_button)
        {
            Intent intent = new Intent(SelectFavoriteActivity.this, SelFavorPlacenamePopup.class);
            int a=0;
            if(sel == R.id.favorite_name1_button)
                a=1;
            else if(sel == R.id.favorite_name2_button)
                a=2;
            else if(sel == R.id.favorite_name3_button)
                a=3;
            intent.putExtra("favorite_num",a);
            startActivity(intent);
        }
         else if(sel==R.id.favorite_address1_button || sel== R.id.favorite_address2_button || sel== R.id.favorite_address3_button)
         {
             int aa=0;
             if(sel == R.id.favorite_address1_button)
                 aa=1;
             else if(sel == R.id.favorite_address2_button)
                 aa=2;
             else if(sel == R.id.favorite_address3_button)
                 aa=3;

             Intent intent1 = new Intent(SelectFavoriteActivity.this, SelFavorPlaceAddrPopup.class);
             intent1.putExtra("favorite_addr_num",aa);
             startActivity(intent1);



         }//end address button


    }

    void setNameAddr(int a)
    {
        String numStr2 = String.valueOf(a);
        String SQL="select placename, address from favorites where _id = ?";
        String[] args = new String[] {numStr2};
        Cursor c = MainActivity.db.rawQuery(SQL,args);
        c.moveToNext();
        placename[a] =c.getString(0);
        address[a] =c.getString(1);
    }
}