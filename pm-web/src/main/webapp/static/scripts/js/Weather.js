
$(function () {
    getWeather();
});

function getWeather(city) {
    if(city==""||city==null){city="绵阳";}
    $.ajax({
        url: "/web/weather/getWeather",
        data:{"location":city},
        type: "post",
        dataType: "json",
        success: function (weather) {
            console.log(weather);
            var html='',future=weather.future;
            html+='<div>当前城市:'+weather.location+',当前使用网站:'+weather.site+'</div>';
            html+='<div><div>今天:'+weather.date+'</div><div>'+weather.tmp+'</div>' +
                '<div>'+weather.wind+'</div><div>'+weather.cond+'</div> </div> <div>';
            if(future!=null&&future.length>0){
                for (var i=0;i<future.length;i++){
                    html+='<div>日期:'+future[i].date+'</div><div>'+future[i].tmp+'℃</div>' +
                        '<div>'+future[i].wind+'</div><div>'+future[i].cond+'</div></div><div>';
                }
            }
            $("#weather").html(html);
        }
    })

}