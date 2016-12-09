/**
 * Created by Administrator on 2014/9/28.
 */


define(function(){
    return {
        render:function(local,option){

            var localDataGrid,mynode;
            var refreshGrid=function() {
                localDataGrid.datagrid('reload');
            };
            var deleteUserInfo=function(record) {
                $.post('deluserbyid', {id:record.userid}, function (data) {
                    refreshGrid();
                }, 'json');
            };
            var viewUserInfo=function(record){
                require(['commonfuncs/popwin/win','text!manager/UserForm.htm'],
                    function(win,htmfile){
                        win.render({
                            title:'用户信息',
                            width:524,
                            html:$(htmfile).eq(0),
                            buttons:[
                                {text:'取消',handler:function(html,parent){
                                    parent.trigger('close');
                                }},
                                {text:'保存',handler:function(html,parent){ }}
                            ],
                            renderHtml:function(poplocal,submitbtn,parent){
                                if(mynode){
                                    //poplocal.find('[opt=tip]').text(mynode.totalname);
                                    //poplocal.find('[name=regionid]').val(mynode.dvcode);
                                    poplocal.find('[name=regionid]').val('330100');
                                    poplocal.find('[opt=tip]').text('杭州市');

                                    poplocal.find('[name=dept]').val(mynode.text);
                                    poplocal.find('[name=deptid]').val(mynode.id);



                                    $(submitbtn).bind('click', function () {
                                        poplocal.find('form').form('submit', {
                                            url: 'saveuser',
                                            onSubmit: function (param) {
                                              var ps=poplocal.find('[name=passwd]');
                                              /*
                                                if((ps.val()||'').length<24){
                                                    ps.val(CryptoJS.enc.Base64.stringify(CryptoJS.MD5(ps.val())));
                                                }
*/
                                                var isValid = $(this).form('validate');
                                                if (!isValid) {
                                                    $.messager.progress('close');
                                                }
                                                if(!poplocal.find('[name=userid]').val()){
                                                    param.flag=-1;
                                                }
                                                return isValid;
                                            },
                                            success: function (data) {
                                                var obj = $.evalJSON(data);
                                                if(obj.success) {
                                                    parent.trigger('close');
                                                    refreshGrid();
                                                }
                                            }
                                        })
                                    });
                                }
                                if(record) {
                                    $.post('getuserbyid',{
                                        id:record.userid
                                    },function(data){
                                        poplocal.find('form').form('load', data);//加载数据到表单
                                        poplocal.find('[opt=tip]').text(data.totalname);
                                    },'json')
                                }
                            }
                        })
                    })
            }
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


            var $menu=$('#mm');
            var $mytree=$('#Depttree').tree({
                lines:true,
                url:'getdepttree',
                animate:true,
                onClick:function(node){
                    var dvcode=node.id;
                    mynode=node;
                    var len=dvcode.length;
                    //dvcode=dvcode.substr(len-2,len-1)=="00"?dvcode.substr(0,len-2):dvcode;
                    localDataGrid.datagrid('load',{
                        node: dvcode
                    });
                },onLoadSuccess:function(node, data){
                    if(!mynode){
                        mynode = data[0];
                        $mytree.tree('expand', $mytree.tree('getRoot').target);
                    }
                },
                onContextMenu: function(e, node){
                    e.preventDefault();
                    // select the node
                    $mytree.tree('select', node.target);
                    // display context menu
                    $menu.menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                }
            });

            function append(){
                var t = $mytree;
                var node = t.tree('getSelected');
                require(['commonfuncs/popwin/win','text!manager/DeptForm.htm'],
                    function(win,htmfile){
                        win.render({
                            title:'部门信息',
                            width:524,
                            height:200,
                            html:$(htmfile).eq(0),
                            buttons:[
                                {text:'取消',handler:function(html,parent){
                                    parent.trigger('close');
                                }},
                                {text:'保存',handler:function(html,parent){ }}
                            ],
                            renderHtml:function(poplocal,submitbtn,parent){
                                if(true){
                                    $(submitbtn).bind('click', function () {
                                        poplocal.find('form').form('submit', {
                                            url: 'savedept',
                                            onSubmit: function (param) {
                                                var isValid = $(this).form('validate');
                                                if (!isValid) {
                                                    $.messager.progress('close');
                                                }
                                                if(!poplocal.find('[name=id]').val()){
                                                    param.flag=-1;
                                                    param.parent=node.id;
                                                }
                                                return isValid;
                                            },
                                            success: function (data) {
                                                var obj = $.evalJSON(data);
                                                if(obj.success) {
                                                    parent.trigger('close');
                                                    refreshGrid();
                                                    t.tree('reload', node.target);
                                                }
                                            }
                                        })
                                    });
                                }
                            }
                        })
                    })

            }
            function edit(){
                var t = $mytree;
                var node = t.tree('getSelected');
                require(['commonfuncs/popwin/win','text!manager/DeptForm.htm'],
                    function(win,htmfile){
                        win.render({
                            title:'部门信息',
                            width:524,
                            height:200,
                            html:$(htmfile).eq(0),
                            buttons:[
                                {text:'取消',handler:function(html,parent){
                                    parent.trigger('close');
                                }},
                                {text:'保存',handler:function(html,parent){ }}
                            ],
                            renderHtml:function(poplocal,submitbtn,parent){
                                if(true){
                                    $(submitbtn).bind('click', function () {
                                        poplocal.find('form').form('submit', {
                                            url: 'savedept',
                                            onSubmit: function (param) {
                                                var isValid = $(this).form('validate');
                                                if (!isValid) {
                                                    $.messager.progress('close');
                                                }
                                                if(!poplocal.find('[name=id]').val()){
                                                    param.flag=-1;
                                                    param.parent=node.id;
                                                }
                                                return isValid;
                                            },
                                            success: function (data) {
                                                var obj = $.evalJSON(data);
                                                if(obj.success) {
                                                    parent.trigger('close');
                                                    refreshGrid();
                                                    t.tree('reload', node.target);

                                                    if(node.id) {
                                                        $.post('getdeptbyid',{
                                                            id:node.id
                                                        },function(data){
                                                            t.tree('update', $.extend( { target: node.target},data,{text:data.deptname}));
                                                        },'json')
                                                    }
                                                    if (node){
                                                        $('#tt').tree('update', {
                                                            target: node.target,
                                                            text: 'new text'
                                                        });
                                                    }
                                                }
                                            }
                                        })
                                    });
                                }
                                if(node.id) {
                                    $.post('getdeptbyid',{
                                        id:node.id
                                    },function(data){
                                        poplocal.find('form').form('load', data);//加载数据到表单
                                    },'json')
                                }
                            }
                        })
                    })

            }
            function removeit(){
                var node = $mytree.tree('getSelected');
                $.ajax({
                    url:'deldeptbyid',data:{id:node.id},
                    type:'post',
                    success:function(){
                        $mytree.tree('remove', node.target);
                    },
                    error:function (result) {

                        alert('请确认部门下不存在人员再进行删除');

                    }
                })


            }
            function collapse(){
                var node = $mytree.tree('getSelected');
                $mytree.tree('collapse',node.target);
            }
            function expandAll(){
                var node = $mytree.tree('getSelected');
                $mytree.tree('expandAll',node.target);
            }
            function expand(){
                var node = $mytree.tree('getSelected');
                $mytree.tree('expand',node.target);
            }
            function refresh(){
                var node = $mytree.tree('getSelected');
                $mytree.tree('reload',node.target);
            }

            $menu.find('[opt=append]').bind('click', append);
            $menu.find('[opt=removeit]').bind('click', removeit);
            $menu.find('[opt=edit]').bind('click', edit);
            $menu.find('[opt=collapse]').bind('click', collapse);
            $menu.find('[opt=expandAll]').bind('click', expandAll);
            $menu.find('[opt=expand]').bind('click', expand);
            $menu.find('[opt=refresh]').bind('click', refresh);


            localDataGrid=
                local.find('.easyui-datagrid-noauto').datagrid({
                    url:'getuserbydeptid',
                    queryParams: {
                        keytext:null,
                        intelligentsp:null
                    },
                    onLoadSuccess:function(data){
                        var viewbtns=local.find('[action=view]');
                        var deletebtns=local.find('[action=delete]');
                        var addrolebtns=local.find('[action=addrole]');
                        var btns_arr=[viewbtns,deletebtns,addrolebtns];
                        var rows=data.rows;
                        for(var i=0;i<rows.length;i++){
                            for(var j=0;j<btns_arr.length;j++){
                                (function(index){
                                    var record=rows[index];
                                    $(btns_arr[j][i]).click(function(){

                                        if($(this).attr("action")=='view'){
                                            viewUserInfo(record);
                                        }else if($(this).attr("action")=='delete'){
                                            deleteUserInfo(record);
                                        }else if($(this).attr("action")=='addrole'){

                                            addRole(record);
                                        }
                                    });
                                })(i);
                            }
                        }
                    },
                    striped:true,
                    toolbar:local.find('div[tb]')
                })

            local.find('.easyui-searchbox').searchbox({
                searcher:function(value){
                    localDataGrid.datagrid('load',{
                        keytext:value,
                        node:mynode.id
                    });
                }
            });

            //添加用户的弹出表单
            local.find('[opt=adduser]').bind('click',function(){
                viewUserInfo();
            })
        }
    }
})
