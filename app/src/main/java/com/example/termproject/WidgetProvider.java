package com.example.termproject;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import static com.example.termproject.MainActivity.global_id;

public class WidgetProvider extends AppWidgetProvider {


    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context,intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        for(int i=0; i<appWidgetIds.length; i++){
            updataAppWidget(context,appWidgetManager,appWidgetIds[i]);
        }
    }

    public static void updataAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){

        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.activity_widget);
        //RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.content_main);

        updateViews.setTextViewText(R.id.widgettext,"여길 어째야 될까");

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        updateViews.setOnClickPendingIntent(R.id.mLayout,pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
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
