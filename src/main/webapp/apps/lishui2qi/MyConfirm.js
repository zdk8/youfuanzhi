define([],function () {

    //ajaxOpt包括 url,type,data
    function MyConfirmAction(title,text,ajaxOpt,callbackYes,callbackNo,reqError) {
        $.messager.confirm(title||'提示', text, function (r) {
                if (r) {
                    $.ajax({
                        url: ajaxOpt.url,
                        type: ajaxOpt.type||'get',
                        data:ajaxOpt.data||{},
                        error:function () {
                            if(reqError) {
                                reqError();
                            }
                        },
                        success: function () {
                            $.messager.show({
                                title: '提示',
                                msg: '操作成功',
                                showType: 'show',
                                timeout: 500,
                                style: {
                                    right: '',
                                    //top:document.body.scrollTop+document.documentElement.scrollTop,
                                    bottom: ''
                                }
                            });
                            if(callbackYes){
                                callbackYes();
                            }
                        }, error: function () {
                            if(callbackNo) {
                                callbackNo();
                            }else{
                                $.messager.alert('错误', '操作失败', 'error');
                            }
                        }
                    });
                }
            });
    }

    return {
        confirm:function (title,text,ajaxOpt,callbackYes,callbackNo,reqError) {
            new MyConfirmAction(title,text,ajaxOpt,callbackYes,callbackNo,reqError);
        }
    }
});