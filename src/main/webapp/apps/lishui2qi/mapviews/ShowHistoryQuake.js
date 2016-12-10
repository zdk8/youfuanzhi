define(['text!mapviews/ShowHistoryQuake-template.htm','mapviews/OneHistoryQuake'],
    function (htm,OneHistoryQuake) {

    var tpl = _.template(htm);
    //添加样式；
    var lt1990MarkerIcon = L.icon({
        popupAnchor: [11, 3],
        iconUrl: 'images/icons/1.png'
    });
    var gt1990MarkerIcon = L.icon({
        popupAnchor: [11, 3],
        iconUrl: 'images/icons/2.png'
    });

    var longTime = new Date('1945').getTime();
    var getIconByYear=function(earthtime) {
        if(earthtime>longTime) {
            return lt1990MarkerIcon;
        }
        return gt1990MarkerIcon;
    }

    var getMyType=function (earthtime) {
        if(earthtime>longTime) {
            return "1945年之后";
        }
        return "1945年之前";
    };


        historyEqMarkerArray=[];

    return {
        start: function () {
            var addPointToMap = function (rows) {
                var mygroup = [];
                _.each(rows, function (element, index, list) {
                    element['mytype'] = getMyType(element.earthtime);
                    var marker = L.marker([ element.lon,element.lat], {icon: getIconByYear(element.earthtime)}).addTo(myMap);
                    marker.bindPopup(tpl(element))
                        .on('click', function () {
                            //console.log(JSON.stringify(element));
                            OneHistoryQuake.showItem(element);
                        });
                   /* if (index == rows.length - 1) {
                        marker.openPopup();
                    }*/

                    historyEqMarkerArray.push(marker);
                    marker.addTo(myMap);
                });

                //var ds = L.layerGroup(mygroup);
                //myMap.addLayer(ds);
            };

            //apps/lishui2qi/data/pointinfo.json
            $.get('getyqquake', function (resp) {
                addPointToMap(resp);
            });
        },
        hide:function () {
            _.each(historyEqMarkerArray,function (element, index, list) {
                myMap.removeLayer(element);
            });
        }
    }
});