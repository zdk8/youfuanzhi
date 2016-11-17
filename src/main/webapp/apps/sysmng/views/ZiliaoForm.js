define(['jqueryform'], function () {
    return {
        render: function (local, cb) {
            var poplocal = local;
            var submitbtn = local.find('[action=save]');
            var record = cb.params.record;
            var saveOrUpdateUrl = 'addblast';

            poplocal.find('form[name=attachment]').ajaxForm({
                    complete: function (xhr) {
                        var results = $.parseJSON(xhr.responseText);
                        console.log(results);
                    }
                });

            if (record) {
                saveOrUpdateUrl = 'updateblast';
            }
            $(submitbtn).bind('click', function () {

                /*poplocal.find('form[name=normal]').form('submit', {
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
                });*/

            });

            poplocal.find('form[name=normal]').form('load', record);
            if (record && record.b_id) {
                $.post('getuserbyid', {
                    id: record.userid
                }, function (data) {
                    poplocal.find('form[name=normal]').form('load', data);//加载数据到表单
                    poplocal.find('[opt=tip]').text(data.totalname);
                }, 'json');
            } else {
                //新增时初始化时间控件
                var currentTime = new Date().pattern('yyyy-MM-dd HH:mm:ss');
                poplocal.find('[opt=blasttime]').datetimebox('setValue', currentTime);
                poplocal.find('[opt=formdate]').datetimebox('setValue', currentTime);
            }


            //end
        }


    }
});