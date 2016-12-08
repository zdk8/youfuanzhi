/**
 * Created by weipan on 2016/04/25.
 * desc:下拉菜单维护
 */

define([cj.getModuleJs('widget/MakeDG'),cj.getModuleJs('model/PanelArgsModel'),'underscore'],function(MakeDG,MngCallback,_){

var edit=function(record,dg){

}
var htm='<div opt="main"><label>*详细<%=aaa101%></label><div opt="add" class="tool-button-add">添加</div><br><div opt="list"></div></div>';
var tpl=_.template(htm)
    return {
        render:function(local,cb){
            var dg=MakeDG.make(local.find('.easyui-datagrid-noauto'),
                             {update:edit,codetype:function(record){
                               require(['manager/Code'],function(js){

                                   var smlocal=local.find('div[opt=combodt]').html(tpl(record));
                                   js.render(smlocal,new MngCallback({record:record}));
                               })

                             }},
                             {url: 'eers/xt_combo?customtag=pagination',
                              toolbar: local.find('div[tb]')}
                            );

        }
    }
})