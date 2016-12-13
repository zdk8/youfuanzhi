define([cj.getModuleJs('widget/MakeDG'), cj.getModuleJs('widget/DispatcherPanel'), 'backbone'],
    function (MakeDG, DispatcherPanel, Backbone) {

        var mydelete = function () {

        }
        var view = function (record,dg) {
            DispatcherPanel.open('text!mapviews/StationView.htm', 'mapviews/StationView',
                {
                    ptype: DispatcherPanel.PANELLAYER,
                    title: ' 查看台站信息 ' ,
                    record: record,
                    dg: dg,
                    height:250
                });
        }

        return {
            render: function () {

                $('#station-dg-wrapper').show();
                var dg = MakeDG.make($('#station-dg'),
                    {mydelete: mydelete, update: view, view: view},
                    {
                        fit: true,
                        rownumbers: false,
                        fitColumns: true,
                        url2: 'data-json/stations.json',
                        url:'getyqstation'
                    }
                );
            }
        };
    });