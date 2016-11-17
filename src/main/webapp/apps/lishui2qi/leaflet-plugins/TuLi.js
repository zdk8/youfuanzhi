(function (factory, window) {

    // define an AMD module that relies on 'leaflet'
    if (typeof define === 'function' && define.amd) {
        define(['leaflet'], factory);

        // define a Common JS module that relies on 'leaflet'
    } else if (typeof exports === 'object') {
        module.exports = factory(require('leaflet'));
    }

    // attach your plugin to the global 'L' variable
    if (typeof window !== 'undefined' && window.L) {
        window.L.TuLi = factory(L);
    }
}(function (L) {

    // implement your plugin

    // return your plugin when you are done

    var MyLeafletPlugin = L.Control.extend({
        options: {
            position: 'bottomleft'
        },

        onAdd: function (map) {
            console.log('add');
            this.container = L.DomUtil.create('div', 'leaflet-bar leaflet-control leaflet-control-tuli ');
            this.link = L.DomUtil.create('a', 'leaflet-control-liveupdate-button leaflet-bar-part', this.container);
            this.link.href = '#';
            this.link.innerHTML = "图例";
            //L.DomEvent.disableClickPropagation(this._container);

            L.DomEvent.on(this.link, 'click', this._click, this);
            return this.container;
        },
        _click: function (e) {
            L.DomEvent.stopPropagation(e);
            L.DomEvent.preventDefault(e);
            this.toggleUpdating();
        },
        toggleUpdating: function () {
            if($('#map-menu-wrapper').is(":hidden")){
                $('#map-menu-wrapper').show();
            }else{
                $('#map-menu-wrapper').hide();
            }
        }
    });

    L.Map.mergeOptions({
        tuli: false
    });

    L.Map.addInitHook(function () {
        if (this.options.tuli) {
            this.tuli = new MyLeafletPlugin();
            this.addControl(this.tuli);
        }
    });

    L.control.tuli = function (options) {
        return new MyLeafletPlugin(options);
    };


    //return MyLeafletPlugin;
}, window));