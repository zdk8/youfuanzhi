define(['underscore','leaflet-plugins/TuLi'], function (_,TuLi) {


    return function () {

        var latlng = null;
        var myMap = L.map('mapid', {
            zoomControl: false
        }).setView([75.58366, 119.91749], 13);

        var zoomControl=L.control.zoom({
            position: 'bottomleft'
        }).addTo(myMap);
        //myMap.addControl(zoomControl);

        L.control.tuli().addTo(myMap);

        var mapurl1='http://t0.tianditu.com/vec_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=vec&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}';
        //mapurl1='https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw';
        L.tileLayer(mapurl1, {
            maxZoom: 18,
            attribution: '地图数据 &copy; <a href="#">丽水</a> 贡献, ' +
            '<a href="#">地震局</a>, ' +
            'admin © <a href="#">lishui</a>',
            id: 'mapbox.streets'
        }).addTo(myMap);

        var mapurl2='http://t0.tianditu.com/cva_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cva&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}';
        L.tileLayer(mapurl2, {maxZoom: 18}).addTo(myMap);


        L.marker([75.58366, 119.91749]).addTo(myMap);

        L.circle([75.58366, 119.91749], {
            color: 'red',
            fillColor: '#f03',
            fillOpacity: 0.5,
            radius: 500
        })//.addTo(myMap);

        L.polygon([
            [75.509, 119.98],
            [75.503, 119.96],
            [75.51, 119.947]
        ]).addTo(myMap);

        //add event

        var popup = L.popup();

        function onMapClick(e) {
            latlng = e.latlng;//给全局变量设值

            popup
                .setLatLng(e.latlng)
                .setContent("You clicked the map at " + e.latlng.toString())
                .openOn(myMap);
        }

        myMap.on('click', onMapClick);


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

        $moreMenu = $('#header-more-menu');
        $moreMenu.find('a').bind('click', function () {
            console.log(this);
        });

        //清除三角形的弹出界面
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

        //点击当前用户名，如果未登录，则进行弹出登录界面
        $('#current-user-name').bind('click', function () {
            if ($(this).attr('isLogined') == 'false') {
                require([cj.getModuleJs('widget/DispatcherPanel')], function (DispatcherPanel) {
                    var module = 'views/Login';
                    DispatcherPanel.open('text!' + module + '.htm', module, {
                        title: "登录",
                        height:200,
                        ptype: DispatcherPanel.PANELLAYER
                    });
                });
            } else {
                alert('已登录');
            }
        });

        //注销
        $('#logout').bind('click', function () {
            $.get('logout', function () {
                document.location.href = 'framework_geoserver';
            })
        });

        var $currentTime = $('#current-time');
        var flashTime=function () {
            var date=new Date();
            var currentTimeString=date.getFullYear()+'-'+date.getMonth()+'-'+date.getDate()+' '+date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();
            $currentTime.text(currentTimeString);
        };
        window.setInterval(flashTime,1000);


        //历史地震信息
        $('#history-earthquake').bind('click',function () {

        });

        //台站列表
        require(['views/StationQuery'],function (js) {
            js.render();
        });

        
        
        //水库列表
        require(['text!views/ShuiKuList-template.htm'],function (htm) {
            var tpl = _.template(htm);
            $.get('data-json/test/datagrid_data1.json',function (resp) {
                $('#shuiku-list').append(tpl({children: resp.rows}));
            })
        });

        //expand panel
        var $hander=$('#earth-panel-hander>div');
        var $epanel = $('#earth-panel');
        $hander.bind('click',function () {
           if($epanel.is(":hidden")){
               $epanel.show('slow');
               $hander.removeClass('down');
           }else{
               $epanel.hide('slow');
               $hander.addClass('down');
           }
        });

        //快速菜单
        require(['text!views/FastMenu-template.htm'],function (htm) {
            var menuList=[{name:'A'},{name:'A'},{name:'A'},{name:'A'}];
            var tpl = _.template(htm);
            $('#lishui-map-button').append(tpl({children: menuList}));
            $('#lishui-map-button a.text').each(function () {
               $(this).bind('click',function () {
                   $(this).siblings('.text').removeClass('selected');
                   $(this).addClass('selected');
               })
            });
        });
    }
});