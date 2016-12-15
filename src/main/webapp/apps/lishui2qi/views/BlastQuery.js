define([cj.getModuleJs('widget/MakeDG'), cj.getModuleJs('widget/DispatcherPanel'), 'backbone','MyConfirm'],
    function (MakeDG, DispatcherPanel, Backbone,MyConfirm) {

        //审核状态枚举
        var reviewStatus={
            NORMAL:1,
            COMMITED:2,
            REVIEWED:3,
            FAILED:4
        };
        var mydelete = function (record, datagrid) {
            $.messager.confirm('确认', '您真的要删除此备案信息吗?<br>' + record.applyunit, function (r) {
                if (r) {
                    $.ajax({
                        url: 'delblast?b_id=' + record.bId,
                        type: 'get',
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
                            datagrid.datagrid('reload');
                        }, error: function () {
                            $.messager.alert('错误', '操作失败', 'error');
                        }
                    })
                }
            });
        };

        var mycommit = function (record, datagrid) {
            MyConfirm.confirm('确认', '您真的要提交此备案信息吗?<br>' + record.applyunit,
                {
                    url: 'blastcommit',
                    type: 'post',
                    data: $.extend({reviewstatus: reviewStatus.COMMITED, b_id: record.bId}, record),
                }, function () {
                    datagrid.datagrid('reload');
                });
        };
        var update = function (record, dg) {
            DispatcherPanel.open('text!views/BlastForm.htm', 'views/BlastForm',
                {
                    ptype: DispatcherPanel.PANELLAYER,
                    title: '更新 爆破备案',
                    record: record,
                    dg: dg,
                    height: 490
                });
        };
        var view = function (record, dg) {
            DispatcherPanel.open('text!views/BlastFormViewOnly.htm', 'views/BlastForm',
                {
                    ptype: DispatcherPanel.PANELLAYER,
                    title: '查看 爆破备案',
                    record: record,
                    dg: dg,
                    height: 490
                });
        };

        var shenhe = function (record, dg) {
            DispatcherPanel.open('text!views/BlastFormShenHe.htm', 'views/BlastFormShenHe',
                {
                    ptype: DispatcherPanel.PANELLAYER,
                    title: '审核 爆破备案',
                    record: record,
                    dg: dg,
                    height: 550
                });
        };


        var module = {
            render: function (local, option) {
                var reviewstatusArray=[];
                var tb = $(local).find('div[tb]');

                var dg = MakeDG.make(local.find('.easyui-datagrid-noauto'),
                    {mydelete: mydelete, view: view, update: update,mycommit:mycommit,shenhe:shenhe},
                    {
                        url: 'getblast',
                        rowLoadCallBack:function (row,index) {
                            reviewstatusArray.push($.extend({index: index}, row));
                        },
                        afterLoadSuccessCallback:function () {
                            local.find('li[action="update"]').hide();
                            local.find('li[action="mycommit"]').hide();
                            local.find('li[action="shenhe"]').hide();

                            for(var i=0;i<reviewstatusArray.length;i++) {
                                var record = reviewstatusArray[i];
                                if(record.reviewstatus==reviewStatus.NORMAL) {
                                    local.find('li[action="mycommit"]').eq(record.index).show();
                                }else if(record.reviewstatus==reviewStatus.COMMITED) {
                                    local.find('li[action="shenhe"]').eq(record.index).show();
                                }else if(record.reviewstatus==reviewStatus.FAILED) {
                                    local.find('li[action="update"]').eq(record.index).show();
                                    local.find('li[action="mycommit"]').eq(record.index).show();
                                }
                            }


                            //清空
                            reviewstatusArray=[];
                        },
                        toolbar: tb
                    }
                );


                _.each(['applicant', 'applyunit'], function (item) {
                    tb.find('.easyui-textbox[opt=' + item + ']').textbox({
                        onChange: function (newValue, oldValue) {
                            dg.datagrid('reload', {
                                intelligentsearch: [
                                    {name: item, operate: "like", value: "%" + newValue + "%"}
                                ]
                            });
                            tb.find('a[action=search]').trigger('click');
                        }
                    })
                })


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
                tb.find('a[action=new-layer]').bind('click', function () {
                    DispatcherPanel.open('text!views/BlastForm.htm', 'views/BlastForm',
                        {
                            ptype: DispatcherPanel.PANELLAYER,
                            width: 600,
                            height: 480,
                            title: '新增 爆破备案',
                            dg: dg
                        });
                });


                //点击关闭
                tb.find('a[action=close]').bind('click', function () {
                    local.trigger('MyClose');
                });

            }
        };

        return module;
    });
