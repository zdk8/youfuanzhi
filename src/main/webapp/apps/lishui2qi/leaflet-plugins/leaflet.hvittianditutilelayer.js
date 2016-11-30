L.TileLayer.Functional = L.TileLayer.extend({

    _tileFunction: null,

    initialize: function (tileFunction, options) {
        this._tileFunction = tileFunction;
        L.TileLayer.prototype.initialize.call(this, null, options);
    },

    filerUrl:function(resource_arr,zoom,options){

        var url="";

        var levlearrs=[];
        for(var i=0;i<resource_arr.length;i++){

            levlearrs=levlearrs.concat(resource_arr[i].level);

           /* if(resource_arr[i].level.indexOf(zoom)>-1){
                url=resource_arr[i].url;
            }*/
            for(var k=0;k<resource_arr[i].level.length;k++){
                if(zoom==resource_arr[i].level[k]){
                    url=resource_arr[i].url;
                }i
            }

        }
        var maxlevel=Math.max.apply(Math,levlearrs);
        var minlevel=Math.min.apply(Math,levlearrs);
        options.minZoom=minlevel;
        options.maxZoom=maxlevel;
        var result_obj={"url":url};


        return result_obj;
    },

    getTileUrl: function (tilePoint) {
        var map = this._map,
            crs = map.options.crs,
            tileSize = this.options.tileSize,
            zoom = tilePoint.z,
            nwPoint = tilePoint.multiplyBy(tileSize),
            sePoint = nwPoint.add(new L.Point(tileSize, tileSize)),
            nw = crs.project(map.unproject(nwPoint, zoom)),
            se = crs.project(map.unproject(sePoint, zoom)),
            bbox = [nw.x, se.y, se.x, nw.y].join(',');

        // Setup object to send to tile function.
        var view = {
            bbox: bbox,
            width: tileSize,
            height: tileSize,
            zoom: zoom,
            tile: {
                row: this.options.tms ? this._tileNumBounds.max.y - tilePoint.y : tilePoint.y,
                column: tilePoint.x
            },
            subdomain: this._getSubdomain(tilePoint)
        };

        return this._tileFunction(view);
    },

    _loadTile: function (tile, tilePoint) {
        tile._layer = this;
        tile.onload = this._tileOnLoad;
        tile.onerror = this._tileOnError;

        this._adjustTilePoint(tilePoint);
        var tileUrl = this.getTileUrl(tilePoint);

        if (typeof tileUrl === 'string') {
            tile.src = tileUrl;
            this.fire('tileloadstart', {
                tile: tile,
                url: tile.src
            });
        } else if (typeof tileUrl.then === 'function') {
            // Assume we are dealing with a promise.
            var self = this;
            tileUrl.then(function (tileUrl) {
                tile.src = tileUrl;
                self.fire('tileloadstart', {
                    tile: tile,
                    url: tile.src
                });
            });
        }
    }
});

L.tileLayer.hvittianditufunctional = function (arr, options) {

    var tiandiurl="";
    var resultLayer= new L.TileLayer.Functional(function (view) {

        var level = view.zoom;
        var filter_obj=this.filerUrl(arr,level,this.options);
        tiandiurl=filter_obj.url;
        options=filter_obj.options;

        var bounds=view.bbox.split(",");
        var topTileFromX= -180;
        var topTileFromY= 90;
        var topTileToX= 180;
        var topTileToY= -270;
        var coef = 360 / Math.pow(2, level);

        var x_num =  Math.round((bounds[0] - topTileFromX) / coef) ;
        var y_num = Math.round((topTileFromY - bounds[3]) / coef);
        var url = tiandiurl
                .replace('{z}', view.zoom)
                .replace('{y}', y_num)
                .replace('{x}', x_num)
            ;
        return url;
    }, options);
    resultLayer._url=tiandiurl;
    return resultLayer;
};
