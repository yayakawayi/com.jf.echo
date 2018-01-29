package com.jf.servlet;

import com.jf.util.SystemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CustomDispatcherServlet extends DispatcherServlet {
    private static final Logger logger = LoggerFactory
            .getLogger(CustomDispatcherServlet.class);

    private String SPLIT_FLAG = "|";

    private Marker marker;

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CustomDispatcherServlet() {
        super();
        marker = new Marker() {

            @Override
            public void add(Marker reference) {

            }

            @Override
            public boolean contains(Marker other) {
                return false;
            }

            @Override
            public boolean contains(String name) {
                return false;
            }

            @Override
            public String getName() {
                return "CustomDispatcherServlet";
            }

            @Override
            public boolean hasChildren() {
                return false;
            }

            @Override
            public boolean hasReferences() {
                return false;
            }

            @Override
            public Iterator<Marker> iterator() {
                return null;
            }

            @Override
            public boolean remove(Marker reference) {
                return false;
            }

        };
    }

    protected void doDispatch(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        String url = request.getRequestURL().toString();
        if (url.contains("static")) {
            super.doDispatch(request, response);
            return;
        }

        //打印请求开始日志
        long starttime = System.currentTimeMillis();
        String msg = makeInfoMsg(starttime, request);
        logger.info(marker, msg);
        super.doDispatch(request, response);
        //打印请求结束日志
        long costtime = System.currentTimeMillis() - starttime;
        StringBuilder costSb = new StringBuilder();
        costSb.append(starttime).append(SPLIT_FLAG);
        costSb.append(costtime).append(SPLIT_FLAG);
        logger.info(marker, costSb.toString());
    }

    private String makeInfoMsg(long starttime, HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(starttime).append(SPLIT_FLAG);
        sb.append(request.getRequestURL()).append(SPLIT_FLAG);

        // 获取所有参数
        Map map = new HashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length < 1) {
                continue;
            }
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            } else {
                StringBuffer voSb = new StringBuffer();
                for (int i = 0; i < paramValues.length; i++) {
                    if (i == 0) {
                        voSb.append(paramValues[i]);
                    } else {
                        voSb.append(",").append(paramValues[i]);
                    }
                }
                map.put(paramName, voSb.toString());
            }
        }
        sb.append(map.toString());
        String agent = request.getHeader("User-Agent");
        sb.append(SPLIT_FLAG).append(agent);
        sb.append(SPLIT_FLAG).append("请求地址：").append(SystemUtil.getClientIP(request));
        return sb.toString();
    }

}
