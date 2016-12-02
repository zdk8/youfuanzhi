define(['text!mapviews/FastMenu-template.htm',
    cj.getModuleJs('widget/DispatcherPanel')], function (htm, DispatcherPanel) {


    var menuList = [{name: '预案响应信息', action: 'YuanResponseMessageQuery'},
        {name: '爆破备案', action: 'BlastQuery'},
        {name: '资料上传下载', action: 'ZiliaoQuery'},
        {name: '应急数据共享', action: 'SharedQuery'},
        {name: '采集信息', action: 'ShowPoint'}];
    var tpl = _.template(htm);
    $('#lishui-map-button').append(tpl({children: menuList}));
    $('#lishui-map-button a.text').each(function () {
        $(this).bind('click', function () {
            $(this).siblings('.text').removeClass('selected');
            $(this).addClass('selected');

            var actionName = $(this).attr('action');
            var textName = $(this).text();
            var winWidth=1000;
            if(actionName=='YuanResponseMessageQuery'){
                winWidth=1000;
            }else if(actionName=='BlastQuery') {
                winWidth=800;
            }else if(actionName=='ZiliaoQuery') {
                winWidth=800;
            }else if(actionName=='SharedQuery') {
                winWidth=500;
            }

            //根据触发不同的action加载不同的js，并调用start
            if (actionName == 'ShowPoint') {
                require(['mapviews/fastmenu/' +actionName], function (action) {
                    action.start();
                });
            } else {
                DispatcherPanel.open('text!views/'+actionName+'.htm', 'views/'+actionName,
                    {
                        ptype: DispatcherPanel.PANELLAYER,
                        title: textName,
                        width:winWidth,
                        height: 480
                    });
            }

        });
    });


    return {
        render: function () {

        }
    }

});