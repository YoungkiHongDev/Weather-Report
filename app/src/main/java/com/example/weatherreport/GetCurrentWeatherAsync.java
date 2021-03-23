package com.example.weatherreport;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class GetCurrentWeatherAsync extends AsyncTask<Double,Void, JSONObject> {
    JSONObject jsonObject;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Double... doubles) {
        double lat = doubles[0];
        double lon = doubles[1];
        int nx = (int)lat;
        int ny = (int)lon;
        String hour;
        String baseTime;
        String baseDate;
        String minute;
        int hourInt;
        SimpleDateFormat hourformat = new SimpleDateFormat("HH");
        SimpleDateFormat minuteformat = new SimpleDateFormat("mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        hour = hourformat.format(calendar.getTime());
        minute = minuteformat.format(calendar.getTime());
        hourInt = timecheck(minute, hour);
        if(hourInt < 10) {
            baseTime = "0" + hourInt;
        }
        else
        {
            baseTime = "" + hourInt;
        }
        baseDate = dateFormat.format(calendar.getTime());
        String serviceKey = "Please Insert Your API Key!";
        String currentFscUrl =  "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtFcst?"
                + "serviceKey=" + serviceKey + "&numOfRows=30&pageNo=1" + "&base_date=" + baseDate
                + "&base_time=" + baseTime + "30" + "&nx=" + nx + "&ny=" + ny + "&dataType=JSON";
        try{
            URL url = new URL(currentFscUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = "";
            String result;
            while ((line = br.readLine())!= null)
            {
                sb.append(line);
            }
            result = sb.toString();
            jsonObject = new JSONObject(result);
            conn.disconnect();
            br.close();
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    private int timecheck(String minute,String hour)
    {
        int minuteInt = Integer.parseInt(minute);
        int hourInt = Integer.parseInt(hour);
        if(minuteInt < 30){
            hourInt = hourInt - 1;
            if(hourInt < 0){
                // 자정 이전은 전날로 계산
                calendar.add(Calendar.DATE, -1);
                hourInt = 23;
            }
        }
        return hourInt;
    }
    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
