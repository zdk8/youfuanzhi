package cn.com.hvit.workspace.dao;


import cn.com.hvit.workspace.model.XtRoleFunc;

public interface XtRoleFuncMapper {
    int deleteByPrimaryKey(Integer rolefuncid);

    int insert(XtRoleFunc record);

    int insertSelective(XtRoleFunc record);

    XtRoleFunc selectByPrimaryKey(Integer rolefuncid);

    int updateByPrimaryKeySelective(XtRoleFunc record);

    int updateByPrimaryKey(XtRoleFunc record);

    void deleteByRoleId(Integer roleid);
}