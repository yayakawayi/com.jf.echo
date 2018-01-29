package com.jf.weather.sites;

import com.jf.weather.weatherUtil.HttpUtil;
import com.jf.weather.Weather;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

public class HeWeather5 {
    private static String siteName = "heWeatherV5";
    private static String url = "https://free-api.heweather.com/v5/weather?key=5c043b56de9f4371b0c7f8bee8f5b75e&city=";

    public static Weather getWeather(String location) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(HttpUtil.getStringData(url, location));
            JSONObject heWeather5 = jsonObject.getJSONArray("HeWeather5").getJSONObject(0);
            if ("ok".equals(heWeather5.getString("status"))) {
                String city = heWeather5.getJSONObject("basic").getString("city");
                JSONArray daily_forecast = heWeather5.getJSONArray("daily_forecast");
//               取今天数据
                JSONObject todayWeather = daily_forecast.getJSONObject(0);
                String todayDate = todayWeather.getString("date");
                double today_tmp_max = todayWeather.getJSONObject("tmp").getDouble("max");
                double today_tmp_min = todayWeather.getJSONObject("tmp").getDouble("min");
                String today_tmp = (today_tmp_max + today_tmp_min) / 2 + "℃";
                String today_wind_sc = todayWeather.getJSONObject("wind").getString("sc");
                String today_cond_txt_d = todayWeather.getJSONObject("cond").getString("txt_d");
//                取未来2天的数据
                ArrayList<Weather> futureWeather = new ArrayList<>();
                for (int i = 1; i < 3; i++) {
                    JSONObject daily = JSONObject.fromObject(daily_forecast.getJSONObject(i));
                    String date = daily.getString("date");
                    Double tmp_max = daily.getJSONObject("tmp").getDouble("max");
                    Double tmp_min = daily.getJSONObject("tmp").getDouble("min");
                    String tmp = (tmp_max + tmp_min) / 2 + "℃";
                    String wind_sc = daily.getJSONObject("wind").getString("sc");
                    String cond_txt_d = daily.getJSONObject("cond").getString("txt_d");
                    futureWeather.add(new Weather(date, tmp, wind_sc, cond_txt_d));
                }
                return new Weather(siteName, city, todayDate, today_tmp, today_wind_sc, today_cond_txt_d, futureWeather);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}