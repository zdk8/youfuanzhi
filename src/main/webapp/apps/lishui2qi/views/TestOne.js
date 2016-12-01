define([],function () {


    return {
        render:function (local) {
            local.append(new Date().toLocaleTimeString());
        }
    }
})