package com.example.weatherreport;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class City_Weather extends Fragment {

    View view;
    TextView now_time;
    LocInfo locInfo;

    ImageView now_s_w_icon;
    ImageView[] s_w_icon = new ImageView[4];
    ImageView now_i_w_icon;
    ImageView[] i_w_icon = new ImageView[4];
    ImageView now_d_w_icon;
    ImageView[] d_w_icon = new ImageView[4];
    ImageView now_b_w_icon;
    ImageView[] b_w_icon = new ImageView[4];
    TextView now_s_big_temp;
    TextView now_i_big_temp;
    TextView now_d_big_temp;
    TextView now_b_big_temp;
    TextView[] now_s_temp = new TextView[4];
    TextView[] now_s_hour = new TextView[4];

    TextView[] now_i_temp = new TextView[4];
    TextView[] now_i_hour = new TextView[4];

    TextView[] now_d_temp = new TextView[4];
    TextView[] now_d_hour = new TextView[4];

    TextView[] now_b_temp = new TextView[4];
    TextView[] now_b_hour = new TextView[4];

    double s_lat = 37.540705;
    double s_lon = 126.956764;

    double i_lat = 37.469221;
    double i_lon = 126.573234;

    double d_lat = 35.798838;
    double d_lon = 128.583052;

    double b_lat = 35.198362;
    double b_lon = 129.053922;

    public City_Weather() {

    }

    public void saveSharedPreferences() {
        String[] cityTemp = new String[4];
        String[] seoulHour = new String[4];
        String[] seoulTemp = new String[4];
        String[] incheonHour = new String[4];
        String[] incheonTemp = new String[4];
        String[] daeguHour = new String[4];
        String[] daeguTemp = new String[4];
        String[] busanHour = new String[4];
        String[] busanTemp = new String[4];

        SharedPreferences pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        cityTemp[0] = now_s_big_temp.getText().toString();
        cityTemp[1] = now_i_big_temp.getText().toString();
        cityTemp[2] = now_d_big_temp.getText().toString();
        cityTemp[3] = now_b_big_temp.getText().toString();

        editor.putString("seoulTempNow", cityTemp[0]);
        editor.putString("incheonTempNow", cityTemp[1]);
        editor.putString("daeguTempNow", cityTemp[2]);
        editor.putString("busanTempNow", cityTemp[3]);

        for(int i=0; i<4; i++) {
           seoulHour[i] = now_s_hour[i].getText().toString();
           seoulTemp[i] = now_s_temp[i].getText().toString();
           incheonHour[i] = now_i_hour[i].getText().toString();
           incheonTemp[i] = now_i_temp[i].getText().toString();
           daeguHour[i] = now_d_hour[i].getText().toString();
           daeguTemp[i] = now_d_temp[i].getText().toString();
           busanHour[i] = now_b_hour[i].getText().toString();
           busanTemp[i] = now_b_temp[i].getText().toString();

           editor.putString("seoulHour" + i, seoulHour[i]);
           editor.putString("seoulTemp" + i, seoulTemp[i]);
           editor.putString("incheonHour" + i, incheonHour[i]);
           editor.putString("incheonTemp" + i, incheonTemp[i]);
           editor.putString("daeguHour" + i, daeguHour[i]);
           editor.putString("daeguTemp" + i, daeguTemp[i]);
           editor.putString("busanHour" + i, busanHour[i]);
           editor.putString("busanTemp" + i, busanTemp[i]);
        }
        editor.apply();
    }

    public void saveWeatherIconPref() {
        Drawable seoulWeatherNow = now_s_w_icon.getDrawable();
        Drawable incheonWeatherNow = now_i_w_icon.getDrawable();
        Drawable daeguWeatherNow = now_d_w_icon.getDrawable();
        Drawable busanWeatherNow = now_b_w_icon.getDrawable();
        Drawable[] seoulWeatherIcon = new Drawable[4];
        Drawable[] incheonWeatherIcon = new Drawable[4];
        Drawable[] daeguWeatherIcon = new Drawable[4];
        Drawable[] busanWeatherIcon = new Drawable[4];
        Bitmap bitSunny = ((BitmapDrawable)getResources().getDrawable(R.drawable.sunny)).getBitmap();
        Bitmap bitDayCloudy = ((BitmapDrawable)getResources().getDrawable(R.drawable.day_cloudy)).getBitmap();
        Bitmap bitNightClear = ((BitmapDrawable)getResources().getDrawable(R.drawable.night_clear)).getBitmap();
        Bitmap bitNightCloudy = ((BitmapDrawable)getResources().getDrawable(R.drawable.night_cloudy)).getBitmap();
        Bitmap bitCloudySky = ((BitmapDrawable)getResources().getDrawable(R.drawable.cloudy_sky)).getBitmap();
        Bitmap bitFog = ((BitmapDrawable)getResources().getDrawable(R.drawable.fog)).getBitmap();
        Bitmap bitRain = ((BitmapDrawable)getResources().getDrawable(R.drawable.rain)).getBitmap();
        Bitmap bitDayRain = ((BitmapDrawable)getResources().getDrawable(R.drawable.day_rain)).getBitmap();
        Bitmap bitNightRain = ((BitmapDrawable)getResources().getDrawable(R.drawable.night_rain)).getBitmap();
        Bitmap bitShowers = ((BitmapDrawable)getResources().getDrawable(R.drawable.showers)).getBitmap();
        Bitmap bitDayShowers = ((BitmapDrawable)getResources().getDrawable(R.drawable.day_showers)).getBitmap();
        Bitmap bitNightShowers = ((BitmapDrawable)getResources().getDrawable(R.drawable.night_showers)).getBitmap();
        Bitmap bitSnow = ((BitmapDrawable)getResources().getDrawable(R.drawable.snow)).getBitmap();
        Bitmap bitDaySnow = ((BitmapDrawable)getResources().getDrawable(R.drawable.day_snow)).getBitmap();
        Bitmap bitNightSnow = ((BitmapDrawable)getResources().getDrawable(R.drawable.night_snow)).getBitmap();
        Bitmap bitRainMix = ((BitmapDrawable)getResources().getDrawable(R.drawable.rain_mix)).getBitmap();
        Bitmap bitDayRainMix = ((BitmapDrawable)getResources().getDrawable(R.drawable.day_rain_mix)).getBitmap();
        Bitmap bitNightRainMix = ((BitmapDrawable)getResources().getDrawable(R.drawable.night_rain_mix)).getBitmap();
        Bitmap bitStormCloud = ((BitmapDrawable)getResources().getDrawable(R.drawable.storm_cloud)).getBitmap();
        Bitmap bitSunStorm = ((BitmapDrawable)getResources().getDrawable(R.drawable.sun_storm)).getBitmap();

        for(int i=0; i<4; i++) {
            seoulWeatherIcon[i] = s_w_icon[i].getDrawable();
            incheonWeatherIcon[i] = i_w_icon[i].getDrawable();
            daeguWeatherIcon[i] = d_w_icon[i].getDrawable();
            busanWeatherIcon[i] = b_w_icon[i].getDrawable();
        }

        Bitmap bitSeoulWeatherNow = ((BitmapDrawable)seoulWeatherNow).getBitmap();
        Bitmap bitIncheonWeatherNow = ((BitmapDrawable)incheonWeatherNow).getBitmap();
        Bitmap bitDaeguWeatherNow = ((BitmapDrawable)daeguWeatherNow).getBitmap();
        Bitmap bitBusanWeatherNow = ((BitmapDrawable)busanWeatherNow).getBitmap();
        Bitmap[] bitSeoulWeatherIcon = new Bitmap[4];
        Bitmap[] bitIncheonWeatherIcon = new Bitmap[4];
        Bitmap[] bitDaeguWeatherIcon = new Bitmap[4];
        Bitmap[] bitBusanWeatherIcon = new Bitmap[4];

        for(int b=0; b<4; b++) {
            bitSeoulWeatherIcon[b] = ((BitmapDrawable)seoulWeatherIcon[b]).getBitmap();
            bitIncheonWeatherIcon[b] = ((BitmapDrawable)incheonWeatherIcon[b]).getBitmap();
            bitDaeguWeatherIcon[b] = ((BitmapDrawable)daeguWeatherIcon[b]).getBitmap();
            bitBusanWeatherIcon[b] = ((BitmapDrawable)busanWeatherIcon[b]).getBitmap();
        }

        String seoulStringNow = null;
        String[] seoulStringIcon = new String[4];
        String incheonStringNow = null;
        String[] incheonStringIcon = new String[4];
        String daeguStringNow = null;
        String[] daeguStringIcon = new String[4];
        String busanStringNow = null;
        String[] busanStringIcon = new String[4];

        SharedPreferences pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        if(bitSeoulWeatherNow.equals(bitSunny)) {seoulStringNow = "sunny";}
        else if(bitSeoulWeatherNow.equals(bitDayCloudy)) {seoulStringNow = "daycloudy";}
        else if(bitSeoulWeatherNow.equals(bitNightClear)) {seoulStringNow = "nightclear";}
        else if(bitSeoulWeatherNow.equals(bitNightCloudy)) {seoulStringNow = "nightcloudy";}
        else if(bitSeoulWeatherNow.equals(bitCloudySky)) {seoulStringNow = "cloudysky";}
        else if(bitSeoulWeatherNow.equals(bitFog)) {seoulStringNow = "fog";}
        else if(bitSeoulWeatherNow.equals(bitRain)) {seoulStringNow = "rain";}
        else if(bitSeoulWeatherNow.equals(bitDayRain)) {seoulStringNow = "dayrain";}
        else if(bitSeoulWeatherNow.equals(bitNightRain)) {seoulStringNow = "nightrain";}
        else if(bitSeoulWeatherNow.equals(bitShowers)) {seoulStringNow = "showers";}
        else if(bitSeoulWeatherNow.equals(bitDayShowers)) {seoulStringNow = "dayshowers";}
        else if(bitSeoulWeatherNow.equals(bitNightShowers)) {seoulStringNow = "nightshowers";}
        else if(bitSeoulWeatherNow.equals(bitSnow)) {seoulStringNow = "snow";}
        else if(bitSeoulWeatherNow.equals(bitDaySnow)) {seoulStringNow = "daysnow";}
        else if(bitSeoulWeatherNow.equals(bitNightSnow)) {seoulStringNow = "nightsnow";}
        else if(bitSeoulWeatherNow.equals(bitRainMix)) {seoulStringNow = "rainmix";}
        else if(bitSeoulWeatherNow.equals(bitDayRainMix)) {seoulStringNow = "dayrainmix";}
        else if(bitSeoulWeatherNow.equals(bitNightRainMix)) {seoulStringNow = "nightrainmix";}
        else if(bitSeoulWeatherNow.equals(bitStormCloud)) {seoulStringNow = "stormcloud";}
        else if(bitSeoulWeatherNow.equals(bitSunStorm)) {seoulStringNow = "sunstorm";}
        editor.putString("seoulWeatherNow", seoulStringNow);

        if(bitIncheonWeatherNow.equals(bitSunny)) {incheonStringNow = "sunny";}
        else if(bitIncheonWeatherNow.equals(bitDayCloudy)) {incheonStringNow = "daycloudy";}
        else if(bitIncheonWeatherNow.equals(bitNightClear)) {incheonStringNow = "nightclear";}
        else if(bitIncheonWeatherNow.equals(bitNightCloudy)) {incheonStringNow = "nightcloudy";}
        else if(bitIncheonWeatherNow.equals(bitCloudySky)) {incheonStringNow = "cloudysky";}
        else if(bitIncheonWeatherNow.equals(bitFog)) {incheonStringNow = "fog";}
        else if(bitIncheonWeatherNow.equals(bitRain)) {incheonStringNow = "rain";}
        else if(bitIncheonWeatherNow.equals(bitDayRain)) {incheonStringNow = "dayrain";}
        else if(bitIncheonWeatherNow.equals(bitNightRain)) {incheonStringNow = "nightrain";}
        else if(bitIncheonWeatherNow.equals(bitShowers)) {incheonStringNow = "showers";}
        else if(bitIncheonWeatherNow.equals(bitDayShowers)) {incheonStringNow = "dayshowers";}
        else if(bitIncheonWeatherNow.equals(bitNightShowers)) {incheonStringNow = "nightshowers";}
        else if(bitIncheonWeatherNow.equals(bitSnow)) {incheonStringNow = "snow";}
        else if(bitIncheonWeatherNow.equals(bitDaySnow)) {incheonStringNow = "daysnow";}
        else if(bitIncheonWeatherNow.equals(bitNightSnow)) {incheonStringNow = "nightsnow";}
        else if(bitIncheonWeatherNow.equals(bitRainMix)) {incheonStringNow = "rainmix";}
        else if(bitIncheonWeatherNow.equals(bitDayRainMix)) {incheonStringNow = "dayrainmix";}
        else if(bitIncheonWeatherNow.equals(bitNightRainMix)) {incheonStringNow = "nightrainmix";}
        else if(bitIncheonWeatherNow.equals(bitStormCloud)) {incheonStringNow = "stormcloud";}
        else if(bitIncheonWeatherNow.equals(bitSunStorm)) {incheonStringNow = "sunstorm";}
        editor.putString("incheonWeatherNow", incheonStringNow);

        if(bitDaeguWeatherNow.equals(bitSunny)) {daeguStringNow = "sunny";}
        else if(bitDaeguWeatherNow.equals(bitDayCloudy)) {daeguStringNow = "daycloudy";}
        else if(bitDaeguWeatherNow.equals(bitNightClear)) {daeguStringNow = "nightclear";}
        else if(bitDaeguWeatherNow.equals(bitNightCloudy)) {daeguStringNow = "nightcloudy";}
        else if(bitDaeguWeatherNow.equals(bitCloudySky)) {daeguStringNow = "cloudysky";}
        else if(bitDaeguWeatherNow.equals(bitFog)) {daeguStringNow = "fog";}
        else if(bitDaeguWeatherNow.equals(bitRain)) {daeguStringNow = "rain";}
        else if(bitDaeguWeatherNow.equals(bitDayRain)) {daeguStringNow = "dayrain";}
        else if(bitDaeguWeatherNow.equals(bitNightRain)) {daeguStringNow = "nightrain";}
        else if(bitDaeguWeatherNow.equals(bitShowers)) {daeguStringNow = "showers";}
        else if(bitDaeguWeatherNow.equals(bitDayShowers)) {daeguStringNow = "dayshowers";}
        else if(bitDaeguWeatherNow.equals(bitNightShowers)) {daeguStringNow = "nightshowers";}
        else if(bitDaeguWeatherNow.equals(bitSnow)) {daeguStringNow = "snow";}
        else if(bitDaeguWeatherNow.equals(bitDaySnow)) {daeguStringNow = "daysnow";}
        else if(bitDaeguWeatherNow.equals(bitNightSnow)) {daeguStringNow = "nightsnow";}
        else if(bitDaeguWeatherNow.equals(bitRainMix)) {daeguStringNow = "rainmix";}
        else if(bitDaeguWeatherNow.equals(bitDayRainMix)) {daeguStringNow = "dayrainmix";}
        else if(bitDaeguWeatherNow.equals(bitNightRainMix)) {daeguStringNow = "nightrainmix";}
        else if(bitDaeguWeatherNow.equals(bitStormCloud)) {daeguStringNow = "stormcloud";}
        else if(bitDaeguWeatherNow.equals(bitSunStorm)) {daeguStringNow = "sunstorm";}
        editor.putString("daeguWeatherNow", daeguStringNow);

        if(bitBusanWeatherNow.equals(bitSunny)) {busanStringNow = "sunny";}
        else if(bitBusanWeatherNow.equals(bitDayCloudy)) {busanStringNow = "daycloudy";}
        else if(bitBusanWeatherNow.equals(bitNightClear)) {busanStringNow = "nightclear";}
        else if(bitBusanWeatherNow.equals(bitNightCloudy)) {busanStringNow = "nightcloudy";}
        else if(bitBusanWeatherNow.equals(bitCloudySky)) {busanStringNow = "cloudysky";}
        else if(bitBusanWeatherNow.equals(bitFog)) {busanStringNow = "fog";}
        else if(bitBusanWeatherNow.equals(bitRain)) {busanStringNow = "rain";}
        else if(bitBusanWeatherNow.equals(bitDayRain)) {busanStringNow = "dayrain";}
        else if(bitBusanWeatherNow.equals(bitNightRain)) {busanStringNow = "nightrain";}
        else if(bitBusanWeatherNow.equals(bitShowers)) {busanStringNow = "showers";}
        else if(bitBusanWeatherNow.equals(bitDayShowers)) {busanStringNow = "dayshowers";}
        else if(bitBusanWeatherNow.equals(bitNightShowers)) {busanStringNow = "nightshowers";}
        else if(bitBusanWeatherNow.equals(bitSnow)) {busanStringNow = "snow";}
        else if(bitBusanWeatherNow.equals(bitDaySnow)) {busanStringNow = "daysnow";}
        else if(bitBusanWeatherNow.equals(bitNightSnow)) {busanStringNow = "nightsnow";}
        else if(bitBusanWeatherNow.equals(bitRainMix)) {busanStringNow = "rainmix";}
        else if(bitBusanWeatherNow.equals(bitDayRainMix)) {busanStringNow = "dayrainmix";}
        else if(bitBusanWeatherNow.equals(bitNightRainMix)) {busanStringNow = "nightrainmix";}
        else if(bitBusanWeatherNow.equals(bitStormCloud)) {busanStringNow = "stormcloud";}
        else if(bitBusanWeatherNow.equals(bitSunStorm)) {busanStringNow = "sunstorm";}
        editor.putString("busanWeatherNow", busanStringNow);

        for(int i=0; i<4; i++) {
            if(bitSeoulWeatherIcon[i].equals(bitSunny)) {seoulStringIcon[i] = "sunny";}
            else if(bitSeoulWeatherIcon[i].equals(bitDayCloudy)) {seoulStringIcon[i] = "daycloudy";}
            else if(bitSeoulWeatherIcon[i].equals(bitNightClear)) {seoulStringIcon[i] = "nightclear";}
            else if(bitSeoulWeatherIcon[i].equals(bitNightCloudy)) {seoulStringIcon[i] = "nightcloudy";}
            else if(bitSeoulWeatherIcon[i].equals(bitCloudySky)) {seoulStringIcon[i] = "cloudysky";}
            else if(bitSeoulWeatherIcon[i].equals(bitFog)) {seoulStringIcon[i] = "fog";}
            else if(bitSeoulWeatherIcon[i].equals(bitRain)) {seoulStringIcon[i] = "rain";}
            else if(bitSeoulWeatherIcon[i].equals(bitDayRain)) {seoulStringIcon[i] = "dayrain";}
            else if(bitSeoulWeatherIcon[i].equals(bitNightRain)) {seoulStringIcon[i] = "nightrain";}
            else if(bitSeoulWeatherIcon[i].equals(bitShowers)) {seoulStringIcon[i] = "showers";}
            else if(bitSeoulWeatherIcon[i].equals(bitDayShowers)) {seoulStringIcon[i] = "dayshowers";}
            else if(bitSeoulWeatherIcon[i].equals(bitNightShowers)) {seoulStringIcon[i] = "nightshowers";}
            else if(bitSeoulWeatherIcon[i].equals(bitSnow)) {seoulStringIcon[i] = "snow";}
            else if(bitSeoulWeatherIcon[i].equals(bitDaySnow)) {seoulStringIcon[i] = "daysnow";}
            else if(bitSeoulWeatherIcon[i].equals(bitNightSnow)) {seoulStringIcon[i] = "nightsnow";}
            else if(bitSeoulWeatherIcon[i].equals(bitRainMix)) {seoulStringIcon[i] = "rainmix";}
            else if(bitSeoulWeatherIcon[i].equals(bitDayRainMix)) {seoulStringIcon[i] = "dayrainmix";}
            else if(bitSeoulWeatherIcon[i].equals(bitNightRainMix)) {seoulStringIcon[i] = "nightrainmix";}
            else if(bitSeoulWeatherIcon[i].equals(bitStormCloud)) {seoulStringIcon[i] = "stormcloud";}
            else if(bitSeoulWeatherIcon[i].equals(bitSunStorm)) {seoulStringIcon[i] = "sunstorm";}
            editor.putString("seoulWeatherIcon" + i, seoulStringIcon[i]);

            if(bitIncheonWeatherIcon[i].equals(bitSunny)) {incheonStringIcon[i] = "sunny";}
            else if(bitIncheonWeatherIcon[i].equals(bitDayCloudy)) {incheonStringIcon[i] = "daycloudy";}
            else if(bitIncheonWeatherIcon[i].equals(bitNightClear)) {incheonStringIcon[i] = "nightclear";}
            else if(bitIncheonWeatherIcon[i].equals(bitNightCloudy)) {incheonStringIcon[i] = "nightcloudy";}
            else if(bitIncheonWeatherIcon[i].equals(bitCloudySky)) {incheonStringIcon[i] = "cloudysky";}
            else if(bitIncheonWeatherIcon[i].equals(bitFog)) {incheonStringIcon[i] = "fog";}
            else if(bitIncheonWeatherIcon[i].equals(bitRain)) {incheonStringIcon[i] = "rain";}
            else if(bitIncheonWeatherIcon[i].equals(bitDayRain)) {incheonStringIcon[i] = "dayrain";}
            else if(bitIncheonWeatherIcon[i].equals(bitNightRain)) {incheonStringIcon[i] = "nightrain";}
            else if(bitIncheonWeatherIcon[i].equals(bitShowers)) {incheonStringIcon[i] = "showers";}
            else if(bitIncheonWeatherIcon[i].equals(bitDayShowers)) {incheonStringIcon[i] = "dayshowers";}
            else if(bitIncheonWeatherIcon[i].equals(bitNightShowers)) {incheonStringIcon[i] = "nightshowers";}
            else if(bitIncheonWeatherIcon[i].equals(bitSnow)) {incheonStringIcon[i] = "snow";}
            else if(bitIncheonWeatherIcon[i].equals(bitDaySnow)) {incheonStringIcon[i] = "daysnow";}
            else if(bitIncheonWeatherIcon[i].equals(bitNightSnow)) {incheonStringIcon[i] = "nightsnow";}
            else if(bitIncheonWeatherIcon[i].equals(bitRainMix)) {incheonStringIcon[i] = "rainmix";}
            else if(bitIncheonWeatherIcon[i].equals(bitDayRainMix)) {incheonStringIcon[i] = "dayrainmix";}
            else if(bitIncheonWeatherIcon[i].equals(bitNightRainMix)) {incheonStringIcon[i] = "nightrainmix";}
            else if(bitIncheonWeatherIcon[i].equals(bitStormCloud)) {incheonStringIcon[i] = "stormcloud";}
            else if(bitIncheonWeatherIcon[i].equals(bitSunStorm)) {incheonStringIcon[i] = "sunstorm";}
            editor.putString("incheonWeatherIcon" + i, incheonStringIcon[i]);

            if(bitDaeguWeatherIcon[i].equals(bitSunny)) {daeguStringIcon[i] = "sunny";}
            else if(bitDaeguWeatherIcon[i].equals(bitDayCloudy)) {daeguStringIcon[i] = "daycloudy";}
            else if(bitDaeguWeatherIcon[i].equals(bitNightClear)) {daeguStringIcon[i] = "nightclear";}
            else if(bitDaeguWeatherIcon[i].equals(bitNightCloudy)) {daeguStringIcon[i] = "nightcloudy";}
            else if(bitDaeguWeatherIcon[i].equals(bitCloudySky)) {daeguStringIcon[i] = "cloudysky";}
            else if(bitDaeguWeatherIcon[i].equals(bitFog)) {daeguStringIcon[i] = "fog";}
            else if(bitDaeguWeatherIcon[i].equals(bitRain)) {daeguStringIcon[i] = "rain";}
            else if(bitDaeguWeatherIcon[i].equals(bitDayRain)) {daeguStringIcon[i] = "dayrain";}
            else if(bitDaeguWeatherIcon[i].equals(bitNightRain)) {daeguStringIcon[i] = "nightrain";}
            else if(bitDaeguWeatherIcon[i].equals(bitShowers)) {daeguStringIcon[i] = "showers";}
            else if(bitDaeguWeatherIcon[i].equals(bitDayShowers)) {daeguStringIcon[i] = "dayshowers";}
            else if(bitDaeguWeatherIcon[i].equals(bitNightShowers)) {daeguStringIcon[i] = "nightshowers";}
            else if(bitDaeguWeatherIcon[i].equals(bitSnow)) {daeguStringIcon[i] = "snow";}
            else if(bitDaeguWeatherIcon[i].equals(bitDaySnow)) {daeguStringIcon[i] = "daysnow";}
            else if(bitDaeguWeatherIcon[i].equals(bitNightSnow)) {daeguStringIcon[i] = "nightsnow";}
            else if(bitDaeguWeatherIcon[i].equals(bitRainMix)) {daeguStringIcon[i] = "rainmix";}
            else if(bitDaeguWeatherIcon[i].equals(bitDayRainMix)) {daeguStringIcon[i] = "dayrainmix";}
            else if(bitDaeguWeatherIcon[i].equals(bitNightRainMix)) {daeguStringIcon[i] = "nightrainmix";}
            else if(bitDaeguWeatherIcon[i].equals(bitStormCloud)) {daeguStringIcon[i] = "stormcloud";}
            else if(bitDaeguWeatherIcon[i].equals(bitSunStorm)) {daeguStringIcon[i] = "sunstorm";}
            editor.putString("daeguWeatherIcon" + i, daeguStringIcon[i]);

            if(bitBusanWeatherIcon[i].equals(bitSunny)) {busanStringIcon[i] = "sunny";}
            else if(bitBusanWeatherIcon[i].equals(bitDayCloudy)) {busanStringIcon[i] = "daycloudy";}
            else if(bitBusanWeatherIcon[i].equals(bitNightClear)) {busanStringIcon[i] = "nightclear";}
            else if(bitBusanWeatherIcon[i].equals(bitNightCloudy)) {busanStringIcon[i] = "nightcloudy";}
            else if(bitBusanWeatherIcon[i].equals(bitCloudySky)) {busanStringIcon[i] = "cloudysky";}
            else if(bitBusanWeatherIcon[i].equals(bitFog)) {busanStringIcon[i] = "fog";}
            else if(bitBusanWeatherIcon[i].equals(bitRain)) {busanStringIcon[i] = "rain";}
            else if(bitBusanWeatherIcon[i].equals(bitDayRain)) {busanStringIcon[i] = "dayrain";}
            else if(bitBusanWeatherIcon[i].equals(bitNightRain)) {busanStringIcon[i] = "nightrain";}
            else if(bitBusanWeatherIcon[i].equals(bitShowers)) {busanStringIcon[i] = "showers";}
            else if(bitBusanWeatherIcon[i].equals(bitDayShowers)) {busanStringIcon[i] = "dayshowers";}
            else if(bitBusanWeatherIcon[i].equals(bitNightShowers)) {busanStringIcon[i] = "nightshowers";}
            else if(bitBusanWeatherIcon[i].equals(bitSnow)) {busanStringIcon[i] = "snow";}
            else if(bitBusanWeatherIcon[i].equals(bitDaySnow)) {busanStringIcon[i] = "daysnow";}
            else if(bitBusanWeatherIcon[i].equals(bitNightSnow)) {busanStringIcon[i] = "nightsnow";}
            else if(bitBusanWeatherIcon[i].equals(bitRainMix)) {busanStringIcon[i] = "rainmix";}
            else if(bitBusanWeatherIcon[i].equals(bitDayRainMix)) {busanStringIcon[i] = "dayrainmix";}
            else if(bitBusanWeatherIcon[i].equals(bitNightRainMix)) {busanStringIcon[i] = "nightrainmix";}
            else if(bitBusanWeatherIcon[i].equals(bitStormCloud)) {busanStringIcon[i] = "stormcloud";}
            else if(bitBusanWeatherIcon[i].equals(bitSunStorm)) {busanStringIcon[i] = "sunstorm";}
            editor.putString("busanWeatherIcon" + i, busanStringIcon[i]);
        }
        editor.apply();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_city__weather, container, false);
        now_time = (TextView)view.findViewById(R.id.now_hour);
        Date date = new Date();
        SimpleDateFormat n_timeformat = new SimpleDateFormat("YYYY-MM-dd-HH");
        SimpleDateFormat h_format = new SimpleDateFormat("HH시");
        String n_time = n_timeformat.format(date);
        now_time.setText(n_time + ":00");
        icon_Color();
        now_weather_setting();
        hours_weather();
        saveSharedPreferences();
        saveWeatherIconPref();

        return view;
    }

    @Override
    public void onResume() {
        LinearLayout cityLayout = (LinearLayout) view.findViewById(R.id.city_layout);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        int bgGetNum =  sharedPreferences.getInt("Background", 1);

        switch (bgGetNum) {
            case 1:
                cityLayout.setBackgroundResource(R.drawable.bg_sunny);
                break;
            case 2:
                cityLayout.setBackgroundResource(R.drawable.bg_day);
                break;
            case 3:
                cityLayout.setBackgroundResource(R.drawable.bg_night);
                break;
            case 4:
                cityLayout.setBackgroundResource(R.drawable.bg_cloudy);
                break;
            case 5:
                cityLayout.setBackgroundResource(R.drawable.bg_fog);
                break;
            case 6:
                cityLayout.setBackgroundResource(R.drawable.bg_rain);
                break;
            case 7:
                cityLayout.setBackgroundResource(R.drawable.bg_snow);
                break;
            case 8:
                cityLayout.setBackgroundResource(R.drawable.bg_rainmix);
                break;
            case 9:
                cityLayout.setBackgroundResource(R.drawable.bg_storm);
                break;
        }
        super.onResume();
    }

    private void icon_Color()
    {
        now_s_w_icon = (ImageView)view.findViewById(R.id.now_wicon);
        now_i_w_icon = (ImageView)view.findViewById(R.id.now_i_wicon);
        now_d_w_icon = (ImageView)view.findViewById(R.id.now_d_wicon);
        now_b_w_icon = (ImageView)view.findViewById(R.id.now_b_wicon);

        s_w_icon[0] = (ImageView)view.findViewById(R.id.now_wicon3);
        s_w_icon[1] = (ImageView)view.findViewById(R.id.now_wicon6);
        s_w_icon[2] = (ImageView)view.findViewById(R.id.now_wicon9);
        s_w_icon[3] = (ImageView)view.findViewById(R.id.now_wicon12);

        i_w_icon[0] = (ImageView)view.findViewById(R.id.now_i_wicon3);
        i_w_icon[1] = (ImageView)view.findViewById(R.id.now_i_wicon6);
        i_w_icon[2] = (ImageView)view.findViewById(R.id.now_i_wicon9);
        i_w_icon[3] = (ImageView)view.findViewById(R.id.now_i_wicon12);

        d_w_icon[0] = (ImageView)view.findViewById(R.id.now_d_wicon3);
        d_w_icon[1] = (ImageView)view.findViewById(R.id.now_d_wicon6);
        d_w_icon[2] = (ImageView)view.findViewById(R.id.now_d_wicon9);
        d_w_icon[3] = (ImageView)view.findViewById(R.id.now_d_wicon12);

        b_w_icon[0] = (ImageView)view.findViewById(R.id.now_b_wicon3);
        b_w_icon[1] = (ImageView)view.findViewById(R.id.now_b_wicon6);
        b_w_icon[2] = (ImageView)view.findViewById(R.id.now_b_wicon9);
        b_w_icon[3] = (ImageView)view.findViewById(R.id.now_b_wicon12);

        now_s_w_icon.setColorFilter(Color.WHITE);
        now_i_w_icon.setColorFilter(Color.WHITE);
        now_d_w_icon.setColorFilter(Color.WHITE);
        now_b_w_icon.setColorFilter(Color.WHITE);
        for(int i = 0; i < 4; i++)
        {
            s_w_icon[i].setColorFilter(Color.WHITE);
            i_w_icon[i].setColorFilter(Color.WHITE);
            d_w_icon[i].setColorFilter(Color.WHITE);
            b_w_icon[i].setColorFilter(Color.WHITE);
        }
    }

    private void now_weather_setting()
    {
        now_s_big_temp = (TextView)view.findViewById(R.id.now_temp1);
        now_i_big_temp = (TextView)view.findViewById(R.id.now_i_temp1);
        now_d_big_temp = (TextView)view.findViewById(R.id.now_d_temp1);
        now_b_big_temp = (TextView)view.findViewById(R.id.now_b_temp1);

        Date date = new Date();
        SimpleDateFormat hourformat = new SimpleDateFormat("HH");
        String hourStr = hourformat.format(date);
        int hour = Integer.parseInt(hourStr);
        Gps_Trans gps_trans = new Gps_Trans();

        locInfo = gps_trans.trans_x_y(0,s_lat,s_lon);
        GetWeatherInfo current = new GetWeatherInfo();
        CurrentWeatherPOJO currentweather;
        currentweather = current.getCurrentWeather(locInfo.lat,locInfo.lon);
        now_s_w_icon.setImageResource(get_icons(currentweather.getSky(),currentweather.getPty(),hour));
        now_s_big_temp.setText(currentweather.getTemp() + "℃");

        locInfo = gps_trans.trans_x_y(0,i_lat,i_lon);
        Log.d("인천 위치 ","" + locInfo.lat + " " +locInfo.lon);
        currentweather = current.getCurrentWeather(locInfo.lat,locInfo.lon);
        now_i_w_icon.setImageResource(get_icons(currentweather.getSky(),currentweather.getPty(),hour));
        now_i_big_temp.setText(currentweather.getTemp() + "℃");

        locInfo = gps_trans.trans_x_y(0,d_lat,d_lon);
        Log.d("대구 위치 ","" + locInfo.lat + " " +locInfo.lon);
        currentweather = current.getCurrentWeather(locInfo.lat,locInfo.lon);
        now_d_w_icon.setImageResource(get_icons(currentweather.getSky(),currentweather.getPty(),hour));
        now_d_big_temp.setText(currentweather.getTemp() + "℃");

        locInfo = gps_trans.trans_x_y(0,b_lat,b_lon);
        Log.d("부산 위치 ","" + locInfo.lat + " " +locInfo.lon);
        currentweather = current.getCurrentWeather(locInfo.lat,locInfo.lon);
        now_b_w_icon.setImageResource(get_icons(currentweather.getSky(),currentweather.getPty(),hour));
        now_b_big_temp.setText(currentweather.getTemp() + "℃");
    }

    private void hours_weather()
    {
        now_s_hour[0] = (TextView)view.findViewById(R.id.now_hp3);
        now_s_hour[1] = (TextView)view.findViewById(R.id.now_hp6);
        now_s_hour[2] = (TextView)view.findViewById(R.id.now_hp9);
        now_s_hour[3] = (TextView)view.findViewById(R.id.now_hp12);

        now_i_hour[0] = (TextView)view.findViewById(R.id.now_i_hp3);
        now_i_hour[1] = (TextView)view.findViewById(R.id.now_i_hp6);
        now_i_hour[2] = (TextView)view.findViewById(R.id.now_i_hp9);
        now_i_hour[3] = (TextView)view.findViewById(R.id.now_i_hp12);

        now_d_hour[0] = (TextView)view.findViewById(R.id.now_d_hp3);
        now_d_hour[1] = (TextView)view.findViewById(R.id.now_d_hp6);
        now_d_hour[2] = (TextView)view.findViewById(R.id.now_d_hp9);
        now_d_hour[3] = (TextView)view.findViewById(R.id.now_d_hp12);

        now_b_hour[0] = (TextView)view.findViewById(R.id.now_b_hp3);
        now_b_hour[1] = (TextView)view.findViewById(R.id.now_b_hp6);
        now_b_hour[2] = (TextView)view.findViewById(R.id.now_b_hp9);
        now_b_hour[3] = (TextView)view.findViewById(R.id.now_b_hp12);

        now_s_temp[0] = (TextView)view.findViewById(R.id.now_temp2);
        now_s_temp[1] = (TextView)view.findViewById(R.id.now_temp3);
        now_s_temp[2] = (TextView)view.findViewById(R.id.now_temp4);
        now_s_temp[3] = (TextView)view.findViewById(R.id.now_temp5);

        now_i_temp[0] = (TextView)view.findViewById(R.id.now_i_temp2);
        now_i_temp[1] = (TextView)view.findViewById(R.id.now_i_temp3);
        now_i_temp[2] = (TextView)view.findViewById(R.id.now_i_temp4);
        now_i_temp[3] = (TextView)view.findViewById(R.id.now_i_temp5);

        now_d_temp[0] = (TextView)view.findViewById(R.id.now_d_temp2);
        now_d_temp[1] = (TextView)view.findViewById(R.id.now_d_temp3);
        now_d_temp[2] = (TextView)view.findViewById(R.id.now_d_temp4);
        now_d_temp[3] = (TextView)view.findViewById(R.id.now_d_temp5);

        now_b_temp[0] = (TextView)view.findViewById(R.id.now_b_temp2);
        now_b_temp[1] = (TextView)view.findViewById(R.id.now_b_temp3);
        now_b_temp[2] = (TextView)view.findViewById(R.id.now_b_temp4);
        now_b_temp[3] = (TextView)view.findViewById(R.id.now_b_temp5);

        Gps_Trans gps_trans = new Gps_Trans();
        locInfo = gps_trans.trans_x_y(0,s_lat,s_lon);
        GetWeatherInfo getWeatherInfo = new GetWeatherInfo();
        ArrayList<DongNeWeatherPOJO> seoulWeatherInfo = getWeatherInfo.getTodayWeather(locInfo.lat,locInfo.lon);
        for(int i=0;i<4;i++)
        {
            String hour = seoulWeatherInfo.get(i).getFcstTime();
            int hourInt = Integer.parseInt(hour) / 100;
            now_s_hour[i].setText("" + hourInt + "시");
            s_w_icon[i].setImageResource(get_icons(seoulWeatherInfo.get(i).getSky(),seoulWeatherInfo.get(i).getPty(),hourInt));
            now_s_temp[i].setText(seoulWeatherInfo.get(i).getTemp()+"℃");
        }

        locInfo = gps_trans.trans_x_y(0,i_lat,i_lon);
        ArrayList<DongNeWeatherPOJO> incheonWeatherInfo = getWeatherInfo.getTodayWeather(locInfo.lat,locInfo.lon);
        for(int i=0;i<4;i++)
        {
            String hour = incheonWeatherInfo.get(i).getFcstTime();
            int hourInt = Integer.parseInt(hour) / 100;
            now_i_hour[i].setText("" + hourInt + "시");
            i_w_icon[i].setImageResource(get_icons(incheonWeatherInfo.get(i).getSky(),incheonWeatherInfo.get(i).getPty(),hourInt));
            now_i_temp[i].setText(incheonWeatherInfo.get(i).getTemp()+"℃");
        }

        locInfo = gps_trans.trans_x_y(0,d_lat,d_lon);
        ArrayList<DongNeWeatherPOJO> daeguWeatherInfo = getWeatherInfo.getTodayWeather(locInfo.lat,locInfo.lon);
        for(int i=0;i<4;i++)
        {
            String hour = daeguWeatherInfo.get(i).getFcstTime();
            int hourInt = Integer.parseInt(hour) / 100;
            now_d_hour[i].setText("" + hourInt + "시");
            d_w_icon[i].setImageResource(get_icons(daeguWeatherInfo.get(i).getSky(),daeguWeatherInfo.get(i).getPty(),hourInt));
            now_d_temp[i].setText(daeguWeatherInfo.get(i).getTemp()+"℃");
        }
        locInfo = gps_trans.trans_x_y(0,b_lat,b_lon);
        ArrayList<DongNeWeatherPOJO> busanWeatherInfo = getWeatherInfo.getTodayWeather(locInfo.lat,locInfo.lon);
        for(int i=0;i<4;i++)
        {
            String hour = busanWeatherInfo.get(i).getFcstTime();
            int hourInt = Integer.parseInt(hour) / 100;
            now_b_hour[i].setText("" + hourInt + "시");
            b_w_icon[i].setImageResource(get_icons(busanWeatherInfo.get(i).getSky(),busanWeatherInfo.get(i).getPty(),hourInt));
            now_b_temp[i].setText(busanWeatherInfo.get(i).getTemp()+"℃");
        }

    }

    private int get_icons(String sky, String pty,int hour) {
        if (hour >= 6 && hour <= 18) {
            if (sky.equals("1")) {
                return R.drawable.sunny;
            } else if (sky.equals("3")) {
                if (pty.equals("0")) {
                    return R.drawable.day_cloudy;
                } else if (pty.equals("1")) {
                    return R.drawable.rain;
                } else if (pty.equals("2")) {
                    return R.drawable.day_rain_mix;
                } else if (pty.equals("3")) {
                    return R.drawable.snow;
                } else if (pty.equals("4")) {
                    return R.drawable.day_showers;
                }
            } else {
                if (pty.equals("0")) {
                    return R.drawable.cloudy_sky;
                } else if (pty.equals("1")) {
                    return R.drawable.rain;
                } else if (pty.equals("2")) {
                    return R.drawable.rain_mix;
                } else if (pty.equals("3")) {
                    return R.drawable.snow;
                } else if (pty.equals("4")) {
                    return R.drawable.showers;
                }

            }
        } else {
            if (sky.equals("1")) {
                return R.drawable.night_clear;
            } else if (sky.equals("3")) {
                if (pty.equals("0")) {
                    return R.drawable.night_cloudy;
                } else if (pty.equals("1")) {
                    return R.drawable.rain;
                } else if (pty.equals("2")) {
                    return R.drawable.rain_mix;
                } else if (pty.equals("3")) {
                    return R.drawable.snow;
                } else if (pty.equals("4")) {
                    return R.drawable.showers;
                }
            } else {
                if (pty.equals("0")) {
                    return R.drawable.cloudy_sky;
                } else if (pty.equals("1")) {
                    return R.drawable.rain;
                } else if (pty.equals("2")) {
                    return R.drawable.rain_mix;
                } else if (pty.equals("3")) {
                    return R.drawable.snow;
                } else if (pty.equals("4")) {
                    return R.drawable.showers;
                }
            }
        }
        return -1;
    }


}
