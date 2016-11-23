package cn.com.hvit.workspace.util;

import cn.com.hvit.workspace.model.Ls_Log;
import cn.com.hvit.workspace.model.Ls_User;
import cn.com.hvit.workspace.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/10.
 */
public class CommonCode {


    /**
     * 日志增加的静态方法，（未实现单例）
     * @param request
     * @param message
     */
    public void addLog(ILogService logService,HttpServletRequest request, String message){
        Ls_User user = (Ls_User) request.getSession().getAttribute("user");
        Ls_Log log = new Ls_Log();
        log.setUserid(user.getUserid());
        log.setLogcontent(user.getUsername()+message);
        log.setClientip(getClientIp(request));
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
//            System.out.println(pingCommand);
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

    /**
     * 获取访问客户端的ip地址
     * @param request
     * @return
     */
    public String getClientIp(HttpServletRequest request){
        String ipAddress = null;
        //ipAddress = this.getRequest().getRemoteAddr();
        ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }

        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 查询列表条件封装,前端提交的name和数据库中的一致
     * @param request
     * @return
     */
    public HashMap<String,Object> condMap(HttpServletRequest request){
        HashMap<String,Object> condMap = new HashMap<String,Object>();
        int i = 0;
        String checktj = request.getParameter("intelligentsearch[" + i + "][name]");            //获取第一个字段名
        while(checktj != null){
            String name =  request.getParameter("intelligentsearch[" + i + "][name]");          //获取字段名
            String value =  request.getParameter("intelligentsearch[" + i + "][value]");        //获取字段信息
            condMap.put(name,value);                                                            //将字段信息和值存入条件的Map中
            i++;
            checktj = request.getParameter("intelligentsearch[" + i + "][name]");              //查找下一个字段信息
        }
        return condMap;
    }
}
