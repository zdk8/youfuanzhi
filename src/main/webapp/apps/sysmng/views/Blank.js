define([],function () {


    return {
        render:function (local,argsModel) {
            local.append(argsModel.get('content'));
        }
    }
})