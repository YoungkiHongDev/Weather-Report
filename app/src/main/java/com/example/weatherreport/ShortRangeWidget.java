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
import java.util.StringTokenizer;

/**
 * Implementation of App Widget functionality.
 */
public class ShortRangeWidget extends AppWidgetProvider {
    private static final String ACTION_BTN = "ButtonClick";
    String formatDate;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("widgetupdate")) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.short_range_widget);
            ComponentName componentName = new ComponentName(context, ShortRangeWidget.class);
            this.onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
        }

        if(intent.getAction().equals(ACTION_BTN)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.short_range_widget);
            ComponentName componentName = new ComponentName(context, ShortRangeWidget.class);
            this.onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
            Toast.makeText(context, "업데이트 시간 " + formatDate, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
            String shortGugun = sharedPreferences.getString("gugun", "0");
            String[] shortWeather = new String[3];
            String[] shortDay = new String[3];
            String[] shortHighTemp = new String[3];
            String[] shortLowTemp = new String[3];

            for(int x=0; x<3; x++) {
                shortWeather[x] = sharedPreferences.getString("weekWeather" + x, "0");
                shortDay[x] = sharedPreferences.getString("weekDay" + x, "0");
                shortHighTemp[x] = sharedPreferences.getString("weekHighTemp" + x, "0");
                shortLowTemp[x] = sharedPreferences.getString("weekLowTemp" + x, "0");

                StringTokenizer st = new StringTokenizer(shortDay[x], " ");
                String[] array = new String[st.countTokens()];
                int cnt = 0;
                while (st.hasMoreElements()) {
                    array[cnt++] = st.nextToken();
                }
                shortDay[x] = array[1];
            }

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat formatNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            formatDate = formatNow.format(date);

            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.short_range_widget);

            Intent intentMain = new Intent(context, MainActivity.class);
            PendingIntent pendingMain = PendingIntent.getActivity(context, 0, intentMain, 0);

            views.setOnClickPendingIntent(R.id.short_range_widget, pendingMain);
            views.setTextViewText(R.id.short_gugun, shortGugun);

            String pkg = context.getPackageName();
            for(int y=0; y<3; y++) {
                int idWeather = context.getResources().getIdentifier("short_weather" + y, "id", pkg);
                int idDay = context.getResources().getIdentifier("short_day" + y, "id", pkg);
                int idHighTemp = context.getResources().getIdentifier("short_hightemp" + y, "id", pkg);
                int idLowTemp = context.getResources().getIdentifier("short_lowtemp" + y, "id", pkg);

                if(shortWeather[y].equals("폭풍우")) {
                    views.setImageViewResource(idWeather, R.drawable.storm_cloud);
                }
                else if(shortWeather[y].equals("소나기")) {
                    views.setImageViewResource(idWeather, R.drawable.showers);
                }
                else if(shortWeather[y].equals("비")) {
                    views.setImageViewResource(idWeather, R.drawable.rain);
                }
                else if(shortWeather[y].equals("눈")) {
                    views.setImageViewResource(idWeather, R.drawable.snow);
                }
                else if(shortWeather[y].equals("안개")) {
                    views.setImageViewResource(idWeather, R.drawable.fog);
                }
                else if(shortWeather[y].equals("맑음")) {
                    views.setImageViewResource(idWeather, R.drawable.sunny);
                }
                else if(shortWeather[y].equals("구름조금")) {
                    views.setImageViewResource(idWeather, R.drawable.day_cloudy);
                }
                else if(shortWeather[y].equals("흐림")) {
                    views.setImageViewResource(idWeather, R.drawable.cloudy_sky);
                }

                views.setInt(idWeather, "setColorFilter", Color.WHITE);
                views.setTextViewText(idDay, shortDay[y]);
                views.setTextViewText(idHighTemp , shortHighTemp[y]);
                views.setTextViewText(idLowTemp, shortLowTemp[y]);
            }

            Intent intentBtn = new Intent(context, ShortRangeWidget.class).setAction(ACTION_BTN);
            PendingIntent pendingBtn = PendingIntent.getBroadcast(context, 0, intentBtn, 0);
            views.setOnClickPendingIntent(R.id.short_refresh, pendingBtn);
            views.setTextViewText(R.id.short_now, formatDate);

            long interval  = 1800000;
            Intent intentAlarm = new Intent(context, ShortRangeWidget.class);
            intentAlarm.setAction("widgetupdate");
            PendingIntent pendingAlarm = PendingIntent.getService(context, 0, intentAlarm, 0);
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), interval, pendingAlarm);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}

