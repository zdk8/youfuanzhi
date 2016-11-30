define([],function () {
    
    
    return {
        start:function () {
            var addPointToMap=function (rows) {
                _.each(rows,function (element, index, list) {
                    console.log(element);
                    L.marker([ element.pointlat,element.pointlon]).addTo(myMap);
                });
            };
            
            
            $.get('apps/lishui2qi/data/pointinfo.json',function (resp) {
                addPointToMap(resp);
            });
        }
    }
});