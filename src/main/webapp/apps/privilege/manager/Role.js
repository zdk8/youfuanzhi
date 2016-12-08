define([cj.getModuleJs('widget/MakeDG'), cj.getModuleJs('widget/DispatcherPanel'),
cj.getModuleJs('BackendInterfaceList')], function (MakeDG, DispatcherPanel,BackendInterfaceList) {
    return {
        render: function (local, option) {
            var localDataGrid, mynode;
            var refreshGrid = function () {
                localDataGrid.datagrid('reload');
            };
            var deleteRoleInfo = function (record) {
                $.messager.confirm('确认', '您真的要删除此角色吗?', function (r) {
                    if (r) {
                        $.post(BackendInterfaceList.getUrl('delete-role-by-id').get('url'),
                            {id: record.roleid}, function (data) {
                            refreshGrid();
                        }, 'json');
                    }
                });
            };

            var viewRoleInfo = function (record) {
                DispatcherPanel.open('text!manager/RoleForm.htm','manager/RoleForm',
                    {
                        title: '角色信息', ptype: DispatcherPanel.PANELLAYER,
                        height: 200,
                        record: record
                    });

            }
            var grant = function (record) {
                DispatcherPanel.open('text!manager/Grant.htm','manager/Grant',
                    {
                        title: '授权', ptype: DispatcherPanel.PANELLAYER,
                        height: 400,
                        record: record
                    });
            }

            var localDataGrid = MakeDG.make(local.find('.easyui-datagrid-noauto'),
                {delete: deleteRoleInfo, grant: grant, view: viewRoleInfo},
                {
                    url: BackendInterfaceList.getUrl('query-role-with-check').get('url'),
                    fit: true,
                    queryParams: {
                        intelligentsp: null,
                        userid: (function () {
                            if(option.params.record){return option.params.record.userid}else{return 0;}
                        })()
                    },
                    rowLoadCallBack: function (r, i) {
                        if (r.userid) {
                            localDataGrid.datagrid('checkRow', i);
                        }
                    },
                    toolbar: local.find('div[tb]')
                }
            );


            local.find('.easyui-searchbox').searchbox({
                searcher: function (value) {
                    localDataGrid.datagrid('load', {
                        keytext: value
                    });
                }
            });

            local.find('[opt=addrole]').bind('click', function () {
                viewRoleInfo();
            })


            local.find('[action=save]').bind('click', function () {
                var checkedrows = localDataGrid.datagrid('getChecked');
                var ids = ""
                for (var i = 0, len = checkedrows.length; i < len; i++) {
                    if (ids != '') ids += ',';
                    ids += checkedrows[i].roleid;
                }
                $.post(BackendInterfaceList.getUrl('save-grant-role-to-user').get('url'),
                    {userid: option.params.record.userid, roleids: ids}, function (data) {
                    local.trigger('MyClose');
                }, 'json');
            })
        }
    }
})
