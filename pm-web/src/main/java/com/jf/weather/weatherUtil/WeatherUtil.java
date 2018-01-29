package com.jf.weather.weatherUtil;


import com.jf.SiteService;
import com.jf.constant.EnumConstant;
import com.jf.entity.Site;
import com.jf.search.Search;
import com.jf.search.SearchFilter;
import com.jf.search.SearchOrder;
import com.jf.weather.Weather;
import com.jf.weather.sites.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherUtil {

    /**
     * 多个线程同时为到网站上抓取天气并为全局weather赋值，
     * while循环等待，只要有一个先成功，weather就不等于空，
     * 就会结束while循环，返回weather，
     * 如果都失败，那么通过break退出循环
     */

    public static Weather getWeather(String location, SiteService siteService) {
        Search search = new Search();
        SearchFilter searchFilter = new SearchFilter("status", EnumConstant.ServerStatus.ENABLE, SearchFilter.Operator.EQ);
        search.addSortOrder(new SearchOrder("sort"));
        search.addFilter(searchFilter);
        List<Site> allSites = siteService.findByFilters(search);
        int len = allSites.size();
        if (len==0){
            return null;
        }
        String[] sites = new String[len];
        for (int i = 0; i < len; i++) {
            sites[i] = allSites.get(i).getName();
        }
        ExecutorService es = Executors.newFixedThreadPool(2);
        final Weather[] weather = {null};
        es.submit(new Runnable() {
            @Override
            public void run() {
                weather[0] = chooseSite(sites[0], location);
            }
        });
        es.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < sites.length; i++) {
                    weather[0] = chooseSite(sites[i], location);
                    if (weather[0] != null) {
                        break;
                    }
                }
            }
        });
        /*
         * 试图关闭,当所有任务都完成了会关闭
         */
        es.shutdown();
        while (weather[0] == null) {
            if (es.isTerminated()) {//关闭状态
                break;
            }
        }
        return weather[0];
    }

    /**
     * 判断使用哪个网站
     */
    public static Weather chooseSite(String site, String location) {
        Weather weather = null;
        switch (site) {
            case "heWeatherS6":
                weather = HeWeather6.getWeather(location);
                break;
            case "heWeatherV5":
                weather = HeWeather5.getWeather(location);
                break;
            case "thinkPage":
                weather = ThinkPage.getWeather(location);
                break;
            case "seniverse":
                weather = Seniverse.getWeather(location);
                break;
        }
        return weather;
    }

    public static Map<String, String> getLngLat(String location) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        final Map<String, String>[] map = new Map[]{null};
        es.submit(new Runnable() {
            @Override
            public void run() {
                map[0] = BaiDu.getLngLat(location);
            }
        });
        es.submit(new Runnable() {
            @Override
            public void run() {
                map[0] = HeWeather6.getLngLat(location);
            }
        });
        es.shutdown();
        while (map[0] == null) {
            if (es.isTerminated()) {//关闭状态
                break;
            }
        }
        return map[0];
    }

    /**
     * main测试
     */
    public static void main(String[] args) {
        long start = System.nanoTime();
        Map<String, String> map = WeatherUtil.getLngLat("36,104");
        System.out.println(System.nanoTime() - start);
        System.out.println(map);
    }
}
