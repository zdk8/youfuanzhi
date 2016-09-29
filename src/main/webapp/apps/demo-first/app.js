(function(){

      var urlPrefix=window.location.hostname+(window.location.port?":"+window.location.port:"");
    var a=window.location.pathname.split('/');
    var pathname =urlPrefix+'/'+a[a.length-2];
  for(var p in baseConfig.paths) {

    if(baseConfig.paths[p].substr(0,4)=='http'){
      continue;
    }
        baseConfig.paths[p]=window.location.protocol+"//"+(pathname+'/'+baseConfig.paths[p]).replace(/\/\//g,"/");
    }
    //配置基本相对路径和缓存设置
    var options={
        baseUrl: 'apps/demo-first/'
        //,urlArgs: "dc_=sqwork" //+  (new Date()).getTime()
        ,urlArgs: function(id, url) {
                 var args = 'v=0';
                 var prefixPath='apps/demo-first/';
                 if (url.substr(0,prefixPath.length)==prefixPath) {
                     args = '?v=2'+new Date().getTime();
                     return args;
                 }

                 return '';
             }

    };

    for(var p in baseConfig) {
        options[p] = baseConfig[p];
    }


  requirejs.config(options);


  require(['jquery','jeasyui','cj'],function($,jeasyui,cj){
   $('body').layout();
   require(['init'],function(Init){new Init();})
  });


})();
