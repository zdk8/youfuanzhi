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
                        url: 'getfiles',
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
                tb.find('a[action=new-layer]').bind('click', function () {
                    DispatcherPanel.open('text!views/ZiliaoForm.htm', 'views/ZiliaoForm',
                        {
                            ptype: DispatcherPanel.PANELLAYER,
                            width:600,
                            height:300,
                            title: '新增 资料',
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
