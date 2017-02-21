define([cj.getModuleJs('widget/MakeDG'), cj.getModuleJs('widget/DispatcherPanel'), 'backbone'],
    function (MakeDG, DispatcherPanel, Backbone) {




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

        var showdetail = function (record, dg) {
            DispatcherPanel.open('text!views/YuanResponseMessageQuery.htm', 'views/YuanResponseMessageQuery',
                {
                    ptype: DispatcherPanel.PANELLAYER,
                    title: '预案信息：'+record.yjname,
                    height:500,
                    width:900,
                    record: record,
                    dg: dg
                });
        }


        var module = {
            render: function (local, option) {
                var tb = $(local).find('div[tb]');
                tb.find('[opt=rmlevel]').combobox('setValue', 2);

                        var jiechu = function (record, datagrid) {
            $.messager.confirm('确认', '您真的要解除预案吗?', function (r) {
                if (r) {
                    $.ajax({
                        url: 'responsestop',
                        data: {level: record.level, yjid: record.yjid},
                        type: 'post',
                        success: function () {
                            datagrid.datagrid('reload');
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
        };

                        var startup = function (record, datagrid) {
            $.messager.confirm('确认', '您真的要启动预案吗?', function (r) {
                var level = tb.find('[opt=rmlevel]').combobox('getValue');
                if (r) {
                    $.ajax({
                        url: 'responsestart',
                        data: {level:(level||3),yjid:record.yjid},
                        type: 'post',
                        success: function () {
                            datagrid.datagrid('reload');
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
        };


                var startArray = [];
                var dg = MakeDG.make(local.find('.easyui-datagrid-noauto'),
                    {startup: startup, jiechu: jiechu,update: view, view: view,showdetail:showdetail},
                    {
                        url:'getearthquake',
                        //url: 'apps/lishui2qi/data/yuan-list.json',
                        rowStyler: function (index, row) {
                            if (row.status ==1) {
                                return 'color:rgb(179, 47, 47);';
                            }
                        },
                        rowLoadCallBack:function (row,index) {
                            if(row.yjstatus=='start') {
                                startArray.push($.extend({index:index},row));
                            }
                        },
                        afterLoadSuccessCallback:function () {
                            local.find('li[action="jiechu"]').hide();
                            for(var i=0;i<startArray.length;i++) {
                                var myRow = startArray[i];
                                local.find('li[action="startup"]').eq(myRow['index']).hide();
                                local.find('li[action="jiechu"]').eq(myRow['index']).show();
                            }

                            //hack背景设置
                            local.find('ul.hvit-pretty-buttons a.round-text-jiechu').css("background-image","url(images/icons/解除.png)");
                            local.find('ul.hvit-pretty-buttons a.round-text-startup').css("background-image","url(images/icons/启动.png)");
                            local.find('ul.hvit-pretty-buttons a.round-text').css('color', "white").css('text-decoration','none');
                            //清空
                            startArray = [];
                        },
                        toolbar: tb
                    }
                );


                _.each(['yjname', 'applyunit'], function (item) {
                    tb.find('.easyui-textbox[opt=' + item + ']').textbox({
                        onChange: function (newValue, oldValue) {
                            dg.datagrid('reload',{
                             intelligentsearch:[
                             {name:item,operate:"like",value:newValue}
                             ]
                             });
                            tb.find('a[action=search]').trigger('click');
                        }
                    })
                });


                var getSearchParams = function () {
                    var searchParams = [];
                    var yjname = tb.find('[opt=yjname]').textbox('getValue');
                    if (yjname) {
                        searchParams.push({name: 'yjname', operate: "like", value: "%" + yjname + "%"});
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
