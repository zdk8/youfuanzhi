define([], function () {

    return {
        render: function (local, cb) {
            var myform = local.find('form');
            Myfrom = myform;
            var record = null;
            if (cb.params.record) {
                record = cb.params.record;
                try{
                    myform.form('load', record);
                    
                }catch (exception){
                    myform.append('如果此处有异常，是因为有校验功能不通过或者校验也出现了异常，先删除  easyui-validatebox');

                }

            }


            local.find('a[action=close]').bind('click', function () {
                local.trigger('MyClose');
            });

        }
    }
})