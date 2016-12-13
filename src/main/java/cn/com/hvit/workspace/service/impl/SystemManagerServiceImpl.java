package cn.com.hvit.workspace.service.impl;


import cn.com.hvit.framework.kon.util.DataSourceContextHolder;
import cn.com.hvit.framework.kon.util.KeyLowerMapUtil;
import cn.com.hvit.workspace.dao.*;
import cn.com.hvit.workspace.model.*;
import cn.com.hvit.workspace.service.SystemManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wp on 16-7-22.
 */

@Service
public class SystemManagerServiceImpl implements SystemManagerService {
    private final String defaultTargetDataSource = "defaultTargetDataSource";

    @Autowired
    private XtUserMapper userMapper;

    @Autowired
    private DivisionMapper divisionMapper;

    @Autowired
    private XtFunctionMapper xtFunctionMapper;

    @Autowired
    private XtRoleMapper xtRoleMapper;

    @Autowired
    private XtRoleFuncMapper xtRoleFuncMapper;

    @Autowired
    private XtRoleUserMapper xtRoleUserMapper;

    @Override
    public List findUserByLoginname(String loginname) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        Map map = new HashMap();
        map.put("loginname", loginname);
        return userMapper.findXt_user(map);
    }

    /**
     * 把list中map大写的键转换为小写
     *
     * @param list
     * @return
     */
    private List parse2LowerList(List<Map> list) {
        List<Map> resultList = new ArrayList<>();
        for (Map map : list) {
            resultList.add(KeyLowerMapUtil.parse(map));
        }
        return resultList;
    }

    @Override
    public List getSubDivisions(String dvhigh) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        List list = divisionMapper.selectSubDivision(dvhigh);
        List result = new ArrayList();
        for (Object o : list) {
            Map map = new HashMap();
            Division division = (Division) o;
            if (5 == division.getDvrank()) {
                map.put("leaf", true);
                map.put("state", "closed");
            } else {
                map.put("leaf", true);
                map.put("state", "closed");
            }
            map.put("id", division.getDvcode());
            map.put("dvcode", division.getDvcode());
            map.put("text", division.getDvname());
            map.put("dvname", division.getDvname());

            map.put("totalname", division.getTotalname());
            map.put("dvrank", division.getDvrank());
            result.add(map);
        }
        return result;
    }

    @Override
    public List menuTree(Map loginnameAndfunctionid) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        List resultList = new ArrayList();
        List list = xtFunctionMapper.selectFunctionByLoginnameAndFunctionid(loginnameAndfunctionid);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Map item = KeyLowerMapUtil.parse((Map) iterator.next());
            System.out.println(item);
            if (item.get("leaf").equals("true")) {
                item.put("state", "open");
                item.put("isParent", false);
                item.put("leaf", true);
            } else {
                item.put("state", "closed");
                item.put("isParent", true);
                item.put("leaf", false);
            }
            item.put("name", item.get("title"));
            resultList.add(KeyLowerMapUtil.parse(item));
        }
        return resultList;
    }

    @Override
    public List allMenuTree(String functionid) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        List resultList = new ArrayList();
        List list = xtFunctionMapper.selectSubFunctions(functionid);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Map item = KeyLowerMapUtil.parse((Map) iterator.next());
            System.out.println(item);
            if (item.get("leaf").equals("true")) {
                item.put("state", "open");
                item.put("isParent", false);
                item.put("leaf", true);
            } else {
                item.put("state", "closed");
                item.put("isParent", true);
                item.put("leaf", false);
            }
            item.put("name", item.get("title"));
            resultList.add(KeyLowerMapUtil.parse(item));
        }
        return resultList;

    }

    @Override
    public XtFunction getFunctionById(String functionid) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        return xtFunctionMapper.selectByPrimaryKey(functionid);
    }

    @Override
    public void saveFunction(XtFunction record) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        if (record.getFunctionid() == null ||
                record.getFunctionid() != null && record.getFunctionid().trim().equals("")) {
            xtFunctionMapper.insert(record);
        } else {
            xtFunctionMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public void delFunctionById(String functionid) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        xtFunctionMapper.deleteByPrimaryKey(functionid);
    }


    @Override
    public void saveUser(XtUser user) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        userMapper.insert(user);
    }

    @Override
    public void updateUser(XtUser user) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void deleteUserById(String userid) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        userMapper.deleteByPrimaryKey(Short.valueOf(userid));
    }

    @Override
    public List getUserByRegionId(String regionid) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        return parse2LowerList(userMapper.findUserByRegionId(regionid));
    }


    @Override
    public List getRoles() {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        return xtRoleMapper.selectAll();
    }

    @Override
    public List getRoles(Integer userid, String rolename) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        Map params = new HashMap();
        params.put("userid", userid);
        params.put("rolename", rolename);
        return parse2LowerList(xtRoleMapper.selectPrivilegesByUserIdAndRoleNameFilter(params));
    }

    @Override
    public void saveRole(XtRole role) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);

        if (role.getRoleid() != null) {
            xtRoleMapper.updateByPrimaryKeySelective(role);
        } else {
            if (null == role.getStatus()) {
                role.setStatus("1");
            }
            xtRoleMapper.insert(role);
        }
    }

    @Override
    public void deleteRoldById(Integer roleid) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        xtRoleMapper.deleteByPrimaryKey(roleid);
    }

    @Override
    public List selectPrivilegesByRole(Integer roleid, String functionid) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        Map params = new HashMap();
        params.put("roleid", roleid);
        if (null == functionid) {
            functionid = "totalroot";
        }
        params.put("functionid", functionid);

        List<Map> list = parse2LowerList(xtRoleMapper.selectPrivilegesByRole(params));

        for (Map item : list) {
            System.out.println(item.get("checked"));

            if (item.get("leaf").equals("true")) {
                item.put("leaf", true);
                item.put("state", "open");
            } else {
                item.put("leaf", false);
                item.put("state", "closed");
            }
        }
        return list;
    }

    @Override
    public void savePrivilegesByRoleFunc(String functionids, Integer roleid) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
//        xtRoleFuncMapper.deleteByPrimaryKey(roleid);
        xtRoleFuncMapper.deleteByRoleId(roleid);
        String[] functionidArray = functionids.split(",");
        for (String functionid : functionidArray) {
            XtRoleFunc roleFunc = new XtRoleFunc();
            roleFunc.setFunctionid(functionid);
            roleFunc.setRoleid(roleid);
            xtRoleFuncMapper.insert(roleFunc);
        }
    }

    @Override
    public void savePrivilegesByRoleUser(String roleids, Integer userid) {
        DataSourceContextHolder.setDbType(defaultTargetDataSource);
        xtRoleUserMapper.deleteByUserId(userid);
        String[] roleidArray = roleids.split(",");
        for (String roleid : roleidArray) {
            XtRoleUser roleUser = new XtRoleUser();
            roleUser.setRoleid(Integer.parseInt(roleid));
            roleUser.setUserid(userid);
            xtRoleUserMapper.insert(roleUser);
        }
    }

}
