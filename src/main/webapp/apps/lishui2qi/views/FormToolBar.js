define([], function () {


    return {
        render: function (local) {
            local.find('a[action=click-me]').click(function () {
                require([cj.getModuleJs('widget/DispatcherPanel')], function (DispatcherPanel) {
                    DispatcherPanel.open('text!views/SampleButton.htm', 'views/SampleButton',
                        {
                            ptype: 0, title: '查看: ',
                            record: {title1: 1},
                            cacheFn: function () {
                                return "helloworld";
                            }
                        });
                });
            });
        }
    }
})