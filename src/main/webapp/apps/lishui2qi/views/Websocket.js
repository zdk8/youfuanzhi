define(['jqueryform'], function () {

    return {
        render: function (local, cb) {
            window.setInterval(function () {
                local.find('iframe').height(local.height()-30);
            }, 3000);
        }
    };
});