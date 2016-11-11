package cn.com.hvit.workspace.util;

import cn.com.hvit.workspace.model.Ls_Log;
import cn.com.hvit.workspace.model.Ls_User;
import cn.com.hvit.workspace.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/11/10.
 */
public class CommonCode {

    @Autowired
    private static ILogService logService;

    public static void addLog(HttpServletRequest request, String message){
        Ls_User user = (Ls_User) request.getSession().getAttribute("user");
        Ls_Log log = new Ls_Log();
        log.setUserid(user.getUserid());
        log.setLogcontent(user.getUsername()+message);
        logService.addLog(log);
    }
}
