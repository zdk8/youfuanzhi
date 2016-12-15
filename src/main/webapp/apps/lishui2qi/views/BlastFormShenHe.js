define([], function () {

    //审核状态枚举
        var reviewStatus={
            NORMAL:1,
            COMMITED:2,
            REVIEWED:3,
            FAILED:4
        };

    return {

        render: function (local, cb) {
            var poplocal = local;
            var submitbtn = local.find('[action=save]');
            var failbtn = local.find('[action=fail]');
            var record = cb.params.record;

            var saveOrUpdateUrl =null;// 'addblast';
            if (record) {
                saveOrUpdateUrl = 'blastreview';
            }

            var submitForm=function () {
                poplocal.find('form').form('submit', {
                    url: saveOrUpdateUrl,
                    onSubmit: function (param) {
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            $.messager.progress('close');
                        }
                        return isValid;
                    },
                    success: function (data) {
                        var obj = JSON.parse(data);
                        if (obj.success) {
                            local.trigger('MyClose');
                            cb.params.dg.datagrid('reload');
                            //refreshGrid();
                        } else {
                            $.messager.alert('提示', '<p>表单验证失败</p><p>请核实数据再进行提交操作</p>', 'error');
                        }
                    }
                })
            };

            //审核不通过
            failbtn.bind('click',function () {
                local.find('input[name=reviewstatus]').val(reviewStatus.FAILED);
                local.find('input[name=issuccess]').val(0);
                submitForm();
            });
            //审核通过
            $(submitbtn).bind('click', function () {
                local.find('input[name=reviewstatus]').val(reviewStatus.REVIEWED);
                local.find('input[name=issuccess]').val(1);
                submitForm();
            });


            if (record) {
                record['b_id'] = record.bId;
                record = $.extend({}, record);
                _.each(['blasttime', 'formdate'], function (element) {
                    var t = record[element];
                    if (t) {
                        record[element] = new Date(t).pattern('yyyy-MM-dd HH:mm:ss');
                    }
                });
                poplocal.find('form').form('load', record);

                var inputDisabledArray = [""];
                for(var i in inputDisabledArray) {
                    
                }
            }
        }


    }
});