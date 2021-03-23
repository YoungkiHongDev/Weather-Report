
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

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class WeekWeather extends Fragment {
    View view;
    ImageView[] weekIcons = new ImageView[7];
    TextView[] weekdates = new TextView[7];
    TextView[] weekdes = new TextView[7];
    TextView[] maxTemp = new TextView[7];
    TextView[] minTemp = new TextView[7];
    public WeekWeather() {
        // Required empty public constructor
    }

    public void saveSharedPreferences() {
        String[] weekWeather = new String[7];
        String[] weekDay = new String[7];
        String[] weekHighTemp = new String[7];
        String[] weekLowTemp = new String[7];

        SharedPreferences pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        for(int i=0; i<7; i++) {
            weekWeather[i] = weekdes[i].getText().toString();
            weekDay[i] = weekdates[i].getText().toString();
            weekHighTemp[i] = maxTemp[i].getText().toString();
            weekLowTemp[i] = minTemp[i].getText().toString();

            editor.putString("weekWeather" + i, weekWeather[i]);
            editor.putString("weekDay" + i, weekDay[i]);
            editor.putString("weekHighTemp" + i, weekHighTemp[i]);
            editor.putString("weekLowTemp" + i, weekLowTemp[i]);
        }
        editor.apply();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_week_weather, container, false);
        showWeekWeather();
        saveSharedPreferences();

        return view;
    }

    @Override
    public void onResume() {
        LinearLayout weekLayout = (LinearLayout) view.findViewById(R.id.week_layout);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        int bgGetNum =  sharedPreferences.getInt("Background", 1);

        switch (bgGetNum) {
            case 1:
                weekLayout.setBackgroundResource(R.drawable.bg_sunny);
                break;
            case 2:
                weekLayout.setBackgroundResource(R.drawable.bg_day);
                break;
            case 3:
                weekLayout.setBackgroundResource(R.drawable.bg_night);
                break;
            case 4:
                weekLayout.setBackgroundResource(R.drawable.bg_cloudy);
                break;
            case 5:
                weekLayout.setBackgroundResource(R.drawable.bg_fog);
                break;
            case 6:
                weekLayout.setBackgroundResource(R.drawable.bg_rain);
                break;
            case 7:
                weekLayout.setBackgroundResource(R.drawable.bg_snow);
                break;
            case 8:
                weekLayout.setBackgroundResource(R.drawable.bg_rainmix);
                break;
            case 9:
                weekLayout.setBackgroundResource(R.drawable.bg_storm);
                break;
        }
        super.onResume();
    }

    private void showWeekWeather()
    {
        id_matches();
        GetWeatherInfo weatherInfo = new GetWeatherInfo();
        GetGpsInfo gpsInfo = new GetGpsInfo(getActivity());
        double lat = gpsInfo.getLatitude();
        double lon = gpsInfo.getLongitude();
        ArrayList<WeekWeatherPOJO> weekWeathers = weatherInfo.getWeekWeather(lat,lon);
        weekWeathers.remove(0);
        for(int i=0;i<weekWeathers.size();i++)
        {
            int tmax = (int)Math.floor(weekWeathers.get(i).getTmax());
            int tmin = (int)Math.floor(weekWeathers.get(i).getTmin());
            weekIcons[i].setImageResource(id_To_icons(weekWeathers.get(i).getId()));
            weekIcons[i].setColorFilter(Color.WHITE);
            weekdates[i].setText(dt_trans(weekWeathers.get(i).getDate()));
            weekdes[i].setText(id_To_String(weekWeathers.get(i).getId()));
            maxTemp[i].setText("" + tmax + "℃");
            minTemp[i].setText("" + tmin + "℃");
        }
    }

    private String dt_trans(long datetime)
    {
        long trans_dt = datetime * 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(trans_dt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd E요일");
        String dateStr = dateFormat.format(calendar.getTime());
        return dateStr;
    }

    private void id_matches()
    {
        weekIcons[0] = (ImageView) view.findViewById(R.id.weekIcon1);
        weekIcons[1] = (ImageView) view.findViewById(R.id.weekIcon2);
        weekIcons[2] = (ImageView) view.findViewById(R.id.weekIcon3);
        weekIcons[3] = (ImageView) view.findViewById(R.id.weekIcon4);
        weekIcons[4] = (ImageView) view.findViewById(R.id.weekIcon5);
        weekIcons[5] = (ImageView) view.findViewById(R.id.weekIcon6);
        weekIcons[6] = (ImageView) view.findViewById(R.id.weekIcon7);

        weekdates[0] = (TextView) view.findViewById(R.id.weekdate1);
        weekdates[1] = (TextView) view.findViewById(R.id.weekdate2);
        weekdates[2] = (TextView) view.findViewById(R.id.weekdate3);
        weekdates[3] = (TextView) view.findViewById(R.id.weekdate4);
        weekdates[4] = (TextView) view.findViewById(R.id.weekdate5);
        weekdates[5] = (TextView) view.findViewById(R.id.weekdate6);
        weekdates[6] = (TextView) view.findViewById(R.id.weekdate7);

        weekdes[0] = (TextView)view.findViewById(R.id.weekdes1);
        weekdes[1] = (TextView)view.findViewById(R.id.weekdes2);
        weekdes[2] = (TextView)view.findViewById(R.id.weekdes3);
        weekdes[3] = (TextView)view.findViewById(R.id.weekdes4);
        weekdes[4] = (TextView)view.findViewById(R.id.weekdes5);
        weekdes[5] = (TextView)view.findViewById(R.id.weekdes6);
        weekdes[6] = (TextView)view.findViewById(R.id.weekdes7);

        maxTemp[0] = (TextView)view.findViewById(R.id.weekMaxTemp1);
        maxTemp[1] = (TextView)view.findViewById(R.id.weekMaxTemp2);
        maxTemp[2] = (TextView)view.findViewById(R.id.weekMaxTemp3);
        maxTemp[3] = (TextView)view.findViewById(R.id.weekMaxTemp4);
        maxTemp[4] = (TextView)view.findViewById(R.id.weekMaxTemp5);
        maxTemp[5] = (TextView)view.findViewById(R.id.weekMaxTemp6);
        maxTemp[6] = (TextView)view.findViewById(R.id.weekMaxTemp7);

        minTemp[0] = (TextView)view.findViewById(R.id.weekMinTemp1);
        minTemp[1] = (TextView)view.findViewById(R.id.weekMinTemp2);
        minTemp[2] = (TextView)view.findViewById(R.id.weekMinTemp3);
        minTemp[3] = (TextView)view.findViewById(R.id.weekMinTemp4);
        minTemp[4] = (TextView)view.findViewById(R.id.weekMinTemp5);
        minTemp[5] = (TextView)view.findViewById(R.id.weekMinTemp6);
        minTemp[6] = (TextView)view.findViewById(R.id.weekMinTemp7);
    }

    private int id_To_icons(int weatherId)
    {
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.storm_cloud;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.showers;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.rain;
        } else if (weatherId == 511) {
            return R.drawable.snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.snow;
        } else if (weatherId >= 701 && weatherId <= 781) {
            return R.drawable.fog;
        } else if (weatherId == 800) {
            return R.drawable.sunny;
        } else if (weatherId == 801) {
            return R.drawable.day_cloudy;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.cloudy_sky;
        }
        return -1;
    }

    private String id_To_String(int weatherId)
    {
        if (weatherId >= 200 && weatherId <= 232) {
            return "폭풍우";
        } else if (weatherId >= 300 && weatherId <= 321) {
            return "소나기";
        } else if (weatherId >= 500 && weatherId <= 504) {
            return "비";
        } else if (weatherId == 511) {
            return "눈";
        } else if (weatherId >= 520 && weatherId <= 531) {
            return "비";
        } else if (weatherId >= 600 && weatherId <= 622) {
            return "눈";
        } else if (weatherId >= 701 && weatherId <= 781) {
            return "안개";
        } else if (weatherId == 800) {
            return "맑음";
        } else if (weatherId == 801) {
            return "구름 조금";
        } else if (weatherId >= 802 && weatherId <= 804) {
            return "흐림";
        }
        return "";
    }

}
