package com.jf.weather.sites;

import com.jf.weather.weatherUtil.HttpUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BaiDu {

    private static String url = "http://api.map.baidu.com/location/ip?ak=YmwsItNzGuPKEFihNqWM8PbyzHCrGyFO&coor=bd09ll&ip=";

    /**
     * 得到对应location经纬度
     */
    public static Map<String, String> getLngLat(String ip) {
        HashMap<String, String> map = new HashMap<>();
        try {
            JSONObject jsonObject = JSONObject.fromObject(HttpUtil.getStringData(url, ip));
            JSONObject point = jsonObject.getJSONObject("content").getJSONObject("point");
            String lng = point.getString("x");
            String lat = point.getString("y");
            map.put("lng", lng);
            map.put("lat", lat);
        } catch (RuntimeException e) {
            return null;
        }
        return map;
    }
}
