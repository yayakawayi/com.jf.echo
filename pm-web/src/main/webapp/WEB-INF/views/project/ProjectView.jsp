<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>服务管理</title>
    <%@ include file="../base/base.jsp" %>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>/static/styles/css/ProjectView.css">
    <script type="text/javascript"
            src="<%=basePath%>/static/scripts/js/ProjectView.js"></script>
    <script type="text/javascript">
        var projectView;
        EUI.onReady(function () {
            projectView = new EUI.ProjectView({
                renderTo: "projectView"
            });
        });
    </script>
</head>
<body style='overflow: auto;background: white;'>
<div id="projectView"></div>
</body>
</html>

