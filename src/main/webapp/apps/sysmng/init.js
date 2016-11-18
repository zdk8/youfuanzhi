define(['jeasyui','jeasyui_zh_CN','cj','myvalidate'],function(jeasyui,jeasyui_zh_CN,cj,myvalidate){



  return function(){
    $('#main-menu-tree').tree({
      onClick: function(node){
        require(['CmPanel'],function(js){
          var renderOptions={title:node.text};
           // alert node text property when clicked
           //js.render(MainTab.add(renderOptions),node);
           var module=node.location.replace(".","/")//+'?v='+new Date().getTime();

           js.open('text!'+module+'.htm',module,{title:node.title,ptype:0});
        })
	  }
    });

    if($('#main-menu-tree2').length){
        require(['Menu',cj.getModuleJs('widget/DispatcherPanel')],function(Menu,js){
            Menu.render($('#main-menu-tree2'), function (node) {
                var module = node.location.replace(".", "/");
                if (/manager\//.test(module)) {
                    //系统管理功能的模块，加载外部的
                    js.open(cj.getModuleTemplate(module+'.htm'),cj.getModuleJs(module) , {title: node.title, ptype: 0});
                } else {
                    js.open('text!' + module + '.htm', module, {title: node.title, ptype: 0});
                }


            })
        });
    }

    //添加头部按钮等事件
    $('.header-icon-logout').parent().bind('click',function(){
       $.get('sysapi/logoutaction',function(){
         window.location.href=window.location.href;
       });
    });

      //动态给cj添加功能，提高加载速度
  /*require(['MakeButton'],function(btn){
    cj.button=btn;
    $.extend($.fn.validatebox.defaults.rules,{
         //账号已经存在验证
    oldcardnum_exist: {
        validator: function (value, param) {
                    var result=false;
            if(value.length==15||value.length==18){
              $.ajax({
               async:false,
               url:'eers/validate-oldcardnum-exist/'+value,
               success:function(resp){
                  console.log(param);
                   console.log(resp);
                  if(resp.result[0] && param.length==2){
                    if(resp.result[0].oldid==param[0]&&resp.result[0].oldcardnum==param[1]){
                      result=true;
                    }else{
                      result=false;
                    }
                  }else{
                    result=true;
                  }
               }
            })
            }else{
               result=true;
            }


            return result;
            },
        message: '账号已经存在'
        },
        isCardNum:{
         validator:function(value,param){
            return (value.length==15||value.length==18);
         },
         message:'身份证长度应该为15位或者18位'
        }

    })
  })
*/
  require(['backbone'],function(Backbone){
     var Position= Backbone.Model.extend({
     idAttribute: "timeid",
     defaults: function() {
      return {
        lat:'',
        lng:''
      };
    }
     });

     var PositionList=  Backbone.Collection.extend({
      model:Position
     })

     global.positionList=new PositionList();

     global.positionList.listenTo(global.positionList, 'add', function(item){
       //console.log(item)
     })
     global.positionList.listenTo(global.positionList, 'change', function(item){
     console.log(item.toJSON());
       if(item.get('latDom')){
         item.get('latDom').val(item.get('lat'));
       }
       if(item.get('lngDom')){
         item.get('lngDom').val(item.get('lng'));
       }
       if(item.get('myCallBack')){
           item.get('myCallBack')(item.get('cbParams'))
       }
              global.positionList.remove(item.id);
       //item.remove();
     $('#'+item.id).remove();
     })

  });

  }
})
