package com.example.weatherreport;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class TmXYForFindDustMsStationAsync extends AsyncTask<String,Void, Document> {
    Document doc;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Document doInBackground(String... strings) {
        String tmx = strings[0];
        String tmy = strings[1];
        String serviceKey = "Please Insert Your API Key!";
        String urlStr = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList?"
                + "ServiceKey=" + serviceKey + "&tmX=" + tmx + "&tmY=" + tmy ;
        try{
            URL url = new URL(urlStr);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return doc;
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
