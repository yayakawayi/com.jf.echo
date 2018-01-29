<%@page import="com.alibaba.fastjson.JSON" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String hostPath = request.getServerName() + ":" + request.getServerPort()+ path;
    String basePath = request.getScheme() + "://"+hostPath;
    String userInfo = JSON.toJSONString(session
            .getAttribute("_SessionUser"));
    String _FeatureMaps = JSON.toJSONString(request
            .getAttribute("_FeatureMaps"));
%>
<link href="http://isr.kydls.cn:10031/wx-manage-web/static/style/images/title_logo.ico" rel="shortcut icon">
<link rel="stylesheet" type="text/css"
      href="http://120.76.219.146:8888/UI/jqgrid/css/jquery-ui.min.css">
<link rel="stylesheet" type="text/css"
      href="http://120.76.219.146:8888/UI/jqgrid/css/ui.jqgrid.min.css">
<link rel="stylesheet" type="text/css"
      href="http://120.76.219.146:8888/UI/eui.unmin.css">

<script type="text/javascript"
        src="http://120.76.219.146:8888/UI/jquery-1.8.min.js"></script>
<script type="text/javascript"
        src="http://120.76.219.146:8888/UI/eui.unmin.js"></script>
<script type="text/javascript"
        src="http://120.76.219.146:8888/UI/eui_zh-cn.js"></script>
<script type="text/javascript"
        src="http://120.76.219.146:8888/UI/jqgrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript">
    var _ctxPath = "<%=basePath%>";
    var __SessionUser = JSON.parse('{"account":"admin","anonymous":false,"authorityPolicy":"GlobalAdmin","ip":"Unknown","locale":"en_US","loginStatus":"success","loginTime":1512455309300,"sessionId":"8129195C-7C9C-4873-99B3-A24E14AAC244","userId":"25db2bee05b043a5ba9729f1b5bc9030","userInfo":"全局管理员 [ admin ] ","userName":"全局管理员","userType":0}');
    var _FeatureMaps = JSON.parse('<%=_FeatureMaps%>');

    EUI.checkAuth = function (code) {
        if ('GlobalAdmin' === __SessionUser['authorityPolicy']) {
            return true;
        }
        return !!_FeatureMaps[code];
    };

    EUI.onReady(function () {
        $(document).bind("keypress", function (event) {
            if (event.keyCode == 8) {
                if (event.target.tagName.toLowerCase() == "body") {
                    return false;
                }
            }
        });
    });
</script>
