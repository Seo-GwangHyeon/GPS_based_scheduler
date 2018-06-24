package com.example.termproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NavigationView.OnCreateContextMenuListener {
    public static DBHelper helper;
    public static SQLiteDatabase db;
    public static ListView list;
    public static SimpleCursorAdapter adapter;
    public static int global_id;
    public static String nowDB;// 현재 참조 데이터 베이스
    public static MenuItem[] items;
    private Menu baseMenu;
    public static MainActivity instance;

    public void NotificationS(String address,String content){
        Bitmap mLargeIconForNo=
                BitmapFactory.decodeResource(getResources(),R.drawable.ic_stat_name);

        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,0,
                new Intent(getApplicationContext(),MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle(address)
                        .setContentText(content)
                        .setTicker(content)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setLargeIcon(mLargeIconForNo)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(contentIntent);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1234,builder.build());

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter=null;
        nowDB="schedule";
        helper=new DBHelper(MainActivity.this,nowDB+".db",null,1);
        db=helper.getWritableDatabase();
        helper.onCreate(db);

        list= (ListView) findViewById(R.id.schedule_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        items=new MenuItem[4];
        //여기 로딩 부분
       // helper = new DBHelper(MainActivity.this,nowDB+".db",null,1);
        Cursor c = db.query(nowDB,null,null,null,null,null,null,null);
        adapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_list_item_2, c,
                new String[] {"content","address"} , new int[] {android.R.id.text1, android.R.id.text2},0);
        list.setAdapter(adapter);

        //여기까지 로딩부분
        //추가 버튼

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, AddScheduleActivity.class);
                startActivity(intent);

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                global_id=(int)id;
               // String a=Integer.toString(global_id);
                //Toast.makeText(MainActivity.this, a, Toast.LENGTH_SHORT).show();
               Intent intent= new Intent(MainActivity.this, DatacheckActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       Menu tempMenu = navigationView.getMenu();
        items[1]=(MenuItem) tempMenu.getItem(1);
        items[2]=(MenuItem) tempMenu.getItem(2);
        items[3]=(MenuItem) tempMenu.getItem(3);
    }//end OnCreate---------------------------------------------------------------------------

    public void onResume() {
        super.onResume();

        setPlaceName(1);
        setPlaceName(2);
        setPlaceName(3);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 //   onCreateNavigationItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //여기에 세팅 레이아웃 넣는다.
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id)
        {
            case  R.id.home:
            {
                nowDB="schedule";

                break;
            }

            case  R.id.favorite1:
            {
                nowDB="favorite1";
                // item.setTitle("aaa");
                break;
            }
            case  R.id.favorite2:
            {
                nowDB="favorite2";
                break;
            }
            case  R.id.favorite3:
            {
                nowDB="favorite3";
                break;
            }
            case  R.id.favorite_manage:
            {
                Intent intent= new Intent(MainActivity.this, SelectFavoriteActivity.class);
                startActivity(intent);
                break;
            }
            default:
            {
                break;
            }


        }//end switch

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        helper = new DBHelper(MainActivity.this,nowDB+".db",null,1);
        db=helper.getWritableDatabase();
        helper.onCreate(db);

        Cursor c = db.query(nowDB,null,null,null,null,null,null,null);
        adapter = new SimpleCursorAdapter(MainActivity.this, android.R.layout.simple_list_item_2, c,
                new String[] {"content","address"} , new int[] {android.R.id.text1, android.R.id.text2},0);
        list.setAdapter(adapter);




        return true;
    }



    void setPlaceName(int a)
    {
        String numStr2 = String.valueOf(a);
        String[] args = new String[] {numStr2};
        Cursor c = MainActivity.db.rawQuery("select placename from favorites where _id = ?",args);
        c.moveToNext();
        String name =c.getString(0);
        items[a].setTitle(name);
    }
}
