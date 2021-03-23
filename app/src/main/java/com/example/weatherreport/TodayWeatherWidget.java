package com.example.weatherreport;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */

public class TodayWeatherWidget extends AppWidgetProvider {
    private static final String ACTION_BTN = "ButtonClick";
    String formatDate;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("widgetupdate")) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.today_weather_widget);
            ComponentName componentName = new ComponentName(context, TodayWeatherWidget.class);
            this.onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
        }

        if(intent.getAction().equals(ACTION_BTN)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.today_weather_widget);
            ComponentName componentName = new ComponentName(context, TodayWeatherWidget.class);
            this.onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
            Toast.makeText(context, "업데이트 시간 " + formatDate, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
            String todayGugun = sharedPreferences.getString("gugun", "0");
            String todayWeather = sharedPreferences.getString("weather", "0");
            String todayTemp = sharedPreferences.getString("temp", "0");
            String todayPm10 = sharedPreferences.getString("pm10", "0");
            String todayPm25 = sharedPreferences.getString("pm25", "0");
            byte[] decoder = Base64.decode(todayWeather, Base64.NO_WRAP);
            Bitmap weatherBitmap = BitmapFactory.decodeByteArray(decoder, 0, decoder.length);

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat formatNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            formatDate = formatNow.format(date);

            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.today_weather_widget);

            Intent intentMain = new Intent(context, MainActivity.class);
            PendingIntent pendingMain = PendingIntent.getActivity(context, 0, intentMain, 0);

            String pkg = context.getPackageName();
            int idWeather = context.getResources().getIdentifier("today_weather", "id", pkg);

            views.setOnClickPendingIntent(R.id.today_weather_widget, pendingMain);
            views.setTextViewText(R.id.today_gugun, todayGugun);
            views.setImageViewBitmap(R.id.today_weather, weatherBitmap);
            views.setTextViewText(R.id.today_temp, todayTemp);
            views.setTextViewText(R.id.today_pm10, todayPm10);
            views.setTextViewText(R.id.today_pm25, todayPm25);
            views.setInt(idWeather, "setColorFilter", Color.WHITE);

            Intent intentBtn = new Intent(context, TodayWeatherWidget.class).setAction(ACTION_BTN);
            PendingIntent pendingBtn = PendingIntent.getBroadcast(context, 0, intentBtn, 0);
            views.setOnClickPendingIntent(R.id.today_refresh, pendingBtn);
            views.setTextViewText(R.id.today_now, formatDate);

            long interval  = 1800000;
            Intent intentAlarm = new Intent(context, TodayWeatherWidget.class);
            intentAlarm.setAction("widgetupdate");
            PendingIntent pendingAlarm = PendingIntent.getService(context, 0, intentAlarm, 0);
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), interval, pendingAlarm);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}

