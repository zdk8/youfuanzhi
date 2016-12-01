define([cj.getModuleJs('widget/MakeDG'), cj.getModuleJs('widget/DispatcherPanel'), 'backbone'],
    function (MakeDG, DispatcherPanel, Backbone) {

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

        var view = function (record, dg) {
            DispatcherPanel.open('text!views/BlastForm.htm', 'views/BlastForm',
                {
                    ptype: DispatcherPanel.PANELLAYER,
                    title: '更新 爆破备案',
                    record: record,
                    dg: dg,
                    height: 480
                });
        };


        var module = {
            render: function (local, option) {
                var tb = $(local).find('div[tb]');
                var dg = MakeDG.make(local.find('.easyui-datagrid-noauto'),
                    {mydelete: mydelete, update: view, view: view},
                    {
                        url: 'getblast',
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
