package cn.com.hvit.workspace.util;

import cn.com.hvit.workspace.model.Ls_Log;
import cn.com.hvit.workspace.model.Ls_User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/11/10.
 */
public class CommonCode {


    public static Ls_Log getLog(HttpServletRequest request, String message){
        Ls_User user = (Ls_User) request.getSession().getAttribute("user");
        Ls_Log log = new Ls_Log();
        log.setUserid(user.getUserid());
        log.setLogcontent(user.getUsername()+message);
        return log;
    }
}
