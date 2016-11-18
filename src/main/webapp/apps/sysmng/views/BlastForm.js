define([], function () {

    return {

        render: function (local, cb) {
            var poplocal = local;
            var submitbtn = local.find('[action=save]');
            var record = cb.params.record;

            var saveOrUpdateUrl = 'addblast';
            if (record) {
                saveOrUpdateUrl = 'updateblast';
            }
            $(submitbtn).bind('click', function () {
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
                            //refreshGrid();
                        } else {
                            $.messager.alert('提示', '<p>表单验证失败</p><p>请核实数据再进行提交操作</p>', 'error');
                        }
                    }
                })
            });

            record['b_id']=record.bId;
            record = $.extend({}, record);
            _.each(['blasttime','formdate'],function (element) {
                var t=record[element];
                if(t) {
                    record[element]=new Date(t).pattern('yyyy-MM-dd HH:mm:ss');
                }
            });
            poplocal.find('form').form('load', record);
            if (record && record.b_id) {

            } else {
                //新增时初始化时间控件
                var currentTime = new Date().pattern('yyyy-MM-dd HH:mm:ss');
                poplocal.find('[opt=blasttime]').datetimebox('setValue', currentTime);
                poplocal.find('[opt=formdate]').datetimebox('setValue', currentTime);

                //初始化  爆破方法 选择1
                poplocal.find('form').form('load', {blastway: 1});
            }
        }


    }
});