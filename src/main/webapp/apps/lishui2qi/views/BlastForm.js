define(['underscore'], function (_) {
    //审核状态枚举
        var reviewStatus={
            NORMAL:1,
            COMMITED:2,
            REVIEWED:3,
            FAILED:4
        };
    var data=cj.getEnum('reviewstatus');
    var getReviewStatusChinese=function (value) {
        for(var i in data) {
            if(value==data[i]['value']){
                return data[i];
            }
        }
    };
    var tpl = _.template('<a><%=reviewstatus%></a>&nbsp;意见:<a><%=review%></a>');
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
                            cb.params.dg.datagrid('reload');
                            //refreshGrid();
                        } else {
                            $.messager.alert('提示', '<p>表单验证失败</p><p>请核实数据再进行提交操作</p>', 'error');
                        }
                    }
                })
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

                var mytr=local.find('tr[opt=shenhe-tr]');
                mytr.hide();
                if(reviewStatus.NORMAL<record.reviewstatus) {
                    mytr.show();
                    mytr.find('[opt=shenhe-result]').append(tpl({reviewstatus:getReviewStatusChinese(record.reviewstatus)['text'],review:record.review||'无'}));
                }
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