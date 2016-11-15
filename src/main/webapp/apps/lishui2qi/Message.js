define([], function () {

    var logArray=[];

    $(document).ajaxComplete(function (event,jqXHR,ajaxOptions) {
        if(jqXHR.status>=400){
            $.messager.alert("warring",jqXHR.status);
        }
    });

    return {
        show: function (text) {
            $.messager.alert(text);
        }
    }
});