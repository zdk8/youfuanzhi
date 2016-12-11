define([cj.getModuleJs('widget/MakeDG'), cj.getModuleJs('widget/DispatcherPanel'), 'backbone'],
    function (MakeDG, DispatcherPanel, Backbone) {

        var mydelete = function (record, datagrid) {
            $.messager.confirm('确认', '您真的要删除此消息吗?', function (r) {
                if (r) {
                    $.ajax({
                        url: 'delearthmsg',
                        data:{rmid:record.rmid},
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

        var view = function (record, dg) {
            DispatcherPanel.open('text!views/YuanResponseMessageForm.htm', 'views/YuanResponseMessageForm',
                {
                    ptype: DispatcherPanel.PANELLAYER,
                    title: '查看: ' ,
                    record: record,
                    dg: dg,
                    cacheFn22: function () {
                        return "helloworld";
                    }
                });
        };


        var module = {
            render: function (local, option) {
                var tb = $(local).find('div[tb]');
                var $yuanStatus=$('#yuan-status');
                var startNumber=0;
                var dg = MakeDG.make(local.find('.easyui-datagrid-noauto'),
                    {mydelete: mydelete, update: view, view: view},
                    {
                        url: 'getearthmsg',
                        rowStyler: function (index, row) {
                            if (row.status ==1) {
                                return 'background-color:#6293BB;color:rgb(179, 47, 47);';
                            }
                        },
                        rowLoadCallBack:function (row,index) {
                            if(row.status==1) {
                                $yuanStatus.attr('status', 'startup');
                                startNumber=index;
                            }
                        },
                        afterLoadSuccessCallback:function () {
                            local.find('li[action="jiechu"]').hide()
                            if($yuanStatus.attr('status')=='startup'){
                                local.find('li[action="startup"]').hide();
                                local.find('li[action="jiechu"]').eq(3).show();
                            }
                        },
                        toolbar: tb
                    }
                );


                _.each(['contact', 'telephone'], function (item) {
                    tb.find('.easyui-textbox[opt=' + item + ']').textbox({
                        onChange: function (newValue, oldValue) {
                            console.log('changed:' + newValue);
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
                    var contact = tb.find('[opt=contact]').textbox('getValue');
                    if (contact) {
                        searchParams.push({name: 'contact', operate: "like", value: "%" + contact + "%"});
                    }

                    var telephone = tb.find('[opt=telephone]').textbox('getValue');
                    if (telephone) {
                        searchParams.push({name: 'telephone', operate: "like", value: "%" + telephone + "%"});
                    }
                    
                    var rmlevel = tb.find('[opt=rmlevel-search]').combobox('getValues');
                    if (rmlevel) {
                        searchParams.push({name: 'rmlevel', operate: "=", value:  rmlevel + ""});
                    }
                    return searchParams;
                };

                //点击查询
                tb.find('a[action=search]').bind('click', function () {
                    console.log('clicked');
                    DD=dg;
                    dg.datagrid('reload', {
                        intelligentsearch: getSearchParams()
                    });
                });


                //新增层式
                tb.find('a[action=new-layer]').bind('click', function () {
                    DispatcherPanel.open('text!views/YuanResponseMessageForm.htm', 'views/YuanResponseMessageForm',
                        {
                            ptype: DispatcherPanel.PANELLAYER,
                            width:600,
                            title: '新增',
                            dg: dg
                        });
                });


                //点击关闭
                tb.find('a[action=close]').bind('click', function () {
                    local.trigger('MyClose');
                });


                var $rmlevel=tb.find('[opt=rmlevel]');
                $rmlevel.combobox('select', 3);


                var startup_jiechuStatusChange;
                //点击启动预案
               var $startup=
                   tb.find('a[action=startup]').bind('click', function () {
                    $.messager.confirm('确认', '您真的要启动预案吗?', function (r) {
                        if (r) {
                            $.ajax({
                                url: 'responsestart',
                                type: 'post',
                                data: {level: 3, yjid: 21},
                                success: function () {
                                    startup_jiechuStatusChange(true);
                                    $.messager.show({
                                        title: '提示',
                                        msg: '启动预案成功',
                                        showType: 'show',
                                        timeout: 500,
                                        style: {
                                            right: '',
                                            bottom: ''
                                        }
                                    });
                                }, error: function () {
                                    $.messager.alert('错误', '启动预案失败', 'error');
                                }
                            })
                        }
                    });
                });


                 //点击解除预案
                var $jiechu=
                tb.find('a[action=jiechu]').bind('click', function () {
                    $.messager.confirm('确认', '您真的要解除预案吗?', function (r) {
                        if (r) {
                            $.ajax({
                                url: 'responsestop',
                                type: 'post',
                                success: function () {
                                    startup_jiechuStatusChange(false);
                                    $.messager.show({
                                        title: '提示',
                                        msg: '解除预案成功',
                                        showType: 'show',
                                        timeout: 500,
                                        style: {
                                            right: '',
                                            bottom: ''
                                        }
                                    });
                                }, error: function () {
                                    $.messager.alert('错误', '解除预案失败', 'error');
                                }
                            })
                        }
                    });
                });

                startup_jiechuStatusChange=function (started) {
                    if(started==true) {
                        $jiechu.show();
                        $startup.hide();
                    }else{
                        $jiechu.hide();
                        $startup.show();
                    }
                };


                //未启动预案时的状态
                /*startup_jiechuStatusChange(false);
                $.get('getearthquake',function (resp) {
                    if(resp[0]['yjstatus']=='start'){
                        startup_jiechuStatusChange(true);
                    }

                    var $download=tb.find('a[opt=yuan-download]');
                    $download.attr('href',resp[0]['yjpath']+'?dlname='+resp[0]['yjpath'].split('/')[1]);
                    $download.text('下载');
                    
                });*/


                tb.find('a[action=yuan-file-upload]').bind('click', function () {
                    DispatcherPanel.open('text!views/YuanFileForm.htm', 'views/YuanFileForm',
                        {
                            ptype: DispatcherPanel.PANELLAYER,
                            width: 600,
                            height: 300,
                            title: '新增 预案资料',
                            dg: dg,
                            fileUploadCallback: function () {
                                $.get('getearthquake', function (resp) {
                                    var $download = tb.find('a[opt=yuan-download]');
                                    $download.attr('href', resp[0]['yjpath'] + '?dlname=' + resp[0]['yjpath'].split('/')[1]);
                                    $download.text('下载');
                                });
                            }
                        });
                });

                //多预案管理
                tb.find('a[action=multi-yuan]').bind('click', function () {
                    DispatcherPanel.open('text!views/MultiYuanQuery.htm', 'views/MultiYuanQuery',
                        {
                            ptype: DispatcherPanel.PANELLAYER,
                            width: 600,
                            title: '预案管理',
                            dg: dg
                        });
                });

            }
        };

        return module;
    });
