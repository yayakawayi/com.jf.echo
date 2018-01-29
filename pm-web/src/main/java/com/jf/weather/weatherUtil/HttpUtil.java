package com.jf.weather.weatherUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class HttpUtil {
    public static synchronized String getStringData(String postUrl, String location) {
        BufferedReader br = null;
        String data = null;
        try {
            // 发送POST请求
            URL url = new URL(postUrl + URLEncoder.encode(location, "UTF-8"));
            URLConnection conn = url.openConnection();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
        return data;
    }
}
