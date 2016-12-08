define([], function () {

    var logArray=[];

    $(document).ajaxComplete(function (event,jqXHR,ajaxOptions) {
        if(jqXHR.status>=400){
            //$.messager.alert("警告",'服务器异常，状态码为：'+jqXHR.status);
            console.log("警告"+'服务器异常，状态码为：'+jqXHR.status);
        }
    });

    return {
        show: function (text) {
            $.messager.alert("提示",text);
        }
    }
});