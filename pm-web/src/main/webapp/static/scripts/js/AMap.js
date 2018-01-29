//新建地图对象
var map = new AMap.Map('container', {
    resizeEnable: true,
    zoom: 8,
    mapStyle: 'amap://styles/d6bf8c1d69cea9f5c696185ad4ac4c86'
});
// 添加Google图层
var googleLayer = new AMap.TileLayer({
    // 图块取图地址
    tileUrl: 'http://mt{1,2,3,0}.google.cn/vt/lyrs=m@142&hl=zh-CN&gl=cn&x=[x]&y=[y]&z=[z]&s=Galil',
    zIndex: 100
});
//精确定位
map.plugin('AMap.Geolocation', function () {
    geolocation = new AMap.Geolocation({
        enableHighAccuracy: true,//是否使用高精度定位，默认:true
        timeout: 10000,          //超过10秒后停止定位，默认：无穷大
        maximumAge: 0,           //定位结果缓存0毫秒，默认：0
        convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
        showButton: true,        //显示定位按钮，默认：true
        buttonPosition: 'LB',    //定位按钮停靠位置，默认：'LB'，左下角
        buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
        showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
        showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
        panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
        zoomToAccuracy: true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
    });
    map.addControl(geolocation);
    geolocation.getCurrentPosition();
    AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
    AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
});
//点标记
var marker = new AMap.Marker({
    icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
    position: map.getCenter()
});
marker.setMap(map);
//地图点击事件
map.on('click', function (e) {
    map.setCenter(e.lnglat);
    showMask();
    getWeather(e.lnglat.getLng() + "," + e.lnglat.getLat());
    marker.setPosition(e.lnglat);
});
//处理用户输入，先去掉前后空格，然后判断如果输入内容含有中文就去掉中间空格，
function handleLocation(location) {
    showMask();
    location = location.trim();
    if (/[^\x00-\xff]/.test(location)) {
        location = location.replace(/\s/g, "")
    }
    judgeLocation(location);
}
//通过输入的location得到坐标，地图跳转到当前坐标位置，通过map.getCity得到city，如果
// city为空(一般国外)则添加Google图层，并采用后台getWeather，否则(一般国内)就用高德地图并采用高德地图getWeather
function judgeLocation(location) {
    $.ajax({
        url: "/web/weather/getLngLat",
        data: {"location": location},
        type: "post",
        dataType: "json",
        success: function (result) {
            alert(result);
            try {
                //必须先设置中心点才能getCity
                if (location.indexOf(",") == -1) {
                    var lon = result.lng, lat = result.lat;
                    map.setCenter(new AMap.LngLat(lon, lat));
                }
                    map.getCity(function (result) {
                    if (!result.city) {
                        googleLayer.setMap(map);
                    } else {
                        googleLayer.setMap();
                    }
                    getWeather(location);
                });
            } catch (e) {
                hideMask();
                alert("找不到数据");
            }
        }
    });
}
//从后台获取天气数据用于展示，可传入的location有坐标，城市名（中英文名），经纬度，ip
function getWeather(location) {
    $.ajax({
        url: "/web/weather/getWeather",
        data: {"location": location},
        type: "post",
        dataType: "json",
        success: function (weather) {
               console.log(weather);
            try {
                var html = '';
                html += '<div>' + weather.location + ',' + weather.site + '</div>';
                html += '<div><div>' + weather.date + '</div><div>' + weather.tmp + '</div>' +
                    '<div>' + weather.wind + '</div><div>' + weather.cond + '</div> </div> ' +
                    '<div class="cancel" onclick="$(\'#weather\').hide()">×</div>';
                 hideMask();
                $("#weather").html(html);
                $("#weather").css({
                    top: "50%",
                    right: "50%"
                }).show();

            } catch (e) {
                alert("没有找到数据");
            }
        }
    });
}
//打开遮罩
function showMask() {
    $("#mask").css("height", $(document).height());
    $("#mask").css("width", $(document).width());
    $("#mask").show();
}
//关闭遮罩
function hideMask() {
    $("#mask").hide();
}