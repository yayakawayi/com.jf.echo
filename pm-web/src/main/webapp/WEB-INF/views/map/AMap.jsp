<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>高德基本地图展示</title>
    <%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();%>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <link rel="stylesheet" href="<%=basePath%>/static/styles/css/AMap.css">
    <script type="text/javascript" src="http://120.76.219.146:8888/UI/jquery-1.8.min.js"></script>
    <script src="http://webapi.amap.com/maps?v=1.4.2&key=24eedf5aa3941f25dd5e131ac2c308fa"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
</head>
<body>
<div id="container">
    <div id="cityInput"><input type="text" id="location" placeholder="请输入城市名,ip地址,经纬度"/>
        <button onclick="handleLocation($('#location').val())">搜索</button>
    </div>
</div>
<div id="weather"></div>
<div id="mask" class="mask"><div class="maskBox"><img src="<%=basePath%>/static/styles/images/timg.gif" width="100" height="80"/><div class="maskMsg">正在加载中。。。</div></div></div>
</body>
</html>
<script type="text/javascript" src="<%=basePath%>/static/scripts/js/AMap.js"></script>
