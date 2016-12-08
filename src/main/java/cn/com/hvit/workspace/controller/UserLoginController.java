package cn.com.hvit.workspace.controller;


import cn.com.hvit.workspace.model.Ls_Log;
import cn.com.hvit.workspace.model.Ls_User;
import cn.com.hvit.workspace.model.UserXt;
import cn.com.hvit.workspace.model.Xt_user;
import cn.com.hvit.workspace.service.ILogService;
import cn.com.hvit.workspace.service.IUserService;
import cn.com.hvit.workspace.util.CommonCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/9.
 */
@Controller
public class UserLoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ILogService logService;

    /**
     * 登录
     * @param useraccount
     * @param userpwd
     * @param request
     * @param response
     * @return 登录结果
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
//    @SystemLog(module = "用户管理",methods = "用户登录")
    public Map<String, Object> userLogin(@RequestParam String useraccount, @RequestParam String userpwd, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> userMap = new HashMap<String, Object>();
//        Ls_User getUser = userService.userLogin(useraccount, userpwd);
//
//        if (getUser != null) {
//            //保存登录结果
//            userMap.put("success", true);
//            userMap.put("message", getUser);
//            request.getSession().setAttribute("user", getUser);//将用户信息添加到session中
//            //将登录信息添加到日志表中
//            Ls_Log log = new Ls_Log();
//            log.setUserid(getUser.getUserid());
//            log.setLogcontent(getUser.getUsername()+"登录系统");
//            log.setClientip(new CommonCode().getClientIp(request));
//            logService.addLog(log);
////            CommonCode.addLog(request,"登录系统");//增加日志信息
//            return userMap;
//        } else {
//            userMap.put("success", false);
//            userMap.put("message", getUser);
//            return userMap;
//        }

        UserXt xtUser = userService.userxtLogin(useraccount, userpwd);
        if (xtUser != null){
            //保存登录结果
            userMap.put("success", true);
            userMap.put("message", xtUser);
            request.getSession().setAttribute("user", xtUser);//将用户信息添加到session中
            return userMap;
        }else {
            userMap.put("success", false);
            userMap.put("message", xtUser);
            return userMap;
        }
    }

    /**
     * 注销登录
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> userLoginOut(HttpServletRequest request, HttpServletResponse response) {
        Map<String,Object> logoutMap = new HashMap<String, Object>();
//        Ls_User user = (Ls_User) request.getSession().getAttribute("user");           //获取session中用户信息
//        //将注销信息添加到日志表中
//        Ls_Log log = new Ls_Log();
//        log.setUserid(user.getUserid());
//        log.setLogcontent(user.getUsername()+"注销登录");
//        logService.addLog(log);
        CommonCode code = new CommonCode();
        code.addLog(logService,request,"注销登录");//增加日志信息

        request.getSession().removeAttribute("user");//注销session中用户信息
        logoutMap.put("success",true);
        logoutMap.put("message" , "注销成功" + request.getRemoteAddr() );

        return logoutMap;
    }

    /**
     * 新增用户，判断输入的用户名是否存在
     * @param user
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/adduser", method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> addUser(Ls_User user, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> userMap = new HashMap<String,Object>();

        Ls_User checkUser = userService.getUserByName(user.getUseraccount());
        if (checkUser != null){
            userMap.put("success",false);
            userMap.put("message","该用户名已经存在");
            return userMap;
        }else {
            userService.addUser(user);
//            CommonCode.addLog(request,"新增用户");//增加日志信息
            userMap.put("success",true);
            userMap.put("message","新用户增加成功");
            return userMap;
        }

    }

    /**
     * 用户信息更新，判断新输入的用户名是否存在
     * @param user
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateuser", method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> updateUser(Ls_User user, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> userMap = new HashMap<String,Object>();

        Ls_User checkUser = userService.getUserByName(user.getUseraccount());
        if (checkUser != null){
            userMap.put("success",false);
            userMap.put("message","该用户名已经存在");
            return userMap;
        }else {
            userService.addUser(user);
//            CommonCode.addLog(request,"更新用户");//增加日志信息
            userMap.put("success",true);
            userMap.put("message","用户更新成功");
            return userMap;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/urltest", method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> testURL(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> userMap = new HashMap<String,Object>();

        userMap.put("success",true);
        userMap.put("message","接口测试");
        return userMap;
    }

    @RequestMapping(value = {"/testjkpage"}, method = RequestMethod.GET)
    public String home() {
        return "testjkpage";
    }

}
