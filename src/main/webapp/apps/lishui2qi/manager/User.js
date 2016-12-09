/**
 * Created by Administrator on 2014/9/28.
 * updated by wp on 2016年 4月17日 星期日 16时53分34秒 CST
 * 减少代码量
 */


define(['MakeDG','CmPanel'],function(MakeDG,CmPanel){
    return {
        render:function(local,option){

            var localDataGrid,mynode;
            var refreshGrid=function() {
                localDataGrid.datagrid('reload');
            };
          var deleteUserInfo=function(record) {
            $.messager.confirm('确认','您真的要删除此用户吗?',function(r){
              if (r){
                $.post('deluserbyid', {id:record.userid}, function (data) {
                  refreshGrid();
                }, 'json');

              }
            });
          };

          var viewUserInfo=function(record) {
            CmPanel.open('text!manager/UserForm.htm','manager/UserForm',
                         {title:'查看用户详细信息',ptype:0,record:record});

          };


            var addRole=function(record){
                require(['commonfuncs/popwin/win','text!manager/Role.htm','manager/Role'],
                    function(win,htmfile,jsfile){
                        win.render({
                            title:'添加角色信息',
                            width:524,
                            height:500,
                            html:$(htmfile),
                            buttons:[
                                {text:'取消',handler:function(html,parent){
                                    parent.trigger('close');
                                }},
                                {text:'保存',handler:function(html,parent){ }}
                            ],
                            renderHtml:function(poplocal,submitbtn,parent){
                                jsfile.render(poplocal,{
                                    submitbtn:submitbtn,
                                    queryParams:record,
                                    parent:parent
                                })
                            }
                        })
                    })
            }


            var $mytree=$('#Divisiontree').tree({
                checkbox:true,
                url:'sys/query-division-tree',
                animate:true,
                onClick:function(node){
                    var dvcode=node.dvcode;
                    mynode=node;
                    var len=dvcode.length;
                    dvcode=dvcode.substr(len-2,len-1)=="00"?dvcode.substr(0,len-2):dvcode;
                    localDataGrid.datagrid('load',{
                        node: dvcode
                    });
                },onLoadSuccess:function(node, data){
                    if(!mynode){
                        mynode = data[0];
                    }
                }
            });

          var tb=local.find('div[tb]');
          localDataGrid=MakeDG.make(local.find('.easyui-datagrid-noauto'),
                                    {view:viewUserInfo,delete:deleteUserInfo,addrole:addRole},
                                    {
                                      url:'getuserbyregionid',
                                      method:'post',
                                      queryParams: {
                                        intelligentsp:null
                                      },

                                      toolbar: tb}
                                   );


            //添加用户的弹出表单
            local.find('[opt=adduser]').bind('click',function(){
                viewUserInfo();
            })
        }
    }
})
