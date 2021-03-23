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
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeekWeatherAsync extends AsyncTask<Double,Void,JSONObject> {
    JSONObject jsonObject;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Double... doubles) {
        double lat = doubles[0];
        double lon = doubles[1];
        String weekFscKey = "777ae7ae83d95b405c483b8d8d9f2c28";
        String weekFscUrl =  "https://api.openweathermap.org/data/2.5/onecall?"
                + "lat=" + lat + "&lon=" + lon +
                "&exclude=minutely,hourly,current&appid=" + weekFscKey + "&units=metric";
        try{
            URL url = new URL(weekFscUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
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
