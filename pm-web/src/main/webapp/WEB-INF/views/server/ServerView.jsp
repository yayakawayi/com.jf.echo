<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>服务管理</title>
    <%@ include file="../base/base.jsp" %>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/static/styles/css/ServerView.css">
    <script type="text/javascript"
            src="<%=basePath%>/static/scripts/js/ServerView.js"></script>
    <script type="text/javascript">
        var serverView;
        EUI.onReady(function () {
            serverView = new EUI.ServerView({
                renderTo: "serverView"
            });
        });
    </script>
</head>
<body style='overflow: auto;background: white;'>
<div id="serverView"></div>
</body>
</html>

