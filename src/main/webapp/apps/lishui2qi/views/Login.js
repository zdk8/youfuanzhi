define(['jqueryform'],function () {
   return {
       render:function (local) {
           local.find('form').ajaxForm({
                complete: function (xhr) {
                    //status.html(xhr.responseText);
                    var result = $.parseJSON(xhr.responseText);
                    
                    require(['Message'],function (message) {
                        if(result.success==true) {
                            message.show("登录成功");
                            document.location.href="framework_geoserver"
                        }else{
                            message.show("登录失败");
                        }

                    });

                }
            });
       }
   } 
});