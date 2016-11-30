define(['leaflet-plugins/TuLi',
    'leaflet-plugins/leaflet-tilelayer-wmts-src',
    'leaflet-plugins/leaflet.hvittianditutilelayer'],function (TuLi) {
   
    
     myMap =new L.map('mapid', {
             crs:L.CRS.EPSG4326,
         tms: true,
                 center:[28.45,119.92],
            zoom:10,
            zoomControl: false
        })//.setView([30.58366, 119.91749], 13);

        var zoomControl=L.control.zoom({
            position: 'bottomleft'
        }).addTo(myMap);
        //myMap.addControl(zoomControl);

        L.control.tuli().addTo(myMap);

        var mapurl1='http://t0.tianditu.com/vec_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=vec&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}';
        //mapurl1='https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpandmbXliNDBjZWd2M2x6bDk3c2ZtOTkifQ._QA7i5Mpkd_m30IGElHziw';

    var mapurl2='http://t0.tianditu.com/cva_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cva&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}';
        //L.tileLayer(mapurl2, {maxZoom: 18}).addTo(myMap);
        L.tileLayer.hvittianditufunctional(
            [{level:[8,9,10,11,12,13,14,15,16,17],url:mapurl1}]
        ).addTo(myMap);

        /*       var url = 'http://t0.tianditu.com/vec_c/wmts';
        var emap = new L.TileLayer.WMTS(url,
            {
                //tileSize: 256,
                layer: 'vec',
                style: "default",
                tilematrixSet: "c",
                format: "tile"
            }
        );
        var emap2 = new L.TileLayer.WMTS('http://t0.tianditu.com/cva_c/wmts',
            {
                //tileSize: 256,
                layer: 'cva',
                style: "default",
                tilematrixSet: "c",
                format: "tile"
            }
        );*/
        //myMap.addLayer(emap);
        //myMap.addLayer(emap2);

        L.tileLayer(mapurl1, {
            maxZoom: 18,
            attribution: '地图数据 &copy; <a href="#">丽水</a> 贡献, ' +
            '<a href="#">地震局</a>, ' +
            'admin © <a href="#">lishui</a>',
            id: 'mapbox.streets'
        })//.addTo(myMap);




        function onMapClick(e) {
            latlng = e.latlng;//给全局变量设值

            popup
                .setLatLng(e.latlng)
                .setContent("You clicked the map at " + e.latlng.toString())
                .openOn(myMap);
        }

        //myMap.on('click', onMapClick);
    

    return {

            afterInited:function () {

            }
        }
});