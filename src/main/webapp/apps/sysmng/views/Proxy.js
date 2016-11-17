define(['jqueryform', 'backbone'], function (jqueryForm, Backbone) {

    /*
     设置 contentType: 'text/html; charset=UTF-8',
     或者 'application/json; charset=UTF-8'

     基本上可以发送 Request Payload 请求

     */
    var showResponse = function (content) {
        require([cj.getModuleJs('widget/DispatcherPanel')], function (DispatcherPanel) {
            var module = 'views/Blank';
            DispatcherPanel.open('text!' + module + '.htm', module, {
                title: "我在这里",
                ptype: DispatcherPanel.PANELLAYER,
                content: content
            });
        });
    }
    return {
        render: function (local, cb) {
            var $demo1 = local.find('div[opt=demo1]');
            var bar = $demo1.find('.bar');
            var percent = $demo1.find('.percent');
            var status = $demo1.find('div[opt=status]');

            $('div[opt=demo1] form').ajaxForm({

                success: function () {

                },
                complete: function (xhr) {
                    //status.html(xhr.responseText);
                    /*var myJson = $.parseJSON(xhr.responseText);
                     if(myJson.length==1) {
                     status.html('<a target= _blank href=' + myJson[0].url + ' >'+myJson[0].url+'</a>');
                     }*/

                }
            });

            //二
            local.find('div[opt=demo2] a').bind('click', function () {
                var data = {username: 'admin', password: 'Nb1zA0GzyRIE/XNADpAr9Q=='}
                $.ajax({
                    url: 'auth/proxy?url=http://localhost:8070/loginaction',
                    type: 'POST',// 必须是POST
                    contentType: 'application/json; charset=UTF-8',
                    data: JSON.stringify(data)// 数据 必须是字符串
                });
            });

            local.find('div[opt=demo4] a').bind('click', function () {
                var data = {username: 'admin', password: 'Nb1zA0GzyRIE/XNADpAr9Q=='}
                $.ajax({
                    url: 'auth/proxy?url=http://localhost:8070/loginaction',
                    type: 'POST',// 必须是POST
                    data: data
                });
            });


            local.find('div[opt=demo3] a').bind('click', function () {
                $.ajax({
                    url: 'auth/proxy?url=http://localhost:8070/loginaction',
                    type: 'POST',// 必须是POST
                    contentType: 'text/html; charset=UTF-8',
                    data: '<span>数据必须是字符串</span>'
                });
            });


            local.find('div[opt=demo5] a').bind('click', function () {
                $.ajax({
                    url: 'auth/proxy?url=http://192.168.1.129:8080/geoserver/wfs',
                    type: 'POST',// 必须是POST
                    contentType: 'text/html; charset=UTF-8',
                    data: '<wfs:GetFeature service="WFS" version="1.0.0"  outputFormat="GML2"  xmlns:topp="http://www.openplans.org/topp"  xmlns:wfs="http://www.opengis.net/wfs"  xmlns:ogc="http://www.opengis.net/ogc"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xsi:schemaLocation="http://www.opengis.net/wfs                      http://schemas.opengis.net/wfs/1.0.0/WFS-basic.xsd">  <wfs:Query typeName="topp:states">    <ogc:Filter>       <ogc:FeatureId fid="states.3"/>    </ogc:Filter>    </wfs:Query></wfs:GetFeature>'
                });

            });

            local.find('div[opt=demo6] a').bind('click', function () {
                $.ajax({
                    url: 'auth/proxy?url=http://192.168.1.129:8080/geoserver/txmap/wfs',
                    type: 'POST',// 必须是POST
                    contentType: 'text/html; charset=UTF-8',
                    data: '<wfs:GetFeature service="WFS" version="1.1.0"  outputFormat="JSON" maxFeatures="200" xmlns:wfs="http://www.opengis.net/wfs" xmlns:ogc="http://www.opengis.net/ogc" xmlns:gml="http://www.opengis.net/gml"><wfs:Query typeName="hymap:PT_BZD"><Filter><BBOX><PropertyName>the_geom</PropertyName><gml:Envelope srsName="http://www.opengis.net/gml/srs/epsg.xml#4326"><gml:lowerCorner>120.62714 30.67217</gml:lowerCorner><gml:upperCorner>121.42502 30.32666</gml:upperCorner></gml:Envelope></BBOX></Filter></wfs:Query></wfs:GetFeature>'
                });

            });

        }
    };
});