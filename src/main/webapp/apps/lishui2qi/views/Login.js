define(['jqueryform'],function () {
   return {
       render:function (local) {
           local.find('form').ajaxForm({
                complete: function (xhr) {
                    //status.html(xhr.responseText);
                    var results = $.parseJSON(xhr.responseText);
                    
                    require(['Message'],function (message) {
                        if(message.success==true) {
                            message.show("登录成功");
                        }else{
                            message.show("登录失败");
                        }

                    });

                }
            });
       }
   } 
});