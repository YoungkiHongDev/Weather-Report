package com.example.weatherreport;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AddressTransAsync extends AsyncTask<Double,Void, JSONObject> {
    JSONObject jsonObject;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Double... doubles) {
        double x = doubles[0];
        double y = doubles[1];
        String appKey = "Please Insert Your APP Key!";
        String addressType = "A02";
        String urlStr = "https://apis.openapi.sk.com/tmap/geo/reversegeocoding?"
                + "addressType=" + addressType + "&lat=" + x + "&lon=" + y
                + "&appKey=" + appKey
                ;
        try {
            URL url = new URL(urlStr);
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
