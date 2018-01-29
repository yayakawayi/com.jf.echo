package com.jf.weather;
import java.util.List;


public class Weather {
    /**
     * 数据来源网站
     */
    private String site;
    /**
     * 位置，只取城市名
     */
    private String location;
    /**
     * 日期
     */
    private String date;
    /**
     * 温度
     */
    private String tmp;
    /**
     * 风向或风力
     */
    private String wind;
    /**
     * 天气
     */
    private String cond;
    /**
     * 未来几天天气
     */
    private List<Weather> future;


    public Weather(String site, String location, String date, String tmp, String wind, String cond, List<Weather> future) {
        this.site = site;
        this.location = location;
        this.date = date;
        this.tmp = tmp;
        this.wind = wind;
        this.cond = cond;
        this.future = future;
    }

    public Weather(String date, String tmp, String wind, String cond) {
        this.date = date;
        this.tmp = tmp;
        this.wind = wind;
        this.cond = cond;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "Site='" + site + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", tmp='" + tmp + '\'' +
                ", wind='" + wind + '\'' +
                ", cond='" + cond + '\'' +
                ", future=" + future +
                '}';
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public List<Weather> getFuture() {
        return future;
    }

    public void setFuture(List<Weather> future) {
        this.future = future;
    }

}
