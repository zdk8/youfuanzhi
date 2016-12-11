define([cj.getModuleJs('widget/MakeDG'), cj.getModuleJs('widget/DispatcherPanel'), 'backbone'],
    function (MakeDG, DispatcherPanel, Backbone) {

        var startup = function (record, datagrid) {
            $.messager.confirm('确认', '您真的要启动预案吗?', function (r) {
                if (r) {
                    $.ajax({
                        url: 'responsestart',
                        data: {level:record.level,yjid:21},
                        type: 'post',
                        success: function () {
                            $.messager.show({
                                title: '提示',
                                msg: '操作成功',
                                showType: 'show',
                                timeout: 500,
                                style: {
                                    right: '',
                                    //top:document.body.scrollTop+document.documentElement.scrollTop,
                                    bottom: ''
                                }
                            });
                        }, error: function () {
                            $.messager.alert('错误', '操作失败', 'error');
                        }
                    });
                }
            });
        }

        var jiechu = function (record, datagrid) {
            $.messager.confirm('确认', '您真的要解除预案吗?', function (r) {
                if (r) {
                    $.ajax({
                        url: 'responsestart',
                        data: {level: record.level, yjid: 21},
                        type: 'post',
                        success: function () {
                            $.messager.show({
                                title: '提示',
                                msg: '操作成功',
                                showType: 'show',
                                timeout: 500,
                                style: {
                                    right: '',
                                    //top:document.body.scrollTop+document.documentElement.scrollTop,
                                    bottom: ''
                                }
                            });
                        }, error: function () {
                            $.messager.alert('错误', '操作失败', 'error');
                        }
                    });
                }
            });
        }

        var view = function (record, dg) {
            DispatcherPanel.open('text!views/MultiYuanForm.htm', 'views/MultiYuanForm',
                {
                    ptype: DispatcherPanel.PANELLAYER,
                    title: '预案信息：'+record.yjname,
                    height:300,
                    record: record,
                    dg: dg
                });
        }


        var module = {
            render: function (local, option) {
                var tb = $(local).find('div[tb]');
                var statusArray = [];
                var dg = MakeDG.make(local.find('.easyui-datagrid-noauto'),
                    {startup: startup, jiechu: jiechu,update: view, view: view},
                    {
                        url: 'apps/lishui2qi/data/yuan-list.json',
                        rowStyler: function (index, row) {
                            if (row.status ==1) {
                                return 'color:rgb(179, 47, 47);';
                            }
                        },
                        rowLoadCallBack:function (row,index) {
                            if(row.status==1) {
                                statusArray.push($.extend({index:index},row));
                            }
                        },
                        afterLoadSuccessCallback:function () {
                            local.find('li[action="jiechu"]').hide();
                            for(var i=0;i<statusArray.length;i++) {
                                var myRow = statusArray[i];
                                local.find('li[action="startup"]').eq(myRow['index']).hide();
                                local.find('li[action="jiechu"]').eq(myRow['index']).show();
                            }
                        },
                        toolbar: tb
                    }
                );


                _.each(['applicant', 'applyunit'], function (item) {
                    tb.find('.easyui-textbox[opt=' + item + ']').textbox({
                        onChange: function (newValue, oldValue) {
                            dg.datagrid('reload',{
                             intelligentsearch:[
                             {name:item,operate:"like",value:"%"+newValue+"%"}
                             ]
                             });
                            tb.find('a[action=search]').trigger('click');
                        }
                    })
                });


                var getSearchParams = function () {
                    var searchParams = [];
                    var applicant = tb.find('[opt=applicant]').textbox('getValue');
                    if (applicant) {
                        searchParams.push({name: 'applicant', operate: "like", value: "%" + applicant + "%"});
                    }

                    var applyunit = tb.find('[opt=applyunit]').textbox('getValue');
                    if (applyunit) {
                        searchParams.push({name: 'applyunit', operate: "like", value: "%" + applyunit + "%"});
                    }
                    return searchParams;
                };

                //点击查询
                tb.find('a[action=search]').bind('click', function () {
                    dg.datagrid('reload', {
                        intelligentsearch: getSearchParams()
                    });
                });


                //新增层式
                //apps/lishui2qi/views/MultiYuanForm.js
                tb.find('a[action=create-yuan]').bind('click', function () {
                    DispatcherPanel.open('text!views/MultiYuanForm.htm', 'views/MultiYuanForm',
                        {
                            ptype: DispatcherPanel.PANELLAYER,
                            width:600,
                            height:300,
                            title: '创建新预案',
                            dg: dg
                        });
                });


            }
        };

        return module;
    });
