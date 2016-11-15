define(['underscore'], function (_) {


    return function () {

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
            var data = '<wfs:GetFeature service="WFS" version="1.0.0"  outputFormat="GML2"  xmlns:topp="http://www.openplans.org/topp"  xmlns:wfs="http://www.opengis.net/wfs"  xmlns:ogc="http://www.opengis.net/ogc"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xsi:schemaLocation="http://www.opengis.net/wfs                      http://schemas.opengis.net/wfs/1.0.0/WFS-basic.xsd">  <wfs:Query typeName="txmap:PT_LNGY">        </wfs:Query></wfs:GetFeature>';
            $.ajax({
                url: 'auth/proxy?url=http://192.168.1.129:8080/geoserver/wfs',
                type: 'POST',// 必须是POST
                contentType: 'text/html; charset=UTF-8',
                data: data
            });
        });


        //man layout ; set resize events
        (function () {
            var refresh = function () {
                var width = $(window).width();
                var height = $(window).height();
                var headerHeight = 50;
                $('#header').width(width).height(headerHeight);
                $('#map-wrapper').width(width).height(height - headerHeight);
            }

            //resize time 500s
            $(window).resize(_.throttle(refresh, 500));
            $(window).trigger('resize');
            var i = 0;
            for (; i < 10; i++) {
                refresh();
            }


        })();


        //地图与图层菜单
        require(['views/MapMenu'], function (MapMenu) {
            MapMenu.render($('#map-menu'));
        });

        var isShow = false;
        //点击三角向下箭头
        $('#header-triangle-down').click(function () {
            if ($('#header-more-menu').is(":hidden")) {
                $('#header-more-menu').show();
                isShow = true;
            } else {
                $('#header-more-menu').hide();
                isShow = false;
            }
        });

        $moreMenu=$('#header-more-menu');
        $moreMenu.find('a').bind('click',function () {
            console.log(this);
        });

        $(window).bind('click', function (e) {

            if (isShow) {
                if ($(e.target).attr('id') == "header-triangle-down") {
                    return;
                } else {
                    $('#header-more-menu').hide();
                    isShow = false;
                }

            }
        });


        $('#current-user-name').bind('click',function () {
           if($(this).attr('isLogined')=='false'){
               require([cj.getModuleJs('widget/DispatcherPanel')], function (DispatcherPanel) {
                var module = 'views/Login';
                DispatcherPanel.open('text!' + module + '.htm', module, {
                    title: "登录",
                    ptype: DispatcherPanel.PANELLAYER
                });
            });
           }else {
               alert('已登录');
           }

        });
    }
});