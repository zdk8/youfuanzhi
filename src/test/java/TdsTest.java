import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.Ls_Blast;
import cn.com.hvit.workspace.model.Ls_Log;
import cn.com.hvit.workspace.service.IBlastService;
import cn.com.hvit.workspace.service.ILogService;
import cn.com.hvit.workspace.service.IUserService;
import cn.com.hvit.workspace.util.CommonCode;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    /**
     * 登录测试
     */
    @Test
    public void testUserLogin(){
        System.out.println(userService.getUserByName("aaa").getUseraccount());
    }

    /**
     * 日志测试
     */
    @Test
    public void testLog(){
        Ls_Log log = new Ls_Log();
        log.setUserid(BigDecimal.valueOf(2));
        log.setLogcontent("test log");
        logService.addLog(log);
//        System.out.println(111);
//        List logs = logService.getLogs();
//        PageHelper.Page<Ls_Log> logs = logService.getLogs(1, 10);
//        System.out.println(logs.getResults().size());
    }

    /**
     * 爆破信息测试
     */
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

    /**
     * ip连接测试
     */
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
        CommonCode code = new CommonCode();
        System.out.println(code.ping("192.168.1.142",1,1));


    }

    /**
     * 短信发送测试
     * @throws Exception
     */
    @Test
    public void testSendMessage() throws Exception{
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://sms.webchinese.cn/web_api/");
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");
        NameValuePair[] data = {new NameValuePair("Uid","kanontds"),new NameValuePair("Key","短信密钥"),new NameValuePair("smsMob","13185019135"),new NameValuePair("smsText","经过国际认证你是个大SB")};
        post.setRequestBody(data);

        client.executeMethod(post);
        Header[] headers = post.getRequestHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        for (Header h : headers){
            System.out.println(h.toString());
        }
        String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        System.out.println(result);

        post.releaseConnection();
    }

    /**
     * 获取客户端ip测试
     */
    @Test
    public void getIP(){

    }

}
