package com.example.weatherreport;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AirPollution extends Fragment {
    View view;
    TextView skyGrade;
    TextView checkTime;
    TextView pm10Value;
    TextView pm25Value;
    TextView o3Value;
    TextView no2Value;
    TextView coValue;
    TextView so2Value;

    public AirPollution() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void saveSharedPreferences() {
        String airState = skyGrade.getText().toString();

        SharedPreferences pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("airState", airState);
        editor.apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_air_pollution, container, false);
        onAirView();
        saveSharedPreferences();

        String pm10widget = pm10Value.getText().toString();
        String pm25widget = pm25Value.getText().toString();

        SharedPreferences pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("pm10", pm10widget);
        editor.putString("pm25", pm25widget);
        editor.apply();

        return view;
    }

    @Override
    public void onResume() {
        LinearLayout airLayout = (LinearLayout) view.findViewById(R.id.air_layout);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        int bgGetNum =  sharedPreferences.getInt("Background", 1);

        switch (bgGetNum) {
            case 1:
                airLayout.setBackgroundResource(R.drawable.bg_sunny);
                break;
            case 2:
                airLayout.setBackgroundResource(R.drawable.bg_day);
                break;
            case 3:
                airLayout.setBackgroundResource(R.drawable.bg_night);
                break;
            case 4:
                airLayout.setBackgroundResource(R.drawable.bg_cloudy);
                break;
            case 5:
                airLayout.setBackgroundResource(R.drawable.bg_fog);
                break;
            case 6:
                airLayout.setBackgroundResource(R.drawable.bg_rain);
                break;
            case 7:
                airLayout.setBackgroundResource(R.drawable.bg_snow);
                break;
            case 8:
                airLayout.setBackgroundResource(R.drawable.bg_rainmix);
                break;
            case 9:
                airLayout.setBackgroundResource(R.drawable.bg_storm);
                break;
        }
        super.onResume();
    }

    private void onAirView()
    {
        double lat;
        double lon;
        AirPollutionPOJO airInfo = new AirPollutionPOJO();
        GetWeatherInfo getAirInfo = new GetWeatherInfo();
        GetGpsInfo gpsInfo = new GetGpsInfo(getActivity());
        lat = gpsInfo.getLatitude();
        lon = gpsInfo.getLongitude();
        airInfo = getAirInfo.getAirPollutionInfo(lat,lon);
        setDisplay(airInfo);
    }

    private void setDisplay(AirPollutionPOJO airInfo)
    {
        skyGrade = (TextView)view.findViewById(R.id.skyGrade);
        checkTime = (TextView)view.findViewById(R.id.checktime);
        pm10Value = (TextView)view.findViewById(R.id.pm10Value);
        pm25Value = (TextView)view.findViewById(R.id.pm25Value);
        so2Value = (TextView)view.findViewById(R.id.so2Value);
        o3Value = (TextView)view.findViewById(R.id.o3Value);
        no2Value = (TextView)view.findViewById(R.id.no2Value);
        coValue = (TextView)view.findViewById(R.id.coValue);

        if(airInfo.getKhaiGrade().equals("1"))
        {
            skyGrade.setText("좋음");
        }
        else if(airInfo.getKhaiGrade().equals("2"))
        {
            skyGrade.setText("보통");
        }
        else if (airInfo.getKhaiGrade().equals("3"))
        {
            skyGrade.setText("나쁨");
        }
        else if(airInfo.getKhaiGrade().equals("4"))
        {
            skyGrade.setText("매우나쁨");
        }
        checkTime.setText(airInfo.getDateTime());
        pm10Value.setText(airInfo.getPm10Value());
        pm25Value.setText(airInfo.getPm25Value());
        so2Value.setText(airInfo.getSo2Value());
        o3Value.setText(airInfo.getO3Value());
        no2Value.setText(airInfo.getNo2Value());
        coValue.setText(airInfo.getCoValue());
    }
}
