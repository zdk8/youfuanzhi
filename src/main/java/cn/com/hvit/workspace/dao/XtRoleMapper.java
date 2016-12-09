package cn.com.hvit.workspace.dao;


import cn.com.hvit.workspace.model.XtRole;

import java.util.List;
import java.util.Map;

public interface XtRoleMapper {
    int deleteByPrimaryKey(Integer roleid);

    int insert(XtRole record);

    int insertSelective(XtRole record);

    XtRole selectByPrimaryKey(Integer roleid);

    int updateByPrimaryKeySelective(XtRole record);

    int updateByPrimaryKey(XtRole record);

    List selectAll();

    /**
     * roleid,functionid
     * @param params
     * @return
     */
    List selectPrivilegesByRole(Map params);

    /**
     * 查询出所有的角色，并判断用户是否有此角色
     * userid,rolename
     * @param param
     * @return
     */
    List selectPrivilegesByUserIdAndRoleNameFilter(Map param);
}