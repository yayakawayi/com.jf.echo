package com.jf.controller;

import com.jf.controller.base.BaseController;
import com.jf.json.OperateStatus;
import com.jf.weather.Weather;
import com.jf.weather.weatherUtil.WeatherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/weather")
public class WeatherController extends BaseController{
    /**
     * 得到天气数据
     */
    @ResponseBody
    @RequestMapping("/getWeather")
    public Weather getWeather(String location) {
        Weather weather = WeatherUtil.getWeather(location, this.siteService);
        return weather;
    }
    /**
     * 显示地图
     */
    @RequestMapping("/showMap")
    public String showMap() {
        return "map/AMap";
    }
    /**
     * 得到location的对应经纬度
     */
    @ResponseBody
    @RequestMapping("/getLngLat")
    public OperateStatus getLngLat(String location) {
        OperateStatus status = OperateStatus.defaultSuccess();
        Map<String, String> lngLat = WeatherUtil.getLngLat(location);
        status.setData(lngLat);
        return status;
    }
}
