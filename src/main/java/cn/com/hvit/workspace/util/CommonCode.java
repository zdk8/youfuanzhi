package cn.com.hvit.workspace.util;

import cn.com.hvit.workspace.model.Ls_Log;
import cn.com.hvit.workspace.model.Ls_User;
import cn.com.hvit.workspace.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/10.
 */
public class CommonCode {

    @Autowired
    private static ILogService logService;

    /**
     * 日志增加的静态方法，（未实现单例）
     * @param request
     * @param message
     */
    public static void addLog(HttpServletRequest request, String message){
        Ls_User user = (Ls_User) request.getSession().getAttribute("user");
        Ls_Log log = new Ls_Log();
        log.setUserid(user.getUserid());
        log.setLogcontent(user.getUsername()+message);
        logService.addLog(log);
    }

    /**
     * ping IP是否通畅的方法
     * @param ipAddress
     * @param pingTime
     * @param timeout
     * @return
     */
    public boolean ping(String ipAddress,int pingTime, int timeout){
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        String pingCommand = "ping " + ipAddress + " -n " + pingTime + " -w " +timeout;
        try {
            System.out.println(pingCommand);
            Process p = r.exec(pingCommand);
            if (p == null){
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int connectecCount = 0;
            String line = null;
            while((line = in.readLine()) !=null){
                connectecCount += getCheckResult(line);
            }
            return connectecCount == pingTime;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int getCheckResult(String line) {
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()){
            return 1;
        }
        return 0;
    }
}
