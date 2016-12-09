define([cj.getModuleJs('BackendInterfaceList')],function(BackendInterfaceList){

  return {

    render:function(local,cb){
      var poplocal=local;
      var submitbtn=local.find('[action=save]');
      var record=cb.params.record;

      $(submitbtn).bind('click', function () {
        poplocal.find('form').form('submit', {
          url: BackendInterfaceList.getUrl('save-role').get('url'),
          onSubmit: function (param) {
            var isValid = $(this).form('validate');
            if (!isValid) {
              $.messager.progress('close');
            }
            if(!poplocal.find('[name=userid]').val()){
              param.flag=-1;
            }
            return isValid;
          },
          success: function (data) {
            var obj = JSON.parse(data);
            if(obj.success) {
              local.trigger('MyClose');
              //refreshGrid();
            }else{
              $.messager.alert('提示','<p>表单验证失败</p><p>请核实数据再进行提交操作</p>','error');
            }
          }
        })
      });

      poplocal.find('form').form('load', record);

    }


  }
})
