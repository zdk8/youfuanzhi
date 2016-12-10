define(['underscore','text!mapviews/StationView-template.htm'],function (_,htm) {

    var tpl = _.template(htm);
    return {
        render:function (local,cb) {
            console.log('kdfkdfkdj')
            local.html(tpl(cb.params.record));
        }
    }
});