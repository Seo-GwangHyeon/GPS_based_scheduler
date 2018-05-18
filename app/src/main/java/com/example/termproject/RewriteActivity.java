package com.example.termproject;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.util.List;

import static com.example.termproject.MainActivity.global_id;

public class RewriteActivity extends AppCompatActivity  implements Button.OnClickListener
{
    public Button CancelButton, EditCompleteButton;
    EditText editText1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewrite);

        CancelButton=(Button) findViewById(R.id.cancel);
        EditCompleteButton=(Button) findViewById(R.id.edit_complete);

        CancelButton.setOnClickListener(this);
        EditCompleteButton.setOnClickListener(this);

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
                ContentValues values=new ContentValues();

                values.put("content", String.valueOf(editText1.getText()));
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
                finish();
                break ;
            default:
                Toast.makeText(this, "나머지", Toast.LENGTH_SHORT).show();
                finish();
                editText1.setText("");
                break;
        }
        return;
    }

}