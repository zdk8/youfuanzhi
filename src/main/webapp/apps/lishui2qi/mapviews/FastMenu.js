define(['text!mapviews/FastMenu-template.htm'],function (htm) {
    
    
    var menuList=[{name:'A',action:'a'},{name:'A',action:'a'},{name:'A',action:'a'},{name:'采集信息',action:'ShowPoint'}];
            var tpl = _.template(htm);
            $('#lishui-map-button').append(tpl({children: menuList}));
            $('#lishui-map-button a.text').each(function () {
               $(this).bind('click',function () {
                   $(this).siblings('.text').removeClass('selected');
                   $(this).addClass('selected');

                   //根据触发不同的action加载不同的js，并调用start
                   require(['mapviews/fastmenu/'+$(this).attr('action')],function (action) {
                       action.start();
                   });
               });
            });
    
    
    
    return {
        render:function () {

        }
    }
    
});