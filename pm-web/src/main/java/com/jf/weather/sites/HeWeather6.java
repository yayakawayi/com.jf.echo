package com.jf.weather.sites;

import com.jf.weather.weatherUtil.HttpUtil;
import com.jf.weather.Weather;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 将从HeWeather网站上抓取的天气数据整理成想要的数据结构
 */
public class HeWeather6 {
    private static String siteName = "heWeatherS6";
    private static String url = "https://free-api.heweather.com/s6/weather/forecast?key=5c043b56de9f4371b0c7f8bee8f5b75e&location=";
    public static Weather getWeather(String location) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(HttpUtil.getStringData(url, location));
            JSONObject heWeather6 = jsonObject.getJSONArray("HeWeather6").getJSONObject(0);
            if ("ok".equals(heWeather6.getString("status"))) {
                String city = heWeather6.getJSONObject("basic").getString("location");
                JSONArray daily_forecast = heWeather6.getJSONArray("daily_forecast");
//               取今天数据
                JSONObject todayWeather = daily_forecast.getJSONObject(0);
                String todayDate = todayWeather.getString("date");
                double today_tmp_max = todayWeather.getDouble("tmp_max");
                double today_tmp_min = todayWeather.getDouble("tmp_min");
                String today_tmp = (today_tmp_max + today_tmp_min) / 2 + "℃";
                String today_wind_dir = todayWeather.getString("wind_dir");
                String today_cond_txt_d = todayWeather.getString("cond_txt_d");
//                取未来2天的数据
                ArrayList<Weather> futureWeather = new ArrayList<>();
                for (int i = 1; i < 3; i++) {
                    JSONObject daily = JSONObject.fromObject(daily_forecast.getJSONObject(i));
                    String date = daily.getString("date");
                    double tmp_max = daily.getDouble("tmp_max");
                    double tmp_min = daily.getDouble("tmp_min");
                    String tmp = (tmp_max + tmp_min) / 2 + "℃";
                    String wind_dir = daily.getString("wind_dir");
                    String cond_txt_d = daily.getString("cond_txt_d");
                    futureWeather.add(new Weather(date, tmp, wind_dir, cond_txt_d));
                }
                return new Weather(siteName, city, todayDate, today_tmp, today_wind_dir, today_cond_txt_d, futureWeather);
            }
            return null;
        } catch (RuntimeException e) {
            return null;
        }
    }
    /**
     * 得到对应location经纬度
     */
    public static Map<String, String> getLngLat(String location) {
        HashMap<String, String> map = new HashMap<>();
        try {
            JSONObject jsonObject = JSONObject.fromObject(HttpUtil.getStringData(url, location));
            JSONObject heWeather6 = jsonObject.getJSONArray("HeWeather6").getJSONObject(0);
            String lng = heWeather6.getJSONObject("basic").getString("lon");
            String lat = heWeather6.getJSONObject("basic").getString("lat");
            map.put("lng", lng);
            map.put("lat", lat);
        } catch (RuntimeException e) {
            return null;
    }
        return map;
    }




}
