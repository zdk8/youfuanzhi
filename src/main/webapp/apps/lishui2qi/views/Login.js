define(['jqueryform'],function () {
   return {
       render:function (local) {
           local.find('form').ajaxForm({
                complete: function (xhr) {
                    //status.html(xhr.responseText);
                    var results = $.parseJSON(xhr.responseText);
                    
                    require(['Message'],function (message) {
                        message.show(results);
                    });

                }
            });
       }
   } 
});