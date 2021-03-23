package com.example.weatherreport;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class AirPollutionWidget extends AppWidgetProvider {
    private static final String ACTION_BTN = "ButtonClick";
    String formatDate;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("widgetupdate")) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.air_pollution_widget);
            ComponentName componentName = new ComponentName(context, AirPollutionWidget.class);
            this.onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
        }

        if(intent.getAction().equals(ACTION_BTN)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.air_pollution_widget);
            ComponentName componentName = new ComponentName(context, AirPollutionWidget.class);
            this.onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
            Toast.makeText(context, "업데이트 시간 " + formatDate, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
            String airGugun = sharedPreferences.getString("gugun", "0");
            String airState = sharedPreferences.getString("airState", "0");

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat formatNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            formatDate = formatNow.format(date);

            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.air_pollution_widget);

            Intent intentMain = new Intent(context, MainActivity.class);
            PendingIntent pendingMain = PendingIntent.getActivity(context, 0, intentMain, 0);

            views.setOnClickPendingIntent(R.id.air_pollution_widget, pendingMain);
            views.setTextViewText(R.id.air_gugun, airGugun);
            views.setTextViewText(R.id.air_state, airState);

            String pkg = context.getPackageName();
            int idFace = context.getResources().getIdentifier("air_face", "id", pkg);

            if(airState.equals("좋음")) {
                views.setImageViewResource(R.id.air_face, R.drawable.smileface_icon);
            }
            else if(airState.equals("보통")) {
                views.setImageViewResource(R.id.air_face, R.drawable.normalface_icon);
            }
            else if(airState.equals("나쁨")) {
                views.setImageViewResource(R.id.air_face, R.drawable.sadface_icon);
            }
            else if(airState.equals("매우나쁨")) {
                views.setImageViewResource(R.id.air_face, R.drawable.angryface_icon);
            }

            views.setInt(idFace, "setColorFilter", Color.WHITE);

            Intent intentBtn = new Intent(context, AirPollutionWidget.class).setAction(ACTION_BTN);
            PendingIntent pendingBtn = PendingIntent.getBroadcast(context, 0, intentBtn, 0);
            views.setOnClickPendingIntent(R.id.air_refresh, pendingBtn);
            views.setTextViewText(R.id.air_now, formatDate);

            long interval  = 1800000;
            Intent intentAlarm = new Intent(context, AirPollutionWidget.class);
            intentAlarm.setAction("widgetupdate");
            PendingIntent pendingAlarm = PendingIntent.getService(context, 0, intentAlarm, 0);
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), interval, pendingAlarm);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}

