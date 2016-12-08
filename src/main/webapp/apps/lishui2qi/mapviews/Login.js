define(['jqueryform','Message'],function (jQform,message) {
   return {
       render:function (local) {
           local.find('form').ajaxForm({
                complete: function (xhr) {
                    //status.html(xhr.responseText);

                    try{
                        var result = $.parseJSON(xhr.responseText);
                        if(result.success==true) {
                            message.show("登录成功");
                            document.location.href="framework_geoserver"
                        }else{
                            message.show("登录失败,用户名或者密码不正确");
                        }
                    }catch (e){
                        message.show("登录异常");
                    }
                }
            });
       }
   } 
});