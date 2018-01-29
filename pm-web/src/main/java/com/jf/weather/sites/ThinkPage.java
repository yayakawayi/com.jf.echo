package com.jf.weather.sites;

import com.jf.weather.weatherUtil.HttpUtil;
import com.jf.weather.Weather;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

public class ThinkPage {
    private static String siteName = "thinkPage";
    private static String url = "https://api.thinkpage.cn/v2/weather/all.json?language=zh-chs&unit=c&aqi=city&key=OD8GU1GW2O&city=";

    public static Weather getWeather(String city) {
        /**
         * 只要抓取失败或报错，就会返回空
         */
        try {
            JSONObject jsonObject = JSONObject.fromObject(HttpUtil.getStringData(url, city));
            if ("OK".equals(jsonObject.getString("status"))) {
                JSONArray weather = jsonObject.getJSONArray("weather");
                String location = weather.getJSONObject(0).getString("city_name");
                JSONArray future = weather.getJSONObject(0).getJSONArray("future");
                JSONObject todayWeather = future.getJSONObject(0);
                /**
                 * 取今天数据
                 */
                String today_date = todayWeather.getString("date");
                double today_high = todayWeather.getDouble("high");
                double today_low = todayWeather.getDouble("low");
                String today_tmp = (today_high + today_low) / 2 + "℃";
                String today_wind = todayWeather.getString("wind");
                String today_text = todayWeather.getString("text");
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
                    String wind = daily.getString("wind");
                    String text = daily.getString("text");
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
