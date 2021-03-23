package com.example.weatherreport;

import android.os.AsyncTask;
import android.util.Log;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class FindDustMsStaionAsync extends AsyncTask<String,Void, Document> {
    Document doc;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Document doInBackground(String... strings) {
        String addrStr = strings[0];

        try{
            addrStr = URLEncoder.encode(addrStr,"utf-8");
            String serviceKey = "Please Insert Your API Key!";
            String urlStr = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getTMStdrCrdnt?"
                    +"ServiceKey=" + serviceKey + "&pageNo=1&numOfRows=10"+ "&umdName=" + addrStr;
            URL url = new URL(urlStr);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
        } catch (Exception e)
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
