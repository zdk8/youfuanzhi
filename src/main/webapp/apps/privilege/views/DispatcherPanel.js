define([],function () {
    
    //resources/public/apps/hvit-help/views/DispatcherPanel.js
    return {render:function (local,argsModel) {
        local.append('hello world');

        //按钮1
        local.find('a[action=one]').click(function () {
            require([cj.getModuleJs('widget/DispatcherPanel')], function (DispatcherPanel) {
                var module = 'views/TestOne';
                DispatcherPanel.open('text!' + module + '.htm', module, {
                    title: "我在这里",
                    ptype: DispatcherPanel.PANELINLINE
                });
            });
        });

        //按钮2
        local.find('a[action=two]').click(function () {
            require([cj.getModuleJs('widget/DispatcherPanel')], function (DispatcherPanel) {
                var module = 'views/TestOne';
                DispatcherPanel.open('text!' + module + '.htm', module, {
                    title: "我在这里",
                    ptype: DispatcherPanel.PANELLAYER
                });
            });
        });
    }}
    
    
});