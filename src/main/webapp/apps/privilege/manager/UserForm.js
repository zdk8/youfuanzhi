define([cj.getModuleJs('BackendInterfaceList')],function(BackendInterfaceList){

  return {

    render:function(local,cb){
      var poplocal=local;
      var submitbtn=local.find('[action=save]');
      console.log(cb);
      var record=cb.params.record;

      $(submitbtn).bind('click', function () {
        poplocal.find('form').form('submit', {
          url: BackendInterfaceList.getUrl('save-user').get('url'),
          onSubmit: function (param) {
            var isValid = $(this).form('validate');
            console.log(isValid);
            if (!isValid) {
              $.messager.progress('close');
            }
            if(!poplocal.find('[name=userid]').val()){
              param.flag=-1;
            }
            return isValid;
          },
          success: function (data) {
            var obj = $.evalJSON(data);
            if(obj.success) {
              //parent.trigger('close');
              //refreshGrid();
            }
          }
        })
      });
      if(record) {
        $.post('getuserbyid',{
          id:record.userid
        },function(data){
          poplocal.find('form').form('load', data);//加载数据到表单
          poplocal.find('[opt=tip]').text(data.totalname);
        },'json');
      };
    }


  }
})
