package com.example.termproject;

import android.app.LauncherActivity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import java.util.ArrayList;

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


        return (new StackRemoteViewsFactory(this.getApplicationContext(),intent));
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{
    private Context mContext;
    private int appWidgetId;
    public static String content;
    public static RemoteViews remoteViews;

    public StackRemoteViewsFactory(Context context,Intent intent){
        mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return MainActivity.list.getCount();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public RemoteViews getViewAt(int position) {
        remoteViews = new RemoteViews(mContext.getPackageName(),R.layout.item);
        //remoteViews.setTextViewText(R.id.widget_item,"Sample"+String.valueOf(position));

        String numStr2 = String.valueOf(position+1);
        String SQL="select content from schedule where _id = ?";
        String[] args = new String[] {numStr2};
        Cursor c = MainActivity.db.rawQuery(SQL,args);
        c.moveToNext();
        content =c.getString(0);

        remoteViews.setTextViewText(R.id.widget_item,content);

        /*
        Intent fillIntent = new Intent();
        fillIntent.setData(Uri.parse("http://www.google.com"));
        remoteViews.setOnClickFillInIntent(R.id.widget_item,fillIntent);
        */

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}