import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.Ls_Blast;
import cn.com.hvit.workspace.model.Ls_Log;
import cn.com.hvit.workspace.service.IBlastService;
import cn.com.hvit.workspace.service.ILogService;
import cn.com.hvit.workspace.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/9.
 */
public class TdsTest {
    private IUserService userService;
    private ILogService logService;
    private IBlastService blastService;

    @Before
    public void before(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:conf/spring.xml","classpath:conf/spring-mybatis.xml");
        userService = (IUserService) context.getBean("userServiceImpl");
        logService = (ILogService) context.getBean("logServiceImpl");
        blastService = (IBlastService) context.getBean("blastServiceImpl");
    }

    @Test
    public void testUserLogin(){
        System.out.println(userService.getUserByName("aaa").getUseraccount());
    }

    @Test
    public void testLog(){
//        Ls_Log log = new Ls_Log();
//        log.setUserid(BigDecimal.valueOf(2));
//        log.setLogcontent("test log");
//        logService.addLog(log);
//        System.out.println(111);
//        List logs = logService.getLogs();
        PageHelper.Page<Ls_Log> logs = logService.getLogs(1, 10);
        System.out.println(logs.getResults().size());
    }

    @Test
    public void testBlast(){
        Ls_Blast blast = new Ls_Blast();
        blast.setApplicant("blast2");
        blast.setApplyunit("hvit2");
        blast.setWeight(BigDecimal.valueOf(200));
        blast.setbId(BigDecimal.valueOf(21));
//        blastService.addBlast(blast);
//        System.out.println("success add");
//        blastService.updateBlast(blast);
        System.out.println("update success");

        PageHelper.Page<Ls_Blast> blasts = blastService.getBlastByCond(1, 10,new HashMap<String, Object>());
        System.out.println(blasts.getResults().size());

    }

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

    @Test
    public void testPing(){
        //第一种方法:Jdk1.5的InetAddresss,代码简单。 测试时间为1000时，能ping通的ip也会返回false
//        int timeOut = 1000;
//        try {
//            boolean reachable = InetAddress.getByName("192.168.1.142").isReachable(timeOut);
//            System.out.println(reachable);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
         //使用java调用cmd命令,这种方式最简单，可以把ping的过程显示在本地。连接时间不能控制
//        String line = null;
//        try {
//            Process pro = Runtime.getRuntime().exec("ping 192.168.1.142");
//            BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream(),"GBK"));
//            while ((line=buf.readLine()) != null){
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //也是使用java调用控制台的ping命令，这个比较可靠，还通用，使用起来方便：传入个ip，设置ping的次数和超时，就可以根据返回值来判断是否ping通。
        System.out.println(ping("192.168.1.141",1,1));


    }
}
