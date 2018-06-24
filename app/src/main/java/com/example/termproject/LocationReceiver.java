package com.example.termproject;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


/**
 * Created by HanQ on 2018-05-20.
 */

public class LocationReceiver extends BroadcastReceiver {

    public LocationReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {

            String content=intent.getStringExtra("content");
            String address=intent.getStringExtra("address");

            Toast.makeText(context,content,Toast.LENGTH_LONG).show();

           // MainActivity.instance.NotificationS(content,address);

        }

    }





}
