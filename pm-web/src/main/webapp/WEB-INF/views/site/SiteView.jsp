<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%@ include file="../base/base.jsp" %>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>天气抓取网站管理</title>
    <link href="<%=basePath%>/static/styles/css/SiteView.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <style>
        #sortable {
            list-style-type: none;
            margin: 0;
            padding: 0;
            width: 60%;
        }

        #sortable > li img {
            width: 20px;
            height: 20px;
        }

        .mask {
            position: absolute;
            top: 0px;
            filter: alpha(opacity=60);
            background-color: #777;
            z-index: 1002;
            left: 0px;
            opacity: 0.5;
            -moz-opacity: 0.5;
            display: none;
            font-size: 60px;
        }
    </style>
</head>
<body>
<button onclick="setSites()">确认</button>
<ul id="sortable"></ul>
<div id="mask" class="mask">正在保存，请稍候...</div>
</body>
</html>
<script>
    $("#sortable").sortable();
    $(function () {
        $('#sortable input').mouseenter(function () {
            if (this.checked) {
                $(this).attr("title", "禁用");
            } else {
                $(this).attr("title", "启用");
            }
        });
        initSites();
    });

    function initSites() {
        $.ajax({
            url: "/web/site/list",
            type: "post",
            dataType: "json",
            success: function (status) {
                var sites = status.data.rows;
                var html = '';
                for (var i = 0, len = sites.length; i < len; i++) {
                    html += '<li><input type="checkbox" name="Site" value="' + sites[i].name + '"/><img src="<%=basePath%>/static/styles/images/' + sites[i].name + '.jpg">' + sites[i].name + '</li>';
                }
                $('#sortable').html(html);
                var $sites = $('#sortable input');
                for (var i = 0, len = $sites.length; i < len; i++) {
                    $sites.eq(i).attr("checked", sites[i].status == "ENABLE");
                }
            }
        });
    }

    function setSites() {
        showMask();
        var siteSorts = [], siteStatus = [], i = 0;
        $("[name=Site]:checkbox").each(function () {
            siteSorts[i] = $(this).val();
            if (this.checked) {
                siteStatus[i++] = "ENABLE"
            } else {
                siteStatus[i++] = "DISABLE";
            }
        });
        $.ajax({
            url: "/web/site/setSites",
            data: {"siteSorts": siteSorts.join(","), "siteStatus": siteStatus.join(",")},
            type: "post",
            dataType: "json",
            success: function (status) {
                console.log(status.msg);
                hideMask();
                initSites();
            }
        });
    }

    function showMask() {
        $("#mask").css("height", $(document).height());
        $("#mask").css("width", $(document).width());
        $("#mask").show();
    }

    function hideMask() {
        $("#mask").hide();
    }
</script>

