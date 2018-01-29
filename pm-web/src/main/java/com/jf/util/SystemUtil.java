package com.jf.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/7/9 22:28      陈飞(fly)                    新建
 * <br>
 * *************************************************************************************************<br>
 */

public class SystemUtil {
    private static final Logger logger = LoggerFactory.getLogger(SystemUtil.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

    /**
     * 微信获取OpenId地址
     */
    private static final String getWxInfoUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取正式ip
     *
     * @param request
     * @return
     */
    public static String getClientIP(HttpServletRequest request) {
        String fromSource = "X-Real-IP";
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
            fromSource = "X-Forwarded-For";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            fromSource = "Proxy-Client-IP";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            fromSource = "WL-Proxy-Client-IP";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteHost();
            fromSource = "request.getRemoteHost";
        }
        logger.error(fromSource + "--" + ip);
        return ip;
    }

    /**
     * 访问的HTTP请求的URL中，去掉contextRoot。
     * <p>
     * 首先重HttpServletRequest对象中获取ContentRoot，然后再从该对象中获取http请求的URI，
     * 最后从获取的URI字符串中去掉ContextRoot后，以字符串类型放回。
     * @param request HttpServletRequest对象
     * @return String 返回的字符串中去掉了ContentRoot。
     */
    public static String removeContextRoot(HttpServletRequest request) {
        String contextRoot = request.getContextPath();
        String requestPath = request.getRequestURI();
        if (requestPath.contains(contextRoot)) {
            requestPath = requestPath.substring(contextRoot.length());
        }

        return requestPath;
    }

    /**
     * 密码加密
     *
     * @param originPass
     * @return
     */
    public static String encryPass(String originPass, String salt) {
        String newPassword = new SimpleHash(
                "md5",
                originPass,
                ByteSource.Util.bytes(salt),
                2).toHex();
        return newPassword;
    }

    /**
     * 从request中获取微信openid
     *
     * @param request
     * @return
     */
    public static String getOpenId(HttpServletRequest request) {
        return request.getHeader("openid");
    }

    /**
     * 判断是否是手机访问
     *
     * @param request
     * @return
     */
    public static boolean isMobileDevice(HttpServletRequest request) {
        String requestHeader = request.getHeader("user-agent");
        String[] deviceArray = new String[]{"android", "mac os", "windows phone", "iphone"};
        if (requestHeader == null)
            return false;
        requestHeader = requestHeader.toLowerCase();
        for (int i = 0; i < deviceArray.length; i++) {
            if (requestHeader.indexOf(deviceArray[i]) != -1) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println(encryPass("fll123456", "b14197c7-fb7e-4966-9d71-d5af93e8861a"));
    }
}
