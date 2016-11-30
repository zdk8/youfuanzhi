define(['text!mapviews/fastmenu/ShowPoint-template.htm'], function (htm) {

    var tpl = _.template(htm);
    //添加样式；
    var markerIcon = L.icon({
        popupAnchor: [11, 3],
        iconUrl: 'images/markerIcon/六石街道.png'
    });

    return {
        start: function () {
            var addPointToMap = function (rows) {
                var mygroup = [];
                _.each(rows, function (element, index, list) {
                    var marker = L.marker([element.pointlat, element.pointlon], {icon: markerIcon}).addTo(myMap);
                    marker.bindPopup(tpl(element))
                        .on('click', function () {
                            console.log(JSON.stringify(element));
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
    }
});