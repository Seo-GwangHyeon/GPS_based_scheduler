package com.example.termproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context,intent);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for(int i=0; i<appWidgetIds.length; ++i){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_provider);
            Intent intent = new Intent(context,WidgetService.class);
            //remoteViews.setRemoteAdapter(R.id.list_view,intent);
            remoteViews.setRemoteAdapter(appWidgetIds[i],R.id.list_view,intent);
            remoteViews.setEmptyView(R.id.list_view,R.id.empty_view);

            Intent itemIntent = new Intent(Intent.ACTION_VIEW);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0,itemIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setPendingIntentTemplate(R.id.list_view,pendingIntent);

            appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,getClass()));

            appWidgetManager.updateAppWidget(appWidgetIds[i],remoteViews);
        }
    }

    @Override
    public void onEnabled(Context context){
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context){
        super.onDisabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds){
        super.onDeleted(context,appWidgetIds);
    }
}

