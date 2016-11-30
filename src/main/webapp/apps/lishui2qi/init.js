define(['underscore', 'mapviews/initMap'], function (_,initMap) {


    return function () {

        //初始化地图
        initMap.create();


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
        require(['mapviews/MapMenu'], function (MapMenu) {
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
                    var module = 'mapviews/Login';
                    DispatcherPanel.open('text!' + module + '.htm', module, {
                        title: "登录",
                        height: 200,
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
        var flashTime = function () {
            var date = new Date();
            var currentTimeString = date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds();
            $currentTime.text(currentTimeString);
        };
        window.setInterval(flashTime, 1000);


        //历史地震信息
        $('#history-earthquake').bind('click', function () {

        });

        //台站列表
        require(['mapviews/StationQuery'], function (js) {
            js.render();
        });


        //水库列表
        require(['text!mapviews/ShuiKuList-template.htm'], function (htm) {
            var tpl = _.template(htm);
            $.get('data-json/test/datagrid_data1.json', function (resp) {
                $('#shuiku-list').append(tpl({children: resp.rows}));
            })
        });

        //expand panel
        var $hander = $('#earth-panel-hander>div');
        var $epanel = $('#earth-panel');
        $hander.bind('click', function () {
            if ($epanel.is(":hidden")) {
                $epanel.show('slow');
                $hander.removeClass('down');
            } else {
                $epanel.hide('slow');
                $hander.addClass('down');
            }
        });

        //快速菜单
        require(['mapviews/FastMenu'], function (FastMenu) {
            FastMenu.render();
        });
    };
});