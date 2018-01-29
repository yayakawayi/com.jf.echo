package com.jf.weather.sites;

import com.jf.weather.Weather;
import com.jf.weather.weatherUtil.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

public class Seniverse {
    private static String siteName = "seniverse";
    private static String url = "https://api.seniverse.com/v3/weather/daily.json?key=re9qc7evlzzm3xvp&language=zh-Hans&unit=c&start=0&days=5&location=";

    public static Weather getWeather(String city) {
        if (city.contains(",") || city.contains(":")) {
            String[] split = city.split(",");
            if (split.length < 2) {
                split = city.split(":");
            }
            city = split[1] + ":" + split[0];
        }
        /**
         * 只要抓取失败或报错，就会返回空
         */
        try {
            JSONObject jsonObject = JSONObject.fromObject(HttpUtil.getStringData(url, city));
            if (null != jsonObject.getJSONArray("results")) {
                JSONArray result = jsonObject.getJSONArray("results");
                String location = result.getJSONObject(0).getJSONObject("location").getString("name");
                JSONArray future = result.getJSONObject(0).getJSONArray("daily");
                JSONObject todayWeather = future.getJSONObject(0);
                /**
                 * 取今天数据
                 */
                String today_date = todayWeather.getString("date");
                double today_high = todayWeather.getDouble("high");
                double today_low = todayWeather.getDouble("low");
                String today_tmp = (today_high + today_low) / 2 + "℃";
                String today_wind = todayWeather.getString("wind_direction");
                String today_text = todayWeather.getString("text_day");
                /**
                 * 取未来2天的数据
                 */
                ArrayList<Weather> futureWeather = new ArrayList<>();
                for (int i = 1; i < 3; i++) {
                    JSONObject daily = JSONObject.fromObject(future.getJSONObject(i));
                    String date = daily.getString("date");
                    Double tmp_max = daily.getDouble("high");
                    Double tmp_min = daily.getDouble("low");
                    String tmp = (tmp_max + tmp_min) / 2 + "℃";
                    String wind = daily.getString("wind_direction");
                    String text = daily.getString("text_day");
                    futureWeather.add(new Weather(date, tmp, wind, text));
                }
                return new Weather(siteName, location, today_date, today_tmp, today_wind, today_text, futureWeather);
            }
            return null;
        } catch (RuntimeException e) {
            return null;
        }
    }
}
