(function () {

    var urlPrefix = window.location.hostname + (window.location.port ? ":" + window.location.port : "");
    var a = window.location.pathname.split('/');
    var pathname = urlPrefix + '/' + a[a.length - 2];
    for (var p in baseConfig.paths) {

        if (baseConfig.paths[p].substr(0, 4) == 'http') {
            continue;
        }
        baseConfig.paths[p] = window.location.protocol + "//" + (pathname + '/' + baseConfig.paths[p]).replace(/\/\//g, "/");
    }

    //以上为固定写法，复制即可。

    //配置基本相对路径和缓存设置
    var baseUrl = 'apps/privilege/';
    var options = {
        baseUrl: baseUrl
        //,urlArgs: "dc_=sqwork" //+  (new Date()).getTime()
        , urlArgs: function (id, url) {
            var args = 'v=0';
            var prefixPath = baseUrl;
            if (!true || url.substr(0, prefixPath.length) == prefixPath) {
                args = '?v=2' + new Date().getTime();
                return args;
            }

            return '';
        },
        config: {
            text: {
                useXhr: function (url, protocol, hostname, port) {
                    // allow cross-domain requests
                    // remote server allows CORS
                    return true;
                }
            }
        }

    };

    for (var p in baseConfig) {
        options[p] = baseConfig[p];
    }

    //requirejs配置
    requirejs.config(options);


    require(['jquery', 'jeasyui', 'cj'], function ($, jeasyui, cj) {
        //配置库的前半部分url
        cj.configModulePrefix(HvitFrontFWPrefix+"hvit-front-framework/");
        //绑定按钮工具到cj上
        require([cj.getModuleJs('widget/MakeButton')], function (btn) {
            cj.button = btn;
        });
                //设置分页参数
        cj['getPageSize']=function () {
            return 15;
        };
        cj['getPageList'] = function () {
            return [10, 15, 30, 50];
        };
        
        $('body').layout();
        require(['init'], function (Init) {
            new Init();
        })
    });


})();
