<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>东阳市民政地图</title>
    <link rel="stylesheet" type="text/css" href="http://192.168.3.102:8080/dymap-java/mapjs/css/basic.css"/>
    <link rel="stylesheet" type="text/css" href="http://192.168.3.102:8080/dymap-java/mapjs/css/style.css"/>

    <script>
        if (window.console && window.console.log) {
        } else {
            window.console = {};
            window.console.log = function () {
            };
        }

        var doRenderLeftScroll;
        var global = {route: {}, istasksimple2: false, istasksimple2params: ''};
        //var self=null;

    </script>


    <script src="http://localhost:18080/team/js/libs/jquery/3.1.1/jquery.min.js" type="text/javascript"></script>
    <link href="http://192.168.3.102:8080/dymap-java/css/leaflet.css" rel="stylesheet" type="text/css"/>

    <script src="http://192.168.3.102:8080/dymap-java/js/leaflet.js" type="text/javascript"></script>
    <script src="http://192.168.3.102:8080/dymap-java/mapjs/views/map/leaflet.hvittianditutilelayer.js"
            type="text/javascript"></script>
    <script src="http://192.168.3.102:8080/dymap-java/mapjs/leaflet/styledLayerControl/styledLayerControl.js"></script>

</head>
<body>
<div id="map"></div>


<script type="text/javascript" src="http://192.168.3.102:8080/dymap-java/js/jquery.json-2.4.js"></script>


<script src="http://192.168.3.102:8080/dymap-java/mapjs/underscore.js"></script>
<script src="http://192.168.3.102:8080/dymap-java/mapjs/backbone.js"></script>

<script type="text/javascript" src="http://192.168.3.102:8080/dymap-java/mapjs/require.js"></script>

<script>
    $(function () {

        var wms_layers = [];
        var display_layers = [];
        var proxy = "auth/proxy?url="; //wfs wms getfeature proxy

        flag = false;
        function makeDisplayLayer(text, defualtlayers, layer) {
            for (var i = 0; i < defualtlayers.length; i++) {

                if (defualtlayers[i].value === text) {
                    display_layers.push(layer);
                    return true;
                }
            }
            return false;
        }

        var myresult = {};

        $.ajax({
            type: 'get',
            async: false,
            dataType: 'json',
            url: 'apps/lishui2qi/data/mock-getfuncbyrole.json',
            success: function (resbase) {
                var baseMaps = {};
                var overlayMaps = {};
                var defaultLayers = resbase['默认加载图层'];
                var plugins = resbase['地图插件'];
                for (var i = 0; i < resbase['底图'].length; i++) {
                    var layertype = $.evalJSON(resbase['底图'][i].imgcss);
                    var layer_group = new L.layerGroup();
                    var mini_group = new L.layerGroup();

                    if (layertype.type === 'tile') {
                        for (var j = 0; j < layertype.resources.length; j++) {
                            var layer = L.tileLayer.hvittianditufunctional(layertype.resources[j]);
                            layer_group.addLayer(layer)
                        }
                        layer_group.resources = layertype.resources;
                        baseMaps[resbase['底图'][i].text] = layer_group;

                        var isDisplay = makeDisplayLayer(resbase['底图'][i].text, defaultLayers, layer_group);
                        if (isDisplay) {
                            for (var n = 0; n < layertype.resources.length; n++) {
                                var mini_Layer = L.tileLayer.hvittianditufunctional(layertype.resources[n]);
                                mini_group.addLayer(mini_Layer)
                            }
                            mini_group.resources = layertype.resources;
                            miniLayer = mini_group;
                        }
                    }
                }
                var oovers = {};
                for (var i = 0; i < resbase['覆盖图'].length; i++) {
                    var layertype = $.evalJSON(resbase['覆盖图'][i].imgcss);

                    if (layertype.type === 'tile') {

                    }
                    else if (layertype.type === 'wms') {
                        var wms_layer = L.tileLayer.wms(resbase['覆盖图'][i].value + "?service=wms", {
                            layers: layertype.layers,
                            format: 'image/png',
                            transparent: true,
                            crs: eval(layertype.crs),
                            noWrap: true
                        });
                        overlayMaps[resbase['覆盖图'][i].text] = wms_layer;
                        oovers[resbase['覆盖图'][i].text] = wms_layer;
                        makeDisplayLayer(resbase['覆盖图'][i].text, defaultLayers, wms_layer);
                        wms_layers.push(
                                {
                                    text: resbase['覆盖图'][i].text,
                                    value: resbase['覆盖图'][i].text,
                                    url: resbase['覆盖图'][i].value,
                                    layers: layertype.layers,
                                    searchField: layertype.searchField,
                                    propertyName: layertype.propertyName,
                                    shape: layertype.shape,
                                    zoom: layertype.zoom
                                });
                    }
                    flag = true;
                }


                for (var i = 0; i < plugins.length; i++) {
                    console.log(plugins[i].text)
                    if (plugins[i].text == "小地图") {
                        console.log(plugins.length)
                        hasminimap = true;
                    }
                    else if (plugins[i].text == "地图全屏") {
                        hasfullmap = false;
                    }
                }
                var options = {
                    container_width: "140px",
                    container_maxHeight: "500px",
                    group_maxHeight: "160px",
                    exclusive: false
                };
                var basemaps = [
                    {
                        groupName: "底图",
                        expanded: true,
                        layers: baseMaps

                    }
                ];
                var overlayers = [
                    {

                        groupName: "专题图",
                        expanded: true,
                        layers: oovers
                    }
                ];

                var layersControl = L.Control.styledLayerControl(basemaps, overlayers, options);


                if (display_layers.length === 0)alert("无地图资源");
                var map = new L.Map('map', {
                    center: [29.24281, 120.32712],
                    zoom: 10,
                    zoomControl: false,
                    crs: L.CRS.EPSG4326,
                    layers: display_layers
                });

                myMap = map;

                map.addControl(layersControl);


                // start add mark
                var markerIcon = L.icon({
                    popupAnchor: [11, 3],
                    iconUrl: 'images/markerIcon/六石街道.png'
                });

                var addPointToMap = function (rows) {
                    var mygroup = [];
                    _.each(rows, function (element, index, list) {
                        var marker = L.marker([element.pointlat, element.pointlon], {icon: markerIcon}).addTo(myMap);
                        marker.bindPopup('good')
                                .on('click', function () {
                                    alert(JSON.stringify(element));
                                });
                        if (index == rows.length - 1) {
                            marker.openPopup();
                        }

                    });

                    var ds = L.layerGroup(mygroup);
                    myMap.addLayer(ds);
                };

                //apps/lishui2qi/data/pointinfo.json
                $.get('getpoints', function (resp) {
                    addPointToMap(resp);
                });


            }
        });
        return myresult;

    });
</script>
</body>
</html>