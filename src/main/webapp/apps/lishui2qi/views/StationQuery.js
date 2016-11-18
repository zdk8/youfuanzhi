define([cj.getModuleJs('widget/MakeDG'), cj.getModuleJs('widget/DispatcherPanel'), 'backbone'],
    function (MakeDG, DispatcherPanel, Backbone) {

        var mydelete = function () {

        }
        var view = function () {

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
                        url: 'data-json/test/datagrid_data1.json'
                    }
                );
            }
        };
    });