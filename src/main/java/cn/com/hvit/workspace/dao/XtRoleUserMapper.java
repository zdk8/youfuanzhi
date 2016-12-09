package cn.com.hvit.workspace.dao;


import cn.com.hvit.workspace.model.XtRoleUser;

public interface XtRoleUserMapper {
    int deleteByPrimaryKey(Integer roleuserid);

    int insert(XtRoleUser record);

    int insertSelective(XtRoleUser record);

    XtRoleUser selectByPrimaryKey(Integer roleuserid);

    int updateByPrimaryKeySelective(XtRoleUser record);

    int updateByPrimaryKey(XtRoleUser record);

    void deleteByUserId(Integer userid);
}