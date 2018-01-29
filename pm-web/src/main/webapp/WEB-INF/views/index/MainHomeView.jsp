<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../base/base.jsp" %>
    <script type="text/javascript"
            src="http://isr.kydls.cn:10031/wx-manage-web/static/UI/jquery.cookie.js"></script>
    <title>后台管理</title>
    <link rel="stylesheet" type="text/css"
          href="http://isr.kydls.cn:10031/wx-manage-web/static/UI/ext/scrollbar/jquery.mCustomScrollbar.css">
    <link rel="stylesheet" type="text/css"
          href="http://isr.kydls.cn:10031/wx-manage-web/static/style/css/MainHomeView.unmin.css?v=4">
    <script type="text/javascript"
            src="<%=basePath%>/static/scripts/index/MainHomeView.unmin.js?v=11"></script>
    <script type="text/javascript"
            src="http://isr.kydls.cn:10031/wx-manage-web/static/UI/ext/jquery.mousewheel.min.js"></script>
    <script type="text/javascript">
        var homeView;
        EUI.onReady(function () {
            homeView = new EUI.MainHomeView({
                renderTo: "mainHomeView"
            });
        });
    </script>
</head>
<body style='min-width: 1260px;/*overflow: auto;*/'>
<div id="mainHomeView"></div>
</body>
</html>