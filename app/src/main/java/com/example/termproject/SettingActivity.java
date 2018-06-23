package com.example.termproject;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;


public class SettingActivity extends AppCompatActivity {

    Toolbar myToolbar;
    public static Switch frequent;
    public static Switch vibration;
    boolean setSetting;
    boolean frequent_check;
    boolean vibration_check;

    private SharedPreferences appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        appData = getSharedPreferences("appData", MODE_PRIVATE);

        load();
        frequent=(Switch) findViewById(R.id.frequent_switch);
        vibration=(Switch) findViewById(R.id.vibration_switch);

        if (setSetting) {
            vibration.setChecked(vibration_check);
            frequent.setChecked(frequent_check);
        }


        vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //버튼누르면 설정 변경
                save();
            }
        });

        frequent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                save();
            }
        });

        getSupportActionBar().setTitle("Setting");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF3F51B5));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;

    }

    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("setSetting", true);
        editor.putBoolean("frequent",  frequent.isChecked());
        editor.putBoolean("vibration", vibration.isChecked());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        setSetting = appData.getBoolean("setSetting", false);
        frequent_check = appData.getBoolean("frequent", false);
        vibration_check = appData.getBoolean("vibration", false);
    }


}
