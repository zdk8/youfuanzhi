define(['text!mapviews/OneHistoryQuake-template.htm'], function (htm) {

    var tpl = _.template(htm);
    var local = $('#history-earthquake');

    return {
        showItem:function (item) {
            local.html(tpl(item));
        },
        showHtml:function (html) {
            local.html(html);
        },
        initLastQuake:function () {
            $.get('getyqquake', function (data) {
                for(var i=0;i<data.length;i++) {
                    if(data[i]['earthid']=='LS185308010000'){
                        local.html(tpl(data[i]));
                    }
                }
            });
        }
    }
});