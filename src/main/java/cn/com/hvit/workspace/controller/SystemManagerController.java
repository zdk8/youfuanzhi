package cn.com.hvit.workspace.controller;


import cn.com.hvit.framework.kon.util.Global;
import cn.com.hvit.workspace.model.XtFunction;
import cn.com.hvit.workspace.model.XtRole;
import cn.com.hvit.workspace.model.XtUser;
import cn.com.hvit.workspace.service.SystemManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SystemManagerController {


    @Autowired
    SystemManagerService systemManagerService;


    @ResponseBody
    @RequestMapping(value = "sys/query-division-tree", method = {RequestMethod.GET, RequestMethod.POST})
    public Object getDivisionByGetOrPost(@RequestParam(value = "node", required = false) String node,
                                         @RequestParam(value = "id", required = false) String id,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (node == null) {
            node = id;
        }

        /* 默认 000000 浙江省 ,331100 丽水市 */
        if (node == null) {
            node = "330000";
            //{"dvrank":2,"state":"closed","id":"331100","text":"丽水市","dvname":"丽水市","leaf":true,"totalname":"丽水市","dvcode":"331100"}
            //默认请求返回丽水一条信息，前台需要
            Map lishuisheeInfo = new HashMap();
            lishuisheeInfo.put("dvrank", 2);
            lishuisheeInfo.put("state", "closed");
            lishuisheeInfo.put("id", "331100");
            lishuisheeInfo.put("text", "丽水市");
            lishuisheeInfo.put("dvname", "丽水市");
            lishuisheeInfo.put("totalname", "丽水市");
            lishuisheeInfo.put("dvcode", "331100");
            lishuisheeInfo.put("leaf", true);

            List list = new ArrayList();
            list.add(lishuisheeInfo);
            return list;
        }


        return systemManagerService.getSubDivisions(node);
    }


    /**
     * 根据当前登录用户，查询用户权限菜单树
     *
     * @param node
     * @param id
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "menutree", method = RequestMethod.GET)
    public List getMenuTree(@RequestParam(value = "node", required = false) String node,
                            @RequestParam(value = "id", required = false) String id,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map params = new HashMap();
        params.put("loginname", Global.getCurrentUser(request).getLoginname());
        String functionid = id;
        if (null == functionid) {
            functionid = node;
        }
        if (null == functionid) {
            functionid = "businessmenu";
        }
        params.put("functionid", functionid);
        return systemManagerService.menuTree(params);
    }


    /**
     * 功能管理中，查询所有的菜单
     *
     * @param node
     * @param id
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "allmenutree", method = {RequestMethod.GET, RequestMethod.POST})
    public List getAllMenuTree(@RequestParam(value = "node", required = false) String node,
                               @RequestParam(value = "id", required = false) String id,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        String functionid = id;
        if (null == functionid) {
            functionid = node;
        }
        if (null == functionid) {
            functionid = "totalroot";
        }
        return systemManagerService.allMenuTree(functionid);
    }

    /**
     * 根据菜单id查询菜单详细信息
     *
     * @param node
     * @param id
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getFunctionById", method = {RequestMethod.GET})
    public XtFunction getFunctionById(@RequestParam(value = "node", required = false) String node,
                                      @RequestParam(value = "id", required = false) String id,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        String functionid = id;
        if (null == functionid) {
            functionid = node;
        }
        if (null == functionid) {
            functionid = "totalroot";
        }
        return systemManagerService.getFunctionById(functionid);
    }

    /**
     * 保存功能信息
     *
     * @param xtFunction
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "sys/save-function", method = {RequestMethod.POST})
    public Map saveFunction(XtFunction xtFunction,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (xtFunction.getOrderno() == null) {
            xtFunction.setOrderno((short) 99);
        }
        systemManagerService.saveFunction(xtFunction);
        Map resultMap = new HashMap();
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 删除功能
     *
     * @param functionid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "sys/delete-function-by-id", method = {RequestMethod.POST})
    public Map delFunctionById(@RequestParam String functionid,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        systemManagerService.delFunctionById(functionid);
        Map resultMap = new HashMap();
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 保存或者更新系统用户信息
     *
     * @param xt_user
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "sys/save-user", method = {RequestMethod.POST})
    public Map saveuser(XtUser xt_user,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        XtUser user = xt_user;
        if (user.getUserid() != null && user.getUserid().trim().length() != 0) {
            systemManagerService.updateUser(xt_user);
        } else {
            systemManagerService.saveUser(xt_user);
        }


        Map resultMap = new HashMap();
        resultMap.put("success", true);
        return resultMap;
    }

    /**
     * 帐户设置
     *
     * @param xt_user
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "sys/person-setting", method = {RequestMethod.POST})
    public Map persontSetting(XtUser xt_user,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        XtUser user = xt_user;
        XtUser sessionUser = Global.getCurrentUser(request);
        if (sessionUser != null) {
            user.setUserid(sessionUser.getUserid());
            user.setUseful(sessionUser.getUseful());
            systemManagerService.updateUser(xt_user);
            sessionUser.setUsername(user.getUsername());
        }

        Map resultMap = new HashMap();
        resultMap.put("success", true);
        return resultMap;
    }


    @ResponseBody
    @RequestMapping(value = "sys/delete-user", method = {RequestMethod.POST})
    public Map deleteUser(String id,
                          HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        systemManagerService.deleteUserById(id);
        Map resultMap = new HashMap();
        resultMap.put("success", true);
        return resultMap;
    }


    @ResponseBody
    @RequestMapping(value = "sys/query-user-by-regionid", method = {RequestMethod.GET})
    public List getuserbyregionid(@RequestParam(value = "node", required = false) String regionid,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (regionid == null || "330000".equals(regionid)) {
            regionid = "33";
        }
        return systemManagerService.getUserByRegionId(regionid);
    }


    @ResponseBody
    @RequestMapping(value = "sys/query-role-with-check", method = {RequestMethod.GET})
    public List getrole(@RequestParam(value = "userid", required = true) Integer userid,
                        @RequestParam(value = "rolename", required = false) String rolename,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        return systemManagerService.getRoles(userid, rolename);
    }

    @ResponseBody
    @RequestMapping(value = "sys/save-role", method = {RequestMethod.POST})
    public Map saverole(XtRole xtRole,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        systemManagerService.saveRole(xtRole);
        Map resultMap = new HashMap();
        resultMap.put("success", true);
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = "sys/delete-role-by-id", method = {RequestMethod.POST})
    public Map delrolebyid(@RequestParam(value = "id", required = true) Integer roleid,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        systemManagerService.deleteRoldById(roleid);
        Map resultMap = new HashMap();
        resultMap.put("success", true);
        return resultMap;
    }


    @ResponseBody
    @RequestMapping(value = "sys/query-grant-menu-tree", method = {RequestMethod.POST, RequestMethod.GET})
    public List grantmenutree(@RequestParam(value = "id", required = false) String functionid,
                              @RequestParam(value = "roleid", required = true) Integer roleid,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map> list = systemManagerService.selectPrivilegesByRole(roleid, functionid);

        for (Map map : list) {
            System.out.println(map);
        }
        return list;
    }


    @ResponseBody
    @RequestMapping(value = "sys/save-grant-menu-to-role", method = {RequestMethod.POST})
    public Map savegrant(@RequestParam(value = "functionids", required = true) String functionids,
                         @RequestParam(value = "roleid", required = true) Integer roleid,
                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        systemManagerService.savePrivilegesByRoleFunc(functionids, roleid);
        Map resultMap = new HashMap();
        resultMap.put("success", true);
        return resultMap;
    }


    @ResponseBody
    @RequestMapping(value = "sys/save-grant-role-to-user", method = {RequestMethod.POST})
    public Map saveroleuser(@RequestParam(value = "roleids", required = true) String roleids,
                            @RequestParam(value = "userid", required = true) Integer userid,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        systemManagerService.savePrivilegesByRoleUser(roleids, userid);
        Map resultMap = new HashMap();
        resultMap.put("success", true);
        return resultMap;
    }


}