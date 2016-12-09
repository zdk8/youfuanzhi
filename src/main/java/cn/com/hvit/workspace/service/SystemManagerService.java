package cn.com.hvit.workspace.service;



import cn.com.hvit.workspace.model.XtFunction;
import cn.com.hvit.workspace.model.XtRole;
import cn.com.hvit.workspace.model.XtUser;

import java.util.List;
import java.util.Map;

/**
 * Created by wp on 16-7-22.
 *
 * 系统管理模块总接口
 *
 */
public interface SystemManagerService {



    List findUserByLoginname(String loginname);

    List getSubDivisions(String dvhigh);


    List menuTree(Map loginnameAndfunctionid);

    List allMenuTree(String functionid);

    XtFunction getFunctionById(String functionid);

    void saveFunction(XtFunction record);

    void delFunctionById(String functionid);

    void saveUser(XtUser user);

    void updateUser(XtUser user);

    void deleteUserById(String userid);

    List getUserByRegionId(String regionid);


    List getRoles();

    List getRoles(Integer userid, String rolename);

    void saveRole(XtRole role);

    void deleteRoldById(Integer roleid);

    List selectPrivilegesByRole(Integer roleid, String functionid);

    void savePrivilegesByRoleFunc(String functionids, Integer roleid);

    void savePrivilegesByRoleUser(String roleids, Integer userid);
}
