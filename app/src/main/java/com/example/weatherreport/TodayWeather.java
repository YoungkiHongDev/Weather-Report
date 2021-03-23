package com.example.weatherreport;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class TodayWeather extends Fragment {
    View view;
    TextView cido;
    TextView gugun;
    TextView now;
    TextView currentWtDes;
    TextView currentTemp;
    ImageView current_weather_icon;
    TextView[] hours = new TextView[6];
    TextView[] pops = new TextView[6];
    ImageView[] weather_icons = new ImageView[6];
    private LineChart lineChart;
    GetGpsInfo gpsInfo;

    public TodayWeather() {

    }

    public String getBase64String(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }

    public void saveSharedPreferences() {
        Drawable drawable = current_weather_icon.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        String gugunValue = gugun.getText().toString();
        String weatherValue = getBase64String(bitmap);
        String tempValue =  currentTemp.getText().toString();

        SharedPreferences pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("gugun", gugunValue);
        editor.putString("weather", weatherValue);
        editor.putString("temp", tempValue);
        editor.apply();
    }

    public void bgSetting() {
        int bgSetNum = 0;
        Bitmap bitCur = ((BitmapDrawable)current_weather_icon.getDrawable()).getBitmap();
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

        if(bitCur.equals(bitSunny)) { bgSetNum = 1;}
        else if(bitCur.equals(bitDayCloudy)) { bgSetNum = 2; }
        else if(bitCur.equals(bitNightClear) || bitCur.equals(bitNightCloudy)) { bgSetNum = 3; }
        else if(bitCur.equals(bitCloudySky)) { bgSetNum = 4; }
        else if(bitCur.equals(bitFog)) { bgSetNum = 5; }
        else if(bitCur.equals(bitRain) || bitCur.equals(bitDayRain) || bitCur.equals(bitNightRain) || bitCur.equals(bitShowers) || bitCur.equals(bitDayShowers) || bitCur.equals(bitNightShowers)) { bgSetNum = 6; }
        else if(bitCur.equals(bitSnow) || bitCur.equals(bitDaySnow) || bitCur.equals(bitNightSnow)) { bgSetNum = 7; }
        else if(bitCur.equals(bitRainMix) || bitCur.equals(bitDayRainMix) || bitCur.equals(bitNightRainMix)) { bgSetNum = 8; }
        else if(bitCur.equals(bitStormCloud) || bitCur.equals(bitSunStorm)) { bgSetNum = 9; }

        if(bgSetNum != 0) {
            SharedPreferences pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("Background", bgSetNum);
            editor.apply();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_today_weather, container, false);

        getWeatherInfo();
        saveSharedPreferences();

        ImageView shirts = (ImageView) view.findViewById(R.id.shirts);
        shirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toastView = new Toast(getActivity());
                ImageView img = new ImageView(getActivity());

                CampusApplication airTempGet = (CampusApplication)getActivity().getApplication();
                int airTemp;
                airTemp = airTempGet.getAirtempData();

                if(airTemp >= 28) img.setImageResource(R.drawable.airtemp1);
                else if(airTemp <= 27 && airTemp >= 23) img.setImageResource(R.drawable.airtemp2);
                else if(airTemp <= 22 && airTemp >= 20) img.setImageResource(R.drawable.airtemp3);
                else if(airTemp <= 19 && airTemp >= 17) img.setImageResource(R.drawable.airtemp4);
                else if(airTemp <= 16 && airTemp >= 12) img.setImageResource(R.drawable.airtemp5);
                else if(airTemp <= 11 && airTemp >= 9) img.setImageResource(R.drawable.airtemp6);
                else if(airTemp <= 8 && airTemp >= 5) img.setImageResource(R.drawable.airtemp7);
                else if(airTemp <= 4) img.setImageResource(R.drawable.airtemp8);
                toastView.setView(img);
                toastView.setDuration(Toast.LENGTH_SHORT);
                toastView.setGravity(Gravity.CENTER, 0, 0);
                toastView.show();
            }
        });

        shirts.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("지금 입고싶은 옷이 무엇인가요?");

                final EditText inputText = new EditText(getContext());
                alert.setView(inputText);

                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CampusApplication airTempGet = (CampusApplication)getActivity().getApplication();
                        int airTemp;
                        airTemp = airTempGet.getAirtempData();
                        String inputClothes = inputText.getText().toString();

                        if(inputClothes.equals("민소매") || inputClothes.equals("나시")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("반팔") || inputClothes.equals("반팔셔츠") || inputClothes.equals("반팔 셔츠")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 23){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("반바지")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 23){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("원피스")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("얇은셔츠") || inputClothes.equals("얇은 셔츠")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("면바지")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 12){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("얇은가디건") || inputClothes.equals("얇은 가디건")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("긴팔") || inputClothes.equals("긴팔셔츠") || inputClothes.equals("긴팔 셔츠")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("청바지")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 9){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("얇은니트") || inputClothes.equals("얇은 니트")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("맨투맨")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("가디건")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 12){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("자켓") || inputClothes.equals("재킷")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 9){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("야상") || inputClothes.equals("바람막이")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 9){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("스타킹")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 9){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("트렌치코트") || inputClothes.equals("트렌치 코트")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("니트")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 5){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("코트")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("가죽자켓") || inputClothes.equals("가죽 자켓") || inputClothes.equals("가죽재킷") || inputClothes.equals("가죽 재킷")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("히트텍") || inputClothes.equals("발열내의") || inputClothes.equals("발열 내의")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("레깅스")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes8);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("패딩") || inputClothes.equals("롱패딩")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("두꺼운코트") || inputClothes.equals("두꺼운 코트")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.equals("목도리") || inputClothes.equals("머플러")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                        else if(inputClothes.contains("기모")) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            ImageView imagelog = new ImageView(getContext());
                            if(airTemp >= 28) {
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes1);
                            }
                            else if(airTemp <= 27 && airTemp >= 23){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes2);
                            }
                            else if(airTemp <= 22 && airTemp >= 20){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes3);
                            }
                            else if(airTemp <= 19 && airTemp >= 17){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes4);
                            }
                            else if(airTemp <= 16 && airTemp >= 12){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes5);
                            }
                            else if(airTemp <= 11 && airTemp >= 9){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes6);
                            }
                            else if(airTemp <= 8 && airTemp >= 5){
                                dialog.setTitle("이런 옷은 어떠신가요?");
                                dialog.setMessage(airTemp+"℃에는 이런 옷을 추천해드려요!");
                                imagelog.setImageResource(R.drawable.clothes7);
                            }
                            else if(airTemp <= 4){
                                dialog.setTitle("정말 좋은 옷이네요!");
                                imagelog.setImageResource(R.drawable.check);
                            }
                            dialog.setView(imagelog);
                            dialog.show();
                        }
                    }
                });

                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alert.show();
                return true;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        LinearLayout todayLayout = (LinearLayout) view.findViewById(R.id.today_layout);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        int bgGetNum =  sharedPreferences.getInt("Background", 1);

        switch (bgGetNum) {
            case 1:
                todayLayout.setBackgroundResource(R.drawable.bg_sunny);
                break;
            case 2:
                todayLayout.setBackgroundResource(R.drawable.bg_day);
                break;
            case 3:
                todayLayout.setBackgroundResource(R.drawable.bg_night);
                break;
            case 4:
                todayLayout.setBackgroundResource(R.drawable.bg_cloudy);
                break;
            case 5:
                todayLayout.setBackgroundResource(R.drawable.bg_fog);
                break;
            case 6:
                todayLayout.setBackgroundResource(R.drawable.bg_rain);
                break;
            case 7:
                todayLayout.setBackgroundResource(R.drawable.bg_snow);
                break;
            case 8:
                todayLayout.setBackgroundResource(R.drawable.bg_rainmix);
                break;
            case 9:
                todayLayout.setBackgroundResource(R.drawable.bg_storm);
                break;
        }
        super.onResume();
    }

    private void getWeatherInfo()
    {
        double lat;
        double lon;
        gpsInfo = new GetGpsInfo(getActivity());
        Gps_Trans gps_trans = new Gps_Trans();
        lat = gpsInfo.getLatitude();
        lon = gpsInfo.getLongitude();
        LocInfo locInfo = gps_trans.trans_x_y(0,lat,lon);
        GetWeatherInfo test = new GetWeatherInfo();
        test.getCurrentWeather(locInfo.lat,locInfo.lon);
        currentWeatherSetting(lat,lon);
        dongNeWeather(locInfo.lat,locInfo.lon);
    }

    private void currentWeatherSetting(double lat, double lon)
    {
        double latitude = lat;
        double longitude = lon;

        cido = (TextView)view.findViewById(R.id.city);
        gugun = (TextView)view.findViewById(R.id.gu_eup_myun_dong);
        now = (TextView)view.findViewById(R.id.date);
        currentTemp = (TextView)view.findViewById(R.id.current_temp);
        currentWtDes = (TextView)view.findViewById(R.id.current_des);
        current_weather_icon = (ImageView)view.findViewById(R.id.current_weather);

        Date date = new Date();

        SimpleDateFormat todayformat = new SimpleDateFormat("M월 d일 E요일 HH시 mm분");
        SimpleDateFormat hourfomat = new SimpleDateFormat("HH");
        String today = todayformat.format(date);
        String hour = hourfomat.format(date);
        int hourInt = Integer.parseInt(hour);
        Gps_Trans gps_trans = new Gps_Trans();

        LocString currentLocation = gps_trans.getAddressStr(latitude,longitude);
        LocInfo locInfo = gps_trans.trans_x_y(0,latitude,longitude);
        GetWeatherInfo current = new GetWeatherInfo();

        CurrentWeatherPOJO currentWt = current.getCurrentWeather(locInfo.lat,locInfo.lon);

        cido.setText(currentLocation.getCity_do());
        gugun.setText(currentLocation.getGu_gun() + " " + currentLocation.getEup_myun() + " " + currentLocation.getLegalDong());
        now.setText(today);

        int airTemp = Integer.parseInt(currentWt.getTemp());
        CampusApplication airTempSet = (CampusApplication)getActivity().getApplicationContext();
        airTempSet.setAirtempData(airTemp);

        currentTemp.setText(currentWt.getTemp() + "℃");
        current_weather_icon.setImageResource(get_icons(currentWt.getSky(),currentWt.getPty(),hourInt));
        current_weather_icon.setColorFilter(Color.WHITE);
        currentWtDes.setText(getDes(currentWt.getSky(),currentWt.getPty()));

        bgSetting();
    }

    private void dongNeWeather(double lat, double lon)
    {
        weather_icons[0] = (ImageView)view.findViewById(R.id.later3);
        weather_icons[1] = (ImageView)view.findViewById(R.id.later6);
        weather_icons[2] = (ImageView)view.findViewById(R.id.later9);
        weather_icons[3] = (ImageView)view.findViewById(R.id.later12);
        weather_icons[4] = (ImageView)view.findViewById(R.id.later15);
        weather_icons[5] = (ImageView)view.findViewById(R.id.later18);

        hours[0] = (TextView)view.findViewById(R.id.time3);
        hours[1] = (TextView)view.findViewById(R.id.time6);
        hours[2] = (TextView)view.findViewById(R.id.time9);
        hours[3] = (TextView)view.findViewById(R.id.time12);
        hours[4] = (TextView)view.findViewById(R.id.time15);
        hours[5] = (TextView)view.findViewById(R.id.time18);

        pops[0] = (TextView)view.findViewById(R.id.pop3);
        pops[1] = (TextView)view.findViewById(R.id.pop6);
        pops[2] = (TextView)view.findViewById(R.id.pop9);
        pops[3] = (TextView)view.findViewById(R.id.pop12);
        pops[4] = (TextView)view.findViewById(R.id.pop15);
        pops[5] = (TextView)view.findViewById(R.id.pop18);

        List<Entry> entries = new ArrayList<>();

        GetWeatherInfo getWeatherInfo = new GetWeatherInfo();
        ArrayList<DongNeWeatherPOJO>dongNeWeatherInfo = getWeatherInfo.getTodayWeather(lat,lon);
        for(int i=0;i<dongNeWeatherInfo.size();i++)
        {
            String hour = dongNeWeatherInfo.get(i).getFcstTime();
            int hourInt = Integer.parseInt(hour) / 100;
            hours[i].setText("" + hourInt + "시");
            weather_icons[i].setImageResource(get_icons(dongNeWeatherInfo.get(i).getSky(),dongNeWeatherInfo.get(i).getPty(),hourInt));
            weather_icons[i].setColorFilter(Color.WHITE);
            pops[i].setText(dongNeWeatherInfo.get(i).getPop() + "%");
            entries.add(new Entry(i + 1,Integer.parseInt(dongNeWeatherInfo.get(i).getTemp())));
        }
        LineDataSet dataset = new LineDataSet(entries,null);
        drawLineChart(dataset);
    }

    private void drawLineChart(LineDataSet dataSet)
    {
        LineDataSet lineDataSet = dataSet;
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setValueTextColor(Color.WHITE);
        LineData data = new LineData(lineDataSet);

        lineChart = (LineChart)view.findViewById(R.id.chart);
        lineChart.setDescription(null);
        lineChart.setScaleEnabled(false);
        lineChart.setData(data);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        YAxis yAxisRight = lineChart.getAxisRight();
        YAxis yAxisLeft = lineChart.getAxisLeft();
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGridColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);

        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setDrawLabels(false);
        yAxisLeft.setDrawGridLines(false);
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);
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

    private String getDes(String sky, String pty)
    {

        if (sky.equals("1"))
        {
            return "맑음";
        }
        else if (sky.equals("3"))
        {
            if (pty.equals("0"))
            {
                return "구름 많음";
            }
            else if (pty.equals("1"))
            {
                return "비";
            }
            else if (pty.equals("2"))
            {
                return "진눈깨비";
            }
            else if (pty.equals("3"))
            {
                return "눈";
            }
            else if (pty.equals("4"))
            {
                return "소나기";
            }
        }
        else if(sky.equals("4"))
        {
            if (pty.equals("0"))
            {
                return "흐림";
            }
            else if (pty.equals("1"))
            {
                return "비";
            }
            else if (pty.equals("2"))
            {
                return "진눈깨비";
            }
            else if (pty.equals("3"))
            {
                return "눈";
            }
            else if (pty.equals("4"))
            {
                return "소나기";
            }
        }
        return "";
    }
}
