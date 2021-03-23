package com.example.weatherreport;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DongNeWeatherAsync extends AsyncTask<Double,Void, JSONObject> {

    JSONObject jsonObject;
    String baseDate;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Double... doubles) {
        double x = doubles[0];
        double y = doubles[1];
        int nx = (int)x;
        int ny = (int)y;
        String hour;
        String baseTime;
        SimpleDateFormat hourformat = new SimpleDateFormat("HH");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        hour = hourformat.format(calendar.getTime());
        baseTime = timeChange(hour);
        baseDate = dateFormat.format(calendar.getTime());
        String serviceKey = "Please Insert Your API Key!";
        int numberofrows = 65;
        String dongNeUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?"
                + "serviceKey=" + serviceKey + "&numOfRows=" + numberofrows + "&pageNo=1"
                + "&base_date=" + baseDate + "&base_time=" + baseTime + "&dataType=JSON"
                + "&nx=" + nx + "&ny=" + ny;
        try{
            URL url = new URL(dongNeUrl);
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
    private String timeChange(String hour)
    {
        int hourInt = Integer.parseInt(hour);
        String baseTime = "";
        if(hourInt < 3)
        {
            calendar.add(Calendar.DATE, -1);
            baseTime = "2300";
        }
        else if(hourInt < 6 )
        {
            baseTime = "0200";
        }
        else if(hourInt < 9)
        {
            baseTime = "0500";
        }
        else if(hourInt < 12)
        {
            baseTime = "0800";
        }
        else if(hourInt < 15)
        {
            baseTime = "1100";
        }
        else if(hourInt < 18)
        {
            baseTime = "1400";
        }
        else if(hourInt < 21)
        {
            baseTime = "1700";
        }
        else if(hourInt <= 23)
        {
            baseTime = "2000";
        }
        return baseTime;
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
