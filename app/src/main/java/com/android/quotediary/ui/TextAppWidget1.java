package com.android.quotediary.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.quotediary.R;

import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class TextAppWidget1 extends AppWidgetProvider {
    public static String ACTION_AUTO_UPDATE_WIDGET = "ACTION_AUTO_UPDATE_WIDGET";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.text_app_widget1);
        Calendar calendar  = Calendar.getInstance();
        views.setTextViewText(R.id.appwidget_text, calendar.getTime().getMinutes()+"");

        Toast.makeText(context,"UpdateAppWidget",Toast.LENGTH_SHORT).show();
//        appWidgetManager.getAppWidgetInfo(appWidgetId).updatePeriodMillis = (int) (calendar.getTimeInMillis());
//        Calendar calendar  = Calendar.getInstance();
//        views.setTextViewText(appWidgetId,calendar.getTime().toString());

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        final Intent intent = new Intent(context, TextAppWidget1.class);
        final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
        final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pending);
        long interval = 1000*60;
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),interval, pending);
        appWidgetManager.partiallyUpdateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Toast.makeText(context,"onUpdate",Toast.LENGTH_SHORT).show();
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context,"onEnable",Toast.LENGTH_SHORT).show();
//        Intent  intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//        int[] ids = AppWidgetManager.getInstance(context)
//                .getAppWidgetIds(new ComponentName(context, TextAppWidget1.class));
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.HOUR_OF_DAY, 0);
//        c.set(Calendar.MINUTE, 0);
//        c.set(Calendar.SECOND, 0);
//        c.set(Calendar.MILLISECOND, 1);
//
//
//        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmMgr.setInexactRepeating(AlarmManager.RTC, 0, 1000, alarmIntent);


        final Intent intent = new Intent(context, TextAppWidget1.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, TextAppWidget1.class));
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        final PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
        final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pending);
        long interval = 1000*60;
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),interval, pending);
    
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context,"onDisabled",Toast.LENGTH_SHORT).show();
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Toast.makeText(context,"onAppWidgetOptionsChanged",Toast.LENGTH_SHORT).show();
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"onReceive",Toast.LENGTH_SHORT).show();
        super.onReceive(context, intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Toast.makeText(context,"onDeleted",Toast.LENGTH_SHORT).show();
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        Toast.makeText(context,"onRestored",Toast.LENGTH_SHORT).show();
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
}