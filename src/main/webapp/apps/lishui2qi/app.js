(function () {

    var urlPrefix = window.location.hostname + (window.location.port ? ":" + window.location.port : "");
    var a = window.location.pathname.split('/');
    var pathname = urlPrefix + '/' + a[a.length - 2];
    for (var p in baseConfig.paths) {

        //console.log(p+':'+baseConfig.paths[p]);

        if (baseConfig.paths[p].substr(0, 4) == 'http') {
            continue;
        }
        baseConfig.paths[p] = window.location.protocol + "//" + (pathname + '/' + baseConfig.paths[p]).replace(/\/\//g, "/");
    }

    //覆盖
    //baseConfig.paths['jquery']='http://cdn.bootcss.com/jquery/3.1.1/jquery.min';
    baseConfig.paths['jquery']='../../js/libs/jquery/3.1.1/jquery.min';
    //添加地图库leaflet
    //baseConfig.paths['leaflet']='http://cdn.bootcss.com/leaflet/1.0.1/leaflet';
    baseConfig.paths['leaflet']='http://192.168.3.102:8080/dymap-java/js/leaflet';

    //以上为固定写法，复制即可。

    //配置基本相对路径和缓存设置
    var baseUrl = 'apps/lishui2qi/';
    var options = {
        baseUrl: baseUrl
        //,urlArgs: "dc_=sqwork" //+  (new Date()).getTime()
        , urlArgs: function (id, url) {
            var args = 'v=0';
            var prefixPath = baseUrl;
            if (url.substr(0, prefixPath.length) == prefixPath) {
                if(url.indexOf('..')>0){
                    return '';
                }
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


    require(['jquery', 'jeasyui', 'cj','leaflet'], function ($, jeasyui, cj,leaflet) {
        //配置库的前半部分url
        cj.configModulePrefix(HvitFrontFWPrefix+"hvit-front-framework/");
        //绑定按钮工具到cj上
        require([cj.getModuleJs('widget/MakeButton')], function (btn) {
            cj.button = btn;
        });
        $('body').layout();
        require(['init'], function (Init) {
            new Init();
        })
    });


})();
