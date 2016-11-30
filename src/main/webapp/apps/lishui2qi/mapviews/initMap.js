define([
    'leaflet-plugins/jquery.json-2.4',
    'leaflet-plugins/TuLi',
    'leaflet-plugins/leaflet.hvittianditutilelayer',
    'leaflet-plugins/styledLayerControl'
], function () {
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

    return {
        create: function (option) {
            $.get('apps/lishui2qi/data/mock-getfuncbyrole.json', function (resbase) {

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
                if(resbase['覆盖图']) {
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
                var map = new L.Map('mapid', {
                    center: [29.24281, 120.32712],
                    zoom: 10,
                    zoomControl: false,
                    crs: L.CRS.EPSG4326,
                    layers: display_layers
                });


                myMap = map;
                var zoomControl = L.control.zoom({
                    position: 'bottomleft'
                }).addTo(myMap);
                //myMap.addControl(zoomControl);

                L.control.tuli().addTo(myMap);
                map.addControl(layersControl);
            });

        }
    }

});
