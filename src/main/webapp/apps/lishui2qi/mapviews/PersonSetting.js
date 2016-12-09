define(['jqueryform'],function () {
   return {
       render:function (local) {
           local.find('form').ajaxForm({
                complete: function (xhr) {
                    var result = $.parseJSON(xhr.responseText);
                    
                    require(['Message'],function (message) {
                        if(result.success==true) {
                            message.show("设置成功");
                            local.trigger('MyClose');
                            document.location.href="framework_geoserver";
                        }else{
                            message.show("设置失败");
                        }

                    });

                }
            });
       }
   } 
});