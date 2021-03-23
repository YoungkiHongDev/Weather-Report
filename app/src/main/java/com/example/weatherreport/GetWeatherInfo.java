package com.example.weatherreport;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GetWeatherInfo {

    public CurrentWeatherPOJO getCurrentWeather(double lat, double lon)
    {
        Double[] lat_lon = {lat,lon};
        JSONObject weatherData;
        CurrentWeatherPOJO currentWeather = new CurrentWeatherPOJO();
        GetCurrentWeatherAsync getWeather = new GetCurrentWeatherAsync();
        try{
            JSONObject jsonObject = getWeather.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,lat_lon).get();
            JSONObject response = jsonObject.getJSONObject("response");
            JSONObject body = response.getJSONObject("body");
            JSONObject items = body.getJSONObject("items");
            JSONArray item = items.getJSONArray("item");
            String info = "";
            int skycount = 0;
            int ptycount = 0;
            int tempcount = 0;
            for(int i = 0; i<item.length(); i++)
            {
                if(skycount == 1 && ptycount == 1 && tempcount == 1)
                    break;
                weatherData = item.getJSONObject(i);
                info = weatherData.getString("category");
                if(info.equals("PTY") && ptycount == 0) {
                    currentWeather.setPty(weatherData.getString("fcstValue"));
                    ptycount = 1;
                }
                else if(info.equals("SKY") && skycount == 0)
                {
                    currentWeather.setSky(weatherData.getString("fcstValue"));
                    skycount = 1;
                }
                else if(info.equals("T1H") && tempcount == 0)
                {
                    currentWeather.setTemp(weatherData.getString("fcstValue"));
                    tempcount = 1;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  currentWeather;
    }
    public ArrayList<DongNeWeatherPOJO> getTodayWeather(double nx, double ny)
    {
        Double[] nx_ny = {nx,ny};
        JSONObject weatherData;
        DongNeWeatherPOJO dongNePoJo = new DongNeWeatherPOJO();
        ArrayList<DongNeWeatherPOJO> dongNeWeatherInfo = new ArrayList<DongNeWeatherPOJO>();
        DongNeWeatherAsync acceptApi = new DongNeWeatherAsync();
        try{
            String info = "";
            JSONObject jsonObject = acceptApi.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,nx_ny).get();
            JSONObject response = jsonObject.getJSONObject("response");
            JSONObject body = response.getJSONObject("body");
            JSONObject items = body.getJSONObject("items");
            JSONArray item = items.getJSONArray("item");
            int count = 0;
            for(int i=0;i<item.length();i++)
            {
                if(count>=6) {
                    break;
                }
                weatherData = item.getJSONObject(i);
                info = weatherData.getString("category");
                dongNePoJo.setFcstTime(weatherData.getString("fcstTime"));
                if (info.equals("POP")) {
                    dongNePoJo.setPop(weatherData.getString("fcstValue"));
                }
                else if (info.equals("PTY")) {
                    dongNePoJo.setPty(weatherData.getString("fcstValue"));
                }
                else if (info.equals("SKY")) {
                    dongNePoJo.setSky(weatherData.getString("fcstValue"));
                }
                else if (info.equals("T3H")) {
                    dongNePoJo.setTemp(weatherData.getString("fcstValue"));
                }
                else if(info.equals("WSD"))
                {
                    dongNeWeatherInfo.add(dongNePoJo);
                    dongNePoJo = new DongNeWeatherPOJO();
                    count++;
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dongNeWeatherInfo;
    }

    public ArrayList<WeekWeatherPOJO> getWeekWeather(double lat, double lon)
    {
        JSONObject weatherObj;
        ArrayList<WeekWeatherPOJO> weekWeathers = new ArrayList<>();
        WeekWeatherPOJO weekWeatherPOJO;
        WeekWeatherAsync weekWeatherAsync = new WeekWeatherAsync();
        Double[] lat_lon = {lat,lon};

        try{
            weatherObj = weekWeatherAsync.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,lat_lon).get();
            JSONArray daily = weatherObj.getJSONArray("daily");
            for(int i=0; i < daily.length(); i++) {
                weekWeatherPOJO = new WeekWeatherPOJO();
                JSONObject object = daily.getJSONObject(i);
                JSONObject temp = object.getJSONObject("temp");
                JSONArray weather = object.getJSONArray("weather");
                JSONObject id = weather.getJSONObject(0);
                weekWeatherPOJO.setDate(object.getLong("dt"));
                weekWeatherPOJO.setTmin(temp.getDouble("min"));
                weekWeatherPOJO.setTmax(temp.getDouble("max"));
                weekWeatherPOJO.setId(id.getInt("id"));
                weekWeathers.add(weekWeatherPOJO);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weekWeathers;
    }
    public AirPollutionPOJO getAirPollutionInfo(double lat, double lon)
    {
        Document doc;
        String msStationName;
        Gps_Trans gps_trans = new Gps_Trans();
        GetAirPollutionInfoAsync getAirPullution = new GetAirPollutionInfoAsync();
        AirPollutionPOJO saveAirPollution = new AirPollutionPOJO();
        msStationName = gps_trans.closeMsStation(lat,lon);
        try{
            doc = getAirPullution.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,msStationName).get();
            NodeList nodeList = doc.getElementsByTagName("item");

            Node node = nodeList.item(0);
            Element element = (Element) node;

            NodeList dateTime = element.getElementsByTagName("dataTime");
            saveAirPollution.setDateTime(dateTime.item(0).getChildNodes().item(0).getNodeValue());
            NodeList so2Value = element.getElementsByTagName("so2Value");
            saveAirPollution.setSo2Value(so2Value.item(0).getChildNodes().item(0).getNodeValue());
            NodeList coValue = element.getElementsByTagName("coValue");
            saveAirPollution.setCoValue(coValue.item(0).getChildNodes().item(0).getNodeValue());
            NodeList o3Value = element.getElementsByTagName("o3Value");
            saveAirPollution.setO3Value(o3Value.item(0).getChildNodes().item(0).getNodeValue());
            NodeList no2Value = element.getElementsByTagName("no2Value");
            saveAirPollution.setNo2Value(no2Value.item(0).getChildNodes().item(0).getNodeValue());
            NodeList pm10Value = element.getElementsByTagName("pm10Value");
            saveAirPollution.setPm10Value(pm10Value.item(0).getChildNodes().item(0).getNodeValue());
            NodeList pm25Value = element.getElementsByTagName("pm25Value");
            saveAirPollution.setPm25Value(pm25Value.item(0).getChildNodes().item(0).getNodeValue());
            NodeList khaiGrade = element.getElementsByTagName("khaiGrade");
            saveAirPollution.setKhaiGrade(khaiGrade.item(0).getChildNodes().item(0).getNodeValue());

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if(saveAirPollution.getKhaiGrade() == null) {
            saveAirPollution.setKhaiGrade("");
        }

        return  saveAirPollution;
    }
}
