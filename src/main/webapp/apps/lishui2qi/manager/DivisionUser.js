/**
 * Created by Administrator on 2014/9/28.
 */


define([cj.getModuleJs('widget/MakeDG'), cj.getModuleJs('widget/DispatcherPanel'),
cj.getModuleJs('BackendInterfaceList')], function (MakeDG, DispatcherPanel,BackendInterfaceList) {
    return {
        render: function (local, option) {

            var currentNode = null;

            var localDataGrid, mynode;
            var refreshGrid = function () {
                localDataGrid.datagrid('reload');
            };

            var deleteUserInfo = function (record) {
                $.messager.confirm('确认', '您真的要删除此用户吗?', function (r) {
                    if (r) {
                        $.ajax({
                            url: 'sys/delete-user',
                            data: {id: record.userid},
                            type: 'post',
                            success: function () {

                                refreshGrid();
                                $.messager.show({
                                    title: '提示',
                                    msg: '操作成功',
                                    showType: 'show',
                                    timeout: 500,
                                    style: {
                                        right: '',
                                        bottom: ''
                                    }
                                });
                            }, error: function () {
                                $.messager.alert('错误', '操作失败', 'error');
                            }
                        })
                    }
                });
            };

            var addUser = function (record) {
                DispatcherPanel.open('text!manager/DivisionUserForm.htm',
                    'manager/DivisionUserForm',
                    {
                        title: '用户 '+(record.username||''),
                        height:300,
                        ptype: DispatcherPanel.PANELLAYER,
                        record: $.extend({
                            regionid: currentNode.dvcode,
                            totalname: currentNode.totalname
                        }, record),
                        mycallback:function () {
                            refreshGrid();
                        }
                    });
            }

            var addRole = function (record) {
                DispatcherPanel.open('text!manager/Role.htm','manager/Role',
                    {
                        title: '角色信息', ptype: DispatcherPanel.PANELLAYER,
                        record: record
                    });
            }


            var $menu = $('#mm');
            var $mytree = $('#Depttree').tree({
                lines: true,
                url: BackendInterfaceList.getUrl('query-division-tree').get('url'),
                animate: true,
                onClick: function (node) {
                    var dvcode = node.id;
                    mynode = node;
                    var len = dvcode.length;
                    //dvcode=dvcode.substr(len-2,len-1)=="00"?dvcode.substr(0,len-2):dvcode;
                    localDataGrid.datagrid('load', {
                        node: dvcode
                    });
                    currentNode = node;
                    $('#user-division-totalname').text('区域：' + node.totalname);
                }, onLoadSuccess: function (node, data) {
                    if (!mynode) {
                        mynode = data[0];
                        currentNode=mynode;
                        //自动打开一次
                        $mytree.tree('expand', $mytree.tree('getRoot').target);
                    }
                }
            });

            var localDataGrid = MakeDG.make(local.find('.easyui-datagrid-noauto'),
                {delete: deleteUserInfo, addrole: addRole, view: addUser},
                {
                    url: BackendInterfaceList.getUrl('query-user-by-regionid').get('url'),

                    queryParams: {
                        keytext: null,
                        intelligentsp: null
                    },
                    toolbar: local.find('div[tb]')
                }
            );
            local.find('.easyui-searchbox').searchbox({
                searcher: function (value) {
                    localDataGrid.datagrid('load', {
                        keytext: value,
                        node: mynode.id
                    });
                }
            });

            //添加用户的弹出表单
            local.find('a[opt=adduser]').bind('click', function () {
                addUser({});
            })
        }
    }
})
