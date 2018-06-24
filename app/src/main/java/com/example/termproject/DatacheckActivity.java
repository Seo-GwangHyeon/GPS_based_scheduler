package com.example.termproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import static com.example.termproject.MainActivity.global_id;

public class DatacheckActivity extends AppCompatActivity {
    Toolbar myToolbar;
    public static String content;
    public static String address;
    public static  TextView AddressView;
    public static TextView ContentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datacheck);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("확인");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF3F51B5));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button editButton = (Button) findViewById(R.id.edit_button);
        Button delButton = (Button) findViewById(R.id.delete_button);
        AddressView = (TextView) findViewById(R.id.view_content);
        ContentView = (TextView) findViewById(R.id.view_address);


        String numStr2 = String.valueOf(global_id);
        String SQL="select content, address from "+MainActivity.nowDB +" where _id = ?";
        String[] args = new String[] {numStr2};
        Cursor c = MainActivity.db.rawQuery(SQL,args);
        c.moveToNext();
        content =c.getString(0);
        address =c.getString(1);

        //

        ContentView.setText(content);
        AddressView.setText(address);
       // Toast.makeText(this, content, Toast.LENGTH_SHORT).show();

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(DatacheckActivity.this,RewriteActivity.class);
                startActivity(intent1);

        }});
        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String numStr2 = String.valueOf(global_id);
                MainActivity.db.delete(MainActivity.nowDB,"_id=?", new String[] {numStr2});

                //로딩
                MainActivity.helper = new DBHelper(DatacheckActivity.this,MainActivity.nowDB+".db",null,1);
                Cursor c = MainActivity.db.query(MainActivity.nowDB,null,null,null,null,null,null,null);
                MainActivity.adapter = new SimpleCursorAdapter(DatacheckActivity.this, android.R.layout.simple_list_item_2, c,
                        new String[] {"content","address"} , new int[] {android.R.id.text1, android.R.id.text2},0);
                MainActivity.list.setAdapter(MainActivity.adapter);
                finish();
                Toast.makeText(DatacheckActivity.this, "삭제됨", Toast.LENGTH_SHORT).show();
            }});
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        ContentView.setText("");
        AddressView.setText("");
        super.onBackPressed();
        return true;

    }
}
