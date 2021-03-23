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
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class CityWeatherWidget extends AppWidgetProvider {
    private static final String ACTION_BTN = "ButtonClick";
    String formatDate;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("widgetupdate")) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.city_weather_widget);
            ComponentName componentName = new ComponentName(context, CityWeatherWidget.class);
            this.onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
        }

        if(intent.getAction().equals(ACTION_BTN)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.city_weather_widget);
            ComponentName componentName = new ComponentName(context, CityWeatherWidget.class);
            this.onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
            Toast.makeText(context, "업데이트 시간 " + formatDate, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
            String seoulWeatherNow = sharedPreferences.getString("seoulWeatherNow", "0");
            String[] seoulWeatherIcon = new String[4];
            String incheonWeatherNow = sharedPreferences.getString("incheonWeatherNow", "0");
            String[] incheonWeatherIcon = new String[4];
            String daeguWeatherNow = sharedPreferences.getString("daeguWeatherNow", "0");
            String[] daeguWeatherIcon = new String[4];
            String busanWeatherNow = sharedPreferences.getString("busanWeatherNow", "0");
            String[] busanWeatherIcon = new String[4];

            String seoulTempNow = sharedPreferences.getString("seoulTempNow", "0");
            String incheonTempNow = sharedPreferences.getString("incheonTempNow", "0");
            String daeguTempNow = sharedPreferences.getString("daeguTempNow", "0");
            String busanTempNow = sharedPreferences.getString("busanTempNow", "0");

            String[] seoulHour = new String[4];
            String[] seoulTemp = new String[4];
            String[] incheonHour = new String[4];
            String[] incheonTemp = new String[4];
            String[] daeguHour = new String[4];
            String[] daeguTemp = new String[4];
            String[] busanHour = new String[4];
            String[] busanTemp = new String[4];

            for(int x=0; x<4; x++) {
                seoulWeatherIcon[x] = sharedPreferences.getString("seoulWeatherIcon" + x, "0");
                seoulHour[x] = sharedPreferences.getString("seoulHour" + x, "0");
                seoulTemp[x] = sharedPreferences.getString("seoulTemp" + x, "0");
                incheonWeatherIcon[x] = sharedPreferences.getString("incheonWeatherIcon" + x, "0");
                incheonHour[x] = sharedPreferences.getString("incheonHour" + x, "0");
                incheonTemp[x] = sharedPreferences.getString("incheonTemp" + x, "0");
                daeguWeatherIcon[x] = sharedPreferences.getString("daeguWeatherIcon" + x, "0");
                daeguHour[x] = sharedPreferences.getString("daeguHour" + x, "0");
                daeguTemp[x] = sharedPreferences.getString("daeguTemp" + x, "0");
                busanWeatherIcon[x] = sharedPreferences.getString("busanWeatherIcon" + x, "0");
                busanHour[x] = sharedPreferences.getString("busanHour" + x, "0");
                busanTemp[x] = sharedPreferences.getString("busanTemp" + x, "0");
            }

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat formatNow = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            formatDate = formatNow.format(date);

            int appWidgetId = appWidgetIds[i];
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.city_weather_widget);

            Intent intentMain = new Intent(context, MainActivity.class);
            PendingIntent pendingMain = PendingIntent.getActivity(context, 0, intentMain, 0);
            views.setOnClickPendingIntent(R.id.city_weather_widget, pendingMain);

            String pkg = context.getPackageName();

            int idSeoulWeatherNow = context.getResources().getIdentifier("seoul_weather_now", "id", pkg);
            if(seoulWeatherNow.equals("sunny")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.sunny);}
            else if(seoulWeatherNow.equals("daycloudy")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.day_cloudy);}
            else if(seoulWeatherNow.equals("nightclear")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.night_clear);}
            else if(seoulWeatherNow.equals("nightcloudy")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.night_cloudy);}
            else if(seoulWeatherNow.equals("cloudysky")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.cloudy_sky);}
            else if(seoulWeatherNow.equals("fog")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.fog);}
            else if(seoulWeatherNow.equals("rain")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.rain);}
            else if(seoulWeatherNow.equals("dayrain")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.day_rain);}
            else if(seoulWeatherNow.equals("nightrain")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.night_rain);}
            else if(seoulWeatherNow.equals("showers")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.showers);}
            else if(seoulWeatherNow.equals("dayshowers")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.day_showers);}
            else if(seoulWeatherNow.equals("nightshowers")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.night_showers);}
            else if(seoulWeatherNow.equals("snow")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.snow);}
            else if(seoulWeatherNow.equals("daysnow")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.day_snow);}
            else if(seoulWeatherNow.equals("nightsnow")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.night_snow);}
            else if(seoulWeatherNow.equals("rainmix")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.rain_mix);}
            else if(seoulWeatherNow.equals("dayrainmix")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.day_rain_mix);}
            else if(seoulWeatherNow.equals("nightrainmix")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.night_rain_mix);}
            else if(seoulWeatherNow.equals("stormcloud")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.storm_cloud);}
            else if(seoulWeatherNow.equals("sunstorm")) {views.setImageViewResource(idSeoulWeatherNow, R.drawable.sun_storm);}
            views.setInt(idSeoulWeatherNow, "setColorFilter", Color.WHITE);

            int idIncheonWeatherNow = context.getResources().getIdentifier("incheon_weather_now", "id", pkg);
            if(incheonWeatherNow.equals("sunny")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.sunny);}
            else if(incheonWeatherNow.equals("daycloudy")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.day_cloudy);}
            else if(incheonWeatherNow.equals("nightclear")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.night_clear);}
            else if(incheonWeatherNow.equals("nightcloudy")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.night_cloudy);}
            else if(incheonWeatherNow.equals("cloudysky")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.cloudy_sky);}
            else if(incheonWeatherNow.equals("fog")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.fog);}
            else if(incheonWeatherNow.equals("rain")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.rain);}
            else if(incheonWeatherNow.equals("dayrain")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.day_rain);}
            else if(incheonWeatherNow.equals("nightrain")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.night_rain);}
            else if(incheonWeatherNow.equals("showers")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.showers);}
            else if(incheonWeatherNow.equals("dayshowers")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.day_showers);}
            else if(incheonWeatherNow.equals("nightshowers")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.night_showers);}
            else if(incheonWeatherNow.equals("snow")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.snow);}
            else if(incheonWeatherNow.equals("daysnow")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.day_snow);}
            else if(incheonWeatherNow.equals("nightsnow")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.night_snow);}
            else if(incheonWeatherNow.equals("rainmix")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.rain_mix);}
            else if(incheonWeatherNow.equals("dayrainmix")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.day_rain_mix);}
            else if(incheonWeatherNow.equals("nightrainmix")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.night_rain_mix);}
            else if(incheonWeatherNow.equals("stormcloud")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.storm_cloud);}
            else if(incheonWeatherNow.equals("sunstorm")) {views.setImageViewResource(idIncheonWeatherNow, R.drawable.sun_storm);}
            views.setInt(idIncheonWeatherNow, "setColorFilter", Color.WHITE);

            int idDaeguWeatherNow = context.getResources().getIdentifier("daegu_weather_now", "id", pkg);
            if(daeguWeatherNow.equals("sunny")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.sunny);}
            else if(daeguWeatherNow.equals("daycloudy")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.day_cloudy);}
            else if(daeguWeatherNow.equals("nightclear")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.night_clear);}
            else if(daeguWeatherNow.equals("nightcloudy")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.night_cloudy);}
            else if(daeguWeatherNow.equals("cloudysky")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.cloudy_sky);}
            else if(daeguWeatherNow.equals("fog")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.fog);}
            else if(daeguWeatherNow.equals("rain")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.rain);}
            else if(daeguWeatherNow.equals("dayrain")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.day_rain);}
            else if(daeguWeatherNow.equals("nightrain")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.night_rain);}
            else if(daeguWeatherNow.equals("showers")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.showers);}
            else if(daeguWeatherNow.equals("dayshowers")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.day_showers);}
            else if(daeguWeatherNow.equals("nightshowers")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.night_showers);}
            else if(daeguWeatherNow.equals("snow")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.snow);}
            else if(daeguWeatherNow.equals("daysnow")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.day_snow);}
            else if(daeguWeatherNow.equals("nightsnow")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.night_snow);}
            else if(daeguWeatherNow.equals("rainmix")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.rain_mix);}
            else if(daeguWeatherNow.equals("dayrainmix")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.day_rain_mix);}
            else if(daeguWeatherNow.equals("nightrainmix")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.night_rain_mix);}
            else if(daeguWeatherNow.equals("stormcloud")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.storm_cloud);}
            else if(daeguWeatherNow.equals("sunstorm")) {views.setImageViewResource(idDaeguWeatherNow, R.drawable.sun_storm);}
            views.setInt(idDaeguWeatherNow, "setColorFilter", Color.WHITE);

            int idBusanWeatherNow = context.getResources().getIdentifier("busan_weather_now", "id", pkg);
            if(busanWeatherNow.equals("sunny")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.sunny);}
            else if(busanWeatherNow.equals("daycloudy")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.day_cloudy);}
            else if(busanWeatherNow.equals("nightclear")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.night_clear);}
            else if(busanWeatherNow.equals("nightcloudy")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.night_cloudy);}
            else if(busanWeatherNow.equals("cloudysky")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.cloudy_sky);}
            else if(busanWeatherNow.equals("fog")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.fog);}
            else if(busanWeatherNow.equals("rain")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.rain);}
            else if(busanWeatherNow.equals("dayrain")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.day_rain);}
            else if(busanWeatherNow.equals("nightrain")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.night_rain);}
            else if(busanWeatherNow.equals("showers")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.showers);}
            else if(busanWeatherNow.equals("dayshowers")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.day_showers);}
            else if(busanWeatherNow.equals("nightshowers")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.night_showers);}
            else if(busanWeatherNow.equals("snow")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.snow);}
            else if(busanWeatherNow.equals("daysnow")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.day_snow);}
            else if(busanWeatherNow.equals("nightsnow")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.night_snow);}
            else if(busanWeatherNow.equals("rainmix")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.rain_mix);}
            else if(busanWeatherNow.equals("dayrainmix")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.day_rain_mix);}
            else if(busanWeatherNow.equals("nightrainmix")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.night_rain_mix);}
            else if(busanWeatherNow.equals("stormcloud")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.storm_cloud);}
            else if(busanWeatherNow.equals("sunstorm")) {views.setImageViewResource(idBusanWeatherNow, R.drawable.sun_storm);}
            views.setInt(idBusanWeatherNow, "setColorFilter", Color.WHITE);

            for(int z=0; z<4; z++) {
                int idSeoulWeatherIcon = context.getResources().getIdentifier("seoul_weather" + z, "id", pkg);
                if(seoulWeatherIcon[z].equals("sunny")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.sunny);}
                else if(seoulWeatherIcon[z].equals("daycloudy")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.day_cloudy);}
                else if(seoulWeatherIcon[z].equals("nightclear")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.night_clear);}
                else if(seoulWeatherIcon[z].equals("nightcloudy")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.night_cloudy);}
                else if(seoulWeatherIcon[z].equals("cloudysky")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.cloudy_sky);}
                else if(seoulWeatherIcon[z].equals("fog")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.fog);}
                else if(seoulWeatherIcon[z].equals("rain")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.rain);}
                else if(seoulWeatherIcon[z].equals("dayrain")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.day_rain);}
                else if(seoulWeatherIcon[z].equals("nightrain")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.night_rain);}
                else if(seoulWeatherIcon[z].equals("showers")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.showers);}
                else if(seoulWeatherIcon[z].equals("dayshowers")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.day_showers);}
                else if(seoulWeatherIcon[z].equals("nightshowers")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.night_showers);}
                else if(seoulWeatherIcon[z].equals("snow")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.snow);}
                else if(seoulWeatherIcon[z].equals("daysnow")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.day_snow);}
                else if(seoulWeatherIcon[z].equals("nightsnow")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.night_snow);}
                else if(seoulWeatherIcon[z].equals("rainmix")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.rain_mix);}
                else if(seoulWeatherIcon[z].equals("dayrainmix")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.day_rain_mix);}
                else if(seoulWeatherIcon[z].equals("nightrainmix")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.night_rain_mix);}
                else if(seoulWeatherIcon[z].equals("stormcloud")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.storm_cloud);}
                else if(seoulWeatherIcon[z].equals("sunstorm")) {views.setImageViewResource(idSeoulWeatherIcon, R.drawable.sun_storm);}
                views.setInt(idSeoulWeatherIcon, "setColorFilter", Color.WHITE);

                int idIncheonWeatherIcon = context.getResources().getIdentifier("incheon_weather" + z, "id", pkg);
                if(incheonWeatherIcon[z].equals("sunny")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.sunny);}
                else if(incheonWeatherIcon[z].equals("daycloudy")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.day_cloudy);}
                else if(incheonWeatherIcon[z].equals("nightclear")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.night_clear);}
                else if(incheonWeatherIcon[z].equals("nightcloudy")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.night_cloudy);}
                else if(incheonWeatherIcon[z].equals("cloudysky")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.cloudy_sky);}
                else if(incheonWeatherIcon[z].equals("fog")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.fog);}
                else if(incheonWeatherIcon[z].equals("rain")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.rain);}
                else if(incheonWeatherIcon[z].equals("dayrain")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.day_rain);}
                else if(incheonWeatherIcon[z].equals("nightrain")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.night_rain);}
                else if(incheonWeatherIcon[z].equals("showers")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.showers);}
                else if(incheonWeatherIcon[z].equals("dayshowers")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.day_showers);}
                else if(incheonWeatherIcon[z].equals("nightshowers")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.night_showers);}
                else if(incheonWeatherIcon[z].equals("snow")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.snow);}
                else if(incheonWeatherIcon[z].equals("daysnow")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.day_snow);}
                else if(incheonWeatherIcon[z].equals("nightsnow")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.night_snow);}
                else if(incheonWeatherIcon[z].equals("rainmix")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.rain_mix);}
                else if(incheonWeatherIcon[z].equals("dayrainmix")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.day_rain_mix);}
                else if(incheonWeatherIcon[z].equals("nightrainmix")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.night_rain_mix);}
                else if(incheonWeatherIcon[z].equals("stormcloud")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.storm_cloud);}
                else if(incheonWeatherIcon[z].equals("sunstorm")) {views.setImageViewResource(idIncheonWeatherIcon, R.drawable.sun_storm);}
                views.setInt(idIncheonWeatherIcon, "setColorFilter", Color.WHITE);

                int idDaeguWeatherIcon = context.getResources().getIdentifier("daegu_weather" + z, "id", pkg);
                if(daeguWeatherIcon[z].equals("sunny")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.sunny);}
                else if(daeguWeatherIcon[z].equals("daycloudy")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.day_cloudy);}
                else if(daeguWeatherIcon[z].equals("nightclear")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.night_clear);}
                else if(daeguWeatherIcon[z].equals("nightcloudy")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.night_cloudy);}
                else if(daeguWeatherIcon[z].equals("cloudysky")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.cloudy_sky);}
                else if(daeguWeatherIcon[z].equals("fog")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.fog);}
                else if(daeguWeatherIcon[z].equals("rain")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.rain);}
                else if(daeguWeatherIcon[z].equals("dayrain")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.day_rain);}
                else if(daeguWeatherIcon[z].equals("nightrain")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.night_rain);}
                else if(daeguWeatherIcon[z].equals("showers")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.showers);}
                else if(daeguWeatherIcon[z].equals("dayshowers")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.day_showers);}
                else if(daeguWeatherIcon[z].equals("nightshowers")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.night_showers);}
                else if(daeguWeatherIcon[z].equals("snow")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.snow);}
                else if(daeguWeatherIcon[z].equals("daysnow")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.day_snow);}
                else if(daeguWeatherIcon[z].equals("nightsnow")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.night_snow);}
                else if(daeguWeatherIcon[z].equals("rainmix")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.rain_mix);}
                else if(daeguWeatherIcon[z].equals("dayrainmix")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.day_rain_mix);}
                else if(daeguWeatherIcon[z].equals("nightrainmix")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.night_rain_mix);}
                else if(daeguWeatherIcon[z].equals("stormcloud")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.storm_cloud);}
                else if(daeguWeatherIcon[z].equals("sunstorm")) {views.setImageViewResource(idDaeguWeatherIcon, R.drawable.sun_storm);}
                views.setInt(idDaeguWeatherIcon, "setColorFilter", Color.WHITE);

                int idBusanWeatherIcon = context.getResources().getIdentifier("busan_weather" + z, "id", pkg);
                if(busanWeatherIcon[z].equals("sunny")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.sunny);}
                else if(busanWeatherIcon[z].equals("daycloudy")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.day_cloudy);}
                else if(busanWeatherIcon[z].equals("nightclear")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.night_clear);}
                else if(busanWeatherIcon[z].equals("nightcloudy")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.night_cloudy);}
                else if(busanWeatherIcon[z].equals("cloudysky")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.cloudy_sky);}
                else if(busanWeatherIcon[z].equals("fog")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.fog);}
                else if(busanWeatherIcon[z].equals("rain")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.rain);}
                else if(busanWeatherIcon[z].equals("dayrain")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.day_rain);}
                else if(busanWeatherIcon[z].equals("nightrain")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.night_rain);}
                else if(busanWeatherIcon[z].equals("showers")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.showers);}
                else if(busanWeatherIcon[z].equals("dayshowers")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.day_showers);}
                else if(busanWeatherIcon[z].equals("nightshowers")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.night_showers);}
                else if(busanWeatherIcon[z].equals("snow")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.snow);}
                else if(busanWeatherIcon[z].equals("daysnow")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.day_snow);}
                else if(busanWeatherIcon[z].equals("nightsnow")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.night_snow);}
                else if(busanWeatherIcon[z].equals("rainmix")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.rain_mix);}
                else if(busanWeatherIcon[z].equals("dayrainmix")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.day_rain_mix);}
                else if(busanWeatherIcon[z].equals("nightrainmix")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.night_rain_mix);}
                else if(busanWeatherIcon[z].equals("stormcloud")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.storm_cloud);}
                else if(busanWeatherIcon[z].equals("sunstorm")) {views.setImageViewResource(idBusanWeatherIcon, R.drawable.sun_storm);}
                views.setInt(idBusanWeatherIcon, "setColorFilter", Color.WHITE);
            }

            int idSeoulTempNow = context.getResources().getIdentifier("seoul_temp_now", "id", pkg);
            int idIncheonTempNow = context.getResources().getIdentifier("incheon_temp_now", "id", pkg);
            int idDaeguTempNow = context.getResources().getIdentifier("daegu_temp_now", "id", pkg);
            int idBusanTempNow = context.getResources().getIdentifier("busan_temp_now", "id", pkg);
            views.setTextViewText(idSeoulTempNow, seoulTempNow);
            views.setTextViewText(idIncheonTempNow, incheonTempNow);
            views.setTextViewText(idDaeguTempNow, daeguTempNow);
            views.setTextViewText(idBusanTempNow, busanTempNow);

            for(int y=0; y<4; y++) {
                int idSeoulHour = context.getResources().getIdentifier("seoul_hour" + y, "id", pkg);
                int idSeoulTemp = context.getResources().getIdentifier("seoul_temp" + y, "id", pkg);
                int idIncheonHour = context.getResources().getIdentifier("incheon_hour" + y, "id", pkg);
                int idIncheonTemp = context.getResources().getIdentifier("incheon_temp" + y, "id", pkg);
                int idDaeguHour = context.getResources().getIdentifier("daegu_hour" + y, "id", pkg);
                int idDaeguTemp = context.getResources().getIdentifier("daegu_temp" + y, "id", pkg);
                int idBusanHour = context.getResources().getIdentifier("busan_hour" + y, "id", pkg);
                int idBusanTemp = context.getResources().getIdentifier("busan_temp" + y, "id", pkg);

                views.setTextViewText(idSeoulHour, seoulHour[y]);
                views.setTextViewText(idSeoulTemp, seoulTemp[y]);
                views.setTextViewText(idIncheonHour, incheonHour[y]);
                views.setTextViewText(idIncheonTemp, incheonTemp[y]);
                views.setTextViewText(idDaeguHour, daeguHour[y]);
                views.setTextViewText(idDaeguTemp, daeguTemp[y]);
                views.setTextViewText(idBusanHour, busanHour[y]);
                views.setTextViewText(idBusanTemp, busanTemp[y]);
            }

            Intent intentBtn = new Intent(context, CityWeatherWidget.class).setAction(ACTION_BTN);
            PendingIntent pendingBtn = PendingIntent.getBroadcast(context, 0, intentBtn, 0);
            views.setOnClickPendingIntent(R.id.city_refresh, pendingBtn);
            views.setTextViewText(R.id.city_now, formatDate);

            long interval  = 1800000;
            Intent intentAlarm = new Intent(context, CityWeatherWidget.class);
            intentAlarm.setAction("widgetupdate");
            PendingIntent pendingAlarm = PendingIntent.getService(context, 0, intentAlarm, 0);
            AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), interval, pendingAlarm);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

}