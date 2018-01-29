<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>视图管理</title>
    <%@ include file="../base/base.jsp" %>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/static/styles/css/WebView.css">
    <script type="text/javascript"
            src="<%=basePath%>/static/scripts/js/WebView.js"></script>
    <script type="text/javascript">
        var webView;
        EUI.onReady(function () {
            webView = new EUI.WebView({
                renderTo: "webView"
            });
        });
    </script>
</head>
<body style='overflow: auto;background: white;'>
<div id="webView"></div>
</body>
</html>
