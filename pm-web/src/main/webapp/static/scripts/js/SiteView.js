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
        url: "/web/weather/getSitesSort",
        type: "post",
        dataType: "json",
        success: function (sites) {
            var html = '';
            for (var i = 0, len = sites.length; i < len; i++) {
                html += '<li><input type="checkbox" name="site" value="' + sites[i] + '"/><img src="<%=basePath%>/picture/' + sites[i] + '.jpg">' + sites[i] + '</li>';
            }
            $('#sortable').html(html);
            getSites();
        }
    });
}
function getSites() {
    $.ajax({
        url: "/web/weather/getSites",
        type: "post",
        dataType: "json",
        success: function (result) {
            var  $sites = $('#sortable input');
            if (result.length > 0) {
                for (var i = 0, len = $sites.length; i < len; i++) {
                    for (var j = 0; j < result.length; j++) {
                        if ($sites.eq(i).val() == result[j]) {
                            $sites.eq(i).attr("checked", true);
                            continue;
                        }
                    }

                }
            }
        }
    })
}
function setSites() {
    setSitesSort();
    var stringSites = '';
    $("[name=site]:checkbox:checked").each(function () {
        stringSites += "," + $(this).val();
    });
//        去掉第一个逗号
    stringSites = stringSites.replace(",","");
    $.ajax({
        url: "/web/weather/setSites",
        data: {"sites": stringSites},
        type: "post",
        async:false,
        dataType: "json"
    });
    alert("设置成功");
    initSites();
}
function setSitesSort() {
    var stringSites = '';
    $("[name=site]:checkbox").each(function () {
        stringSites += "," + $(this).val();
    });
    stringSites = stringSites.replace(",","");
    $.ajax({
        url: "/web/weather/setSitesSort",
        data: {"sites": stringSites},
        type: "post",
        async:false,
        dataType: "json"
    });
}