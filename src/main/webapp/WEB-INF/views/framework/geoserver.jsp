<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

    <title>丽水市防震减灾公共服务信息系统二期</title>

    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" type="text/css" href="http://120.55.65.150/wp/jquery-easyui-1.4.5/themes/default/easyui.css">

    <link rel="stylesheet" type="text/css" href="http://hvit.in.server:3000/css/home.css"/>


    <script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
    <link rel="shortcut icon" type="image/x-icon" href="docs/images/favicon.ico"/>

    <link rel="stylesheet" href="//cdn.bootcss.com/leaflet/1.0.1/leaflet.css"/>
    <script src="//cdn.bootcss.com/leaflet/1.0.1/leaflet.js"></script>


    <script>
        <!--兼容windows无console的情况-->
        if (window.console && window.console.log) {
        } else {
            window.console = {};
            window.console.log = function () {
            };
        }
        var vecIndexOf = function (v, elt) {
            var len = v.length;
            ;
            for (var from = 0; from < len; from++) {
                if (v[from] === elt)
                    return from;
            }
            return -1;
        }

        var global = {route: {}};
        var my_test_mg = !true;
        var proxyUrl = function (yls) {
            return window.location.origin + '/proxyapi?yls=' + yls;
        };
        var App = {
            nav: function () {
            },
            isTest: my_test_mg,
            wrapUrl: function (url) {
                return (my_test_mg ? "test-" : "") + url
            },
            WrapIdAttribute: function (id) {
                return (my_test_mg ? "_id" : id)
            },
            tab: null,
            map:null
        };
        var Iconfig;
        var global={};

    </script>

    <style>
        html, body {
            padding: 0;
            margin: 0;
            width: 100%;
            height: 100%;
        }

        #map-wrapper {
            width: 100%;
            height: 500px;
            position: relative;
        }

        #button-wrapper {
            position: absolute;
            bottom: 10px;
            width: 100%;
            background: orange;
            z-index: 999;
        }

        #mapid {
            height: 100%;
        }

        .btnStyle {
            border: 1px solid green;
        }

        #header{
            height:50px;
        }
    </style>


</head>
<body>
<div id="header"> Ikke rutesat: </div>
<div id="map-wrapper">
        <div id="mapid"></div>
        <div id="button-wrapper">
            <input type="button" id="Btn1" value="Btn1" class="btnStyle span3"/>
            <input type="button" id="Btn2" value="Btn2" class="btnStyle span3"/>
            <input type="button" id="Btn3" value="Btn3" class="btnStyle span3"/>
        </div>
    </div>
<script>

    var latlng = null;
    var mymap = L.map('mapid').setView([51.505, -0.09], 13);

    L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw', {
        maxZoom: 18,
        attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
        '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        'Imagery © <a href="http://mapbox.com">Mapbox</a>',
        id: 'mapbox.streets'
    }).addTo(mymap);

    L.marker([51.5, -0.09]).addTo(mymap);

    L.circle([51.508, -0.11], {
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5,
        radius: 500
    }).addTo(mymap);

    L.polygon([
        [51.509, -0.08],
        [51.503, -0.06],
        [51.51, -0.047]
    ]).addTo(mymap);

    //add event

    var popup = L.popup();

    function onMapClick(e) {
        latlng = e.latlng;//给全局变量设值

        popup
                .setLatLng(e.latlng)
                .setContent("You clicked the map at " + e.latlng.toString())
                .openOn(mymap);
    }

    mymap.on('click', onMapClick);


    //按钮事件
    $('#Btn1').bind('click', function () {
        var data = '<wfs:Transaction service="WFS" version="1.0.0" xmlns:wfs="http://www.opengis.net/wfs" xmlns:topp="http://www.openplans.org/topp" xmlns:gml="http://www.opengis.net/gml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.0.0/WFS-transaction.xsd http://www.openplans.org/topp http://localhost:8080/geoserver/wfs/DescribeFeatureType?typename=topp:tasmania_roads"><wfs:Insert><topp:tasmania_roads><topp:the_geom><gml:MultiLineString srsName="http://www.opengis.net/gml/srs/epsg.xml#4326"><gml:lineStringMember><gml:LineString><gml:coordinates decimal="." cs="," ts=" ">494475.71056415,5433016.8189323 494982.70115662,5435041.95096618</gml:coordinates></gml:LineString></gml:lineStringMember></gml:MultiLineString></topp:the_geom><topp:TYPE>alley</topp:TYPE></topp:tasmania_roads></wfs:Insert></wfs:Transaction>';
        $.ajax({
            url: 'auth/proxy?url=http://192.168.1.129:8080/geoserver/wfs',
            type: 'POST',// 必须是POST
            contentType: 'text/html; charset=UTF-8',
            data: data
        });
    });

    //查询
    $('#Btn2').bind('click', function () {
        var data='<wfs:GetFeature service="WFS" version="1.0.0"  outputFormat="GML2"  xmlns:topp="http://www.openplans.org/topp"  xmlns:wfs="http://www.opengis.net/wfs"  xmlns:ogc="http://www.opengis.net/ogc"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xsi:schemaLocation="http://www.opengis.net/wfs                      http://schemas.opengis.net/wfs/1.0.0/WFS-basic.xsd">  <wfs:Query typeName="txmap:PT_LNGY">        </wfs:Query></wfs:GetFeature>';
        $.ajax({
            url: 'auth/proxy?url=http://192.168.1.129:8080/geoserver/wfs',
            type: 'POST',// 必须是POST
            contentType: 'text/html; charset=UTF-8',
            data: data
        });
    });


</script>


</body>
</html>
<script>
    //重要的设置
    HvitFrontFWPrefix = "http://hvit.in.server:3000/";
</script>
<script src="http://hvit.in.server:3000/hvit-front-framework/baseConfig.js"></script>
<script src="http://hvit.in.server:3000/hvit-front-framework/require.js"
        data-main="apps/lishui2qi/app.js?v=1"></script>