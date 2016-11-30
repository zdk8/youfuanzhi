define([
    'leaflet-plugins/TuLi',
    'leaflet-plugins/leaflet.hvittianditutilelayer'], function () {
    var wms_layers = [];
    var display_layers = [];

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


    var latlng = null;
    $.get('apps/lishui2qi/data/mock-getfuncbyrole.json', function (resbase) {

        var baseMaps = {};
        var defaultLayers = resbase['默认加载图层'];
        for (var i = 0; i < resbase['底图'].length; i++) {
            var layertype = JSON.parse(resbase['底图'][i].imgcss);
            var layer_group = new L.layerGroup();

            if (layertype.type === 'tile') {
                for (var j = 0; j < layertype.resources.length; j++) {

                    var layer = L.tileLayer.hvittianditufunctional(layertype.resources[j]);
                    layer_group.addLayer(layer)
                }
                layer_group.resources = layertype.resources;
                baseMaps[resbase['底图'][i].text] = layer_group;

                var isDisplay = makeDisplayLayer(resbase['底图'][i].text, defaultLayers, layer_group);
            }
        }

        //全局地图变量
        myMap = new L.Map('mapid', {
            center: [28.45, 119.92],
            zoom: 13,
            zoomControl: false,
            crs: L.CRS.EPSG4326,
            layers: display_layers
        });

        L.marker([28.45, 119.92]).addTo(myMap);

        var zoomControl = L.control.zoom({
            position: 'bottomleft'
        }).addTo(myMap);



        L.control.tuli().addTo(myMap);

        var popup = L.popup();
        function onMapClick(e) {
            latlng = e.latlng;//给全局变量设值

            popup
                .setLatLng(e.latlng)
                .setContent("You clicked the map at " + e.latlng.toString())
                .openOn(myMap);
        }

        myMap.on('click', onMapClick);


    });


    return {

        afterInited: function () {

        }
    }
});
