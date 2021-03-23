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
public class LongRangeWidget extends AppWidgetProvider {
    private static final String ACTION_BTN = "ButtonClick";
    String formatDate;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("widgetupdate")) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.long_range_widget);
            ComponentName componentName = new ComponentName(context, LongRangeWidget.class);
            this.onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
        }

        if(intent.getAction().equals(ACTION_BTN)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.long_range_widget);
            ComponentName componentName = new ComponentName(context, LongRangeWidget.class);
            this.onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
            Toast.makeText(context, "업데이트 시간 " + formatDate, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
            String longGugun = sharedPreferences.getString("gugun", "0");
            String[] longWeather = new String[5];
            String[] longDay = new String[5];

            for(int x=0; x<5; x++) {
                longWeather[x] = sharedPreferences.getString("weekWeather" + x, "0");
                longDay[x] = sharedPreferences.getString("weekDay" + x, "0");

                StringTokenizer st = new StringTokenizer(longDay[x], " ");
                String[] array = new String[st.countTokens()];
                int cnt = 0;
                while (st.hasMoreElements()) {
                    array[cnt++] = st.nextToken();
                }
                longDay[x] = array[1];
            }

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat formatNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            formatDate = formatNow.format(date);

            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.long_range_widget);

            Intent intentMain = new Intent(context, MainActivity.class);
            PendingIntent pendingMain = PendingIntent.getActivity(context, 0, intentMain, 0);

            views.setOnClickPendingIntent(R.id.long_range_widget, pendingMain);
            views.setTextViewText(R.id.long_gugun, longGugun);

            String pkg = context.getPackageName();
            for(int y=0; y<5; y++) {
                int idWeather = context.getResources().getIdentifier("long_weather" + y, "id", pkg);
                int idDay = context.getResources().getIdentifier("long_day" + y, "id", pkg);

                if(longWeather[y].equals("폭풍우")) {
                    views.setImageViewResource(idWeather, R.drawable.storm_cloud);
                }
                else if(longWeather[y].equals("소나기")) {
                    views.setImageViewResource(idWeather, R.drawable.showers);
                }
                else if(longWeather[y].equals("비")) {
                    views.setImageViewResource(idWeather, R.drawable.rain);
                }
                else if(longWeather[y].equals("눈")) {
                    views.setImageViewResource(idWeather, R.drawable.snow);
                }
                else if(longWeather[y].equals("안개")) {
                    views.setImageViewResource(idWeather, R.drawable.fog);
                }
                else if(longWeather[y].equals("맑음")) {
                    views.setImageViewResource(idWeather, R.drawable.sunny);
                }
                else if(longWeather[y].equals("구름조금")) {
                    views.setImageViewResource(idWeather, R.drawable.day_cloudy);
                }
                else if(longWeather[y].equals("흐림")) {
                    views.setImageViewResource(idWeather, R.drawable.cloudy_sky);
                }

                views.setInt(idWeather, "setColorFilter", Color.WHITE);
                views.setTextViewText(idDay, longDay[y]);
            }

            Intent intentBtn = new Intent(context, LongRangeWidget.class).setAction(ACTION_BTN);
            PendingIntent pendingBtn = PendingIntent.getBroadcast(context, 0, intentBtn, 0);
            views.setOnClickPendingIntent(R.id.long_refresh, pendingBtn);
            views.setTextViewText(R.id.long_now, formatDate);

            long interval  = 1800000;
            Intent intentAlarm = new Intent(context, LongRangeWidget.class);
            intentAlarm.setAction("widgetupdate");
            PendingIntent pendingAlarm = PendingIntent.getService(context, 0, intentAlarm, 0);
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), interval, pendingAlarm);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}

