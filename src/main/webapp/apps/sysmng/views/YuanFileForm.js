define(['jqueryform'], function () {
    return {
        render: function (local, cb) {
            var poplocal = local;
            var submitbtn = local.find('[action=save]');
            var record = cb.params.record;
            var saveOrUpdateUrl = 'addblast';
            var attachmentResults = [];//附件结果表
            var fileUploadCallback=cb.params.fileUploadCallback;
            var attachmentForm=poplocal.find('form[name=attachment]').ajaxForm({
                    complete: function (xhr) {
                        attachmentResults = $.parseJSON(xhr.responseText);
                        if(attachmentResults.success==true) {
                            if(fileUploadCallback){
                                fileUploadCallback();
                            }
                            local.trigger('MyClose');
                             $.messager.show({
                                title: '提示',
                                msg: '操作成功',
                                showType: 'show',
                                timeout: 1000,
                                style: {
                                    right: '',
                                    bottom: ''
                                }
                            });
                        }else{
                            $.messager.alert('提示', '<p>上传失败</p>', 'error');
                        }

                    }
                });

            if (record) {
                saveOrUpdateUrl = 'updateblast';
            }
            $(submitbtn).bind('click', function () {
                attachmentForm.submit();
            });


        }


    }
});