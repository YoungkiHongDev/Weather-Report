package com.example.weatherreport;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Gps_Trans {

    public LocInfo trans_x_y(int mode,double lat_X, double lng_Y)
    {
        int TO_GRID = 0;
        int TO_GPS = 1;

        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)

        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        LocInfo lat_lon = new LocInfo();
        LocInfo rs = new LocInfo();

        if (mode == TO_GRID) {
            lat_lon.lat = lat_X;
            lat_lon.lon = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.lat = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs.lon = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else {
            rs.lat = lat_X;
            rs.lon = lng_Y;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            rs.lat = alat * RADDEG;
            rs.lon = alon * RADDEG;
        }
        return rs;
    }

    public LocString getAddressStr(double lat, double lon)
    {
        Double[] lat_lon = {lat,lon};
        LocString addressStr = new LocString();
        AddressTransAsync jsonGet = new AddressTransAsync();
        try{
            JSONObject jsonObject = jsonGet.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,lat_lon).get();
            JSONObject addressInfo = (JSONObject) jsonObject.get("addressInfo");
            addressStr.setCity_do(addressInfo.getString("city_do"));
            addressStr.setGu_gun(addressInfo.getString("gu_gun"));
            addressStr.setEup_myun(addressInfo.getString("eup_myun"));
            addressStr.setLegalDong(addressInfo.getString("legalDong"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return addressStr;
    }

    public String closeMsStation(double lat,double lon) {
        String mstaion = "";
        LocString addrStr = getAddressStr(lat,lon);
        String[] tmXY = new String[2];
        FindDustMsStaionAsync findtmXY = new FindDustMsStaionAsync();
        TmXYForFindDustMsStationAsync tmToMsStaion = new TmXYForFindDustMsStationAsync();
        Document tmXyDoc;
        Document msStationNameDoc;
        try{
            tmXyDoc = findtmXY.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,addrStr.getLegalDong()).get();
            NodeList nodeList = tmXyDoc.getElementsByTagName("item");
            for(int i=0;i < nodeList.getLength(); i++)
            {
                Node node = nodeList.item(i);
                Element element = (Element) node;
                NodeList sidoList = element.getElementsByTagName("sidoName");
                String sidoName = sidoList.item(0).getChildNodes().item(0).getNodeValue();
                NodeList sggList = element.getElementsByTagName("sggName");
                String sggName = sggList.item(0).getChildNodes().item(0).getNodeValue();
                NodeList umdList = element.getElementsByTagName("umdName");
                String umdName = umdList.item(0).getChildNodes().item(0).getNodeValue();
                if(sidoName.equals(addrStr.getCity_do()) && sggName.equals(addrStr.getGu_gun()) && umdName.equals(addrStr.getLegalDong()))
                {
                    NodeList tmxList = element.getElementsByTagName("tmX");
                    tmXY[0] = tmxList.item(0).getChildNodes().item(0).getNodeValue();
                    NodeList tmyList = element.getElementsByTagName("tmY");
                    tmXY[1] = tmyList.item(0).getChildNodes().item(0).getNodeValue();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try{
            msStationNameDoc = tmToMsStaion.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,tmXY).get();
            NodeList nodeList = msStationNameDoc.getElementsByTagName("item");
            Node node = nodeList.item(0);
            Element element = (Element) node;
            NodeList stationNameList = element.getElementsByTagName("stationName");
            mstaion = stationNameList.item(0).getChildNodes().item(0).getNodeValue();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return mstaion;
    }


}
