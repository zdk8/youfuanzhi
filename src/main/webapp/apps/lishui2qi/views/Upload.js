define(['jqueryform'], function () {

    return {
        render: function (local, cb) {
            var $demo1 = local.find('div[opt=demo1]');
            var $demo2 = local.find('div[opt=demo2]');
            var $demo3 = local.find('div[opt=demo3]');
            var bar = $demo1.find('.bar');
            var percent = $demo1.find('.percent');
            var status = $demo1.find('div[opt=status]');

            $('div[opt=demo1] form').ajaxForm({
                beforeSend: function () {
                    status.empty();
                    var percentVal = '0%';
                    bar.width(percentVal)
                    percent.html(percentVal);
                },
                uploadProgress: function (event, position, total, percentComplete) {
                    var percentVal = percentComplete + '%';
                    bar.width(percentVal)
                    percent.html(percentVal);
                },
                success: function () {
                    var percentVal = '100%';
                    bar.width(percentVal)
                    percent.html(percentVal);
                },
                complete: function (xhr) {
                    //status.html(xhr.responseText);
                    var myJson = $.parseJSON(xhr.responseText);
                    if(myJson.length==1) {
                        status.html('<a target= _blank href=' + myJson[0].url + ' >'+myJson[0].url+'</a>');
                    }

                }
            });


            //第二个例子
            var bar2 = $demo2.find('.bar');
            var percent2 = $demo2.find('.percent');
            var status2 = $demo2.find('div[opt=status]');

            $('div[opt=demo2] form').ajaxForm({
                complete: function (xhr) {
                    //status.html(xhr.responseText);
                    var results = $.parseJSON(xhr.responseText);
                    status2.html("");
                    console.log(results);
                    for(var index in results) {
                        var myJson = results[index];
                        status2.append('<a target= _blank href=' + myJson.url + ' >'+myJson.url+'</a><br/>');
                    }
                }
            });


            //第三个例子
              var bar3 = $demo3.find('.bar');
            var percent3 = $demo3.find('.percent');
            var status3 = $demo3.find('div[opt=status]');

            $('div[opt=demo3] form').ajaxForm({
                complete: function (xhr) {
                    var results = $.parseJSON(xhr.responseText);
                    status3.html("");
                    for(var index in results) {
                        var myJson = results[index];
                        status3.append('<a target= _blank href=' + myJson.url + ' >'+myJson.url+'</a><br/>');
                    }
                }
            });

        }
    };
});