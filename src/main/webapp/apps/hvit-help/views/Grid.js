define([cj.getModuleJs('widget/MakeDG'), cj.getModuleJs('widget/DispatcherPanel'), 'backbone'],
    function (MakeDG, DispatcherPanel, Backbone) {
        var OldinfoModel = Backbone.Model.extend({
            urlRoot: 'eers/oldinfo',
            idAttribute: "oldid"
        });

        var mydelete = function (record, datagrid) {
            $.messager.confirm('确认', '您真的要删除此用户吗?', function (r) {
                if (r) {
                    $.ajax({
                        url: 'eers/oldinfo/' + record.oldid,
                        type: 'delete',
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
                    })
                }
            });

        }

        var view = function (record, dg) {
            DispatcherPanel.open('text!views/SampleForm.htm', 'views/SampleForm',
                {
                    ptype: 0, title: '查看: ' ,
                    record: record,
                    dg: dg,
                    cacheFn: function () {
                        return "helloworld";
                    }
                });
        }


        var module = {
            render: function (local, option) {
                var tb = $(local).find('div[tb]');
                var dg = MakeDG.make(local.find('.easyui-datagrid-noauto'),
                    {mydelete: mydelete, update: view, view: view},
                    {
                        url: HvitFrontFWPrefix+ 'eers/nine-table',
                        toolbar: tb
                    }
                );


                _.each(['oldname', 'oldcardnum', 'oldtelnum'], function (item) {
                    tb.find('.easyui-textbox[opt=' + item + ']').textbox({
                        onChange: function (newValue, oldValue) {
                            /*dg.datagrid('reload',{
                             intelligentsearch:[
                             {name:item,operate:"like",value:"%"+newValue+"%"}
                             ]
                             });*/
                            tb.find('a[action=search]').trigger('click');
                        }
                    })
                })


                var getSearchParams = function () {
                    var searchParams = [];
                    var oldname = tb.find('[opt=oldname]').textbox('getValue');
                    var oldcardnum = tb.find('[opt=oldcardnum]').textbox('getValue');
                    var oldtelnum = tb.find('[opt=oldtelnum]').textbox('getValue');
                    var oldsex = tb.find('[opt=oldsex]').combobox('getValue');
                    var oldtype = tb.find('[opt=oldtype]').combobox('getValue');
                    if (oldname) {
                        searchParams.push({name: 'oldname', operate: "like", value: "%" + oldname + "%"});
                    }
                    if (oldcardnum) {
                        searchParams.push({name: 'oldcardnum', operate: "like", value: "%" + oldcardnum + "%"});
                    }
                    if (oldtelnum) {
                        searchParams.push({name: 'oldtelnum', operate: "like", value: "%" + oldtelnum + "%"});
                    }
                    if (oldsex) {
                        searchParams.push({name: 'oldsex', operate: "=", value: oldsex});
                    }
                    if (oldtype) {
                        searchParams.push({name: 'oldtype', operate: "=", value: oldtype});
                    }
                    return searchParams;
                }

                //点击查询
                tb.find('a[action=search]').bind('click', function () {
                    dg.datagrid('reload', {
                        intelligentsearch: getSearchParams()
                    });
                });

                //新增内联式
                tb.find('a[action=new-inline]').bind('click', function () {
                    DispatcherPanel.open('text!views/SampleForm.htm', 'views/SampleForm',
                        {
                            ptype: DispatcherPanel.PANELINLINE,
                            width:1000,
                            title: '新增XX信息',
                            dg: dg
                        });
                })

                //新增层式
                tb.find('a[action=new-layer]').bind('click', function () {
                    DispatcherPanel.open('text!views/SampleForm.htm', 'views/SampleForm',
                        {
                            ptype: DispatcherPanel.PANELLAYER,
                            width:1000,
                            title: '新增XX信息',
                            dg: dg
                        });
                })

                //层式弹出自己
                tb.find('a[action=recur-layer]').bind('click', function () {
                    DispatcherPanel.open('text!views/Grid.htm', 'views/Grid',
                        {
                            ptype: DispatcherPanel.PANELLAYER,
                            width:1000,
                            title: '列表'
                        });
                })

                //点击关闭
                tb.find('a[action=close]').bind('click', function () {
                    local.trigger('MyClose');
                });
                //点击导出Excel
                tb.find('a[action=excel-data]').bind('click', function () {
                    var ooo = getSearchParams();
                    var href = 'eers/oldinfo-export?' + $.param({
                            customtag: 'nopagination',
                            intelligentsearch: ooo,
                            version: new Date().getTime()
                        });
                    document.location.href = href;
                });
            }
        };

        return module;
    });
