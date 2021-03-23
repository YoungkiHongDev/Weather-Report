package com.example.weatherreport;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GetAirPollutionInfoAsync extends AsyncTask<String,Void, Document> {
    Document doc;
    @Override
    protected Document doInBackground(String... strings) {
        String stationName = strings[0];
        String serviceKey = "Please Insert Your API Key!";
        try{
            stationName = URLEncoder.encode(stationName,"utf-8");
            String urlStr = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?"
                    + "stationName=" + stationName + "&dataTerm=DAILY&pageNo=1&numOfRows=10"
                    + "&ServiceKey=" + serviceKey + "&ver=1.3";
            URL url = new URL(urlStr);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return doc;
    }
}
