import cn.com.hvit.framework.kon.util.DataSourceContextHolder;
import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.*;
import cn.com.hvit.workspace.service.*;
import cn.com.hvit.workspace.util.CommonCode;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/9.
 */
public class TdsTest {
    private IUserService userService;
    private ILogService logService;
    private IBlastService blastService;
    private IFileService fileService;
    private IResponseService responseService;

    @Before
    public void before(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:conf/spring.xml","classpath:conf/spring-mybatis.xml");
        userService = (IUserService) context.getBean("userServiceImpl");
        logService = (ILogService) context.getBean("logServiceImpl");
        blastService = (IBlastService) context.getBean("blastServiceImpl");
        fileService = (IFileService) context.getBean("fileServiceImpl");
        responseService = (IResponseService) context.getBean("responseServiceImpl");
    }


    @Test
    public void TestJava(){
//        ls_responsemessage msg = new ls_responsemessage();
//        msg.setTelephone("18358158536");
//        System.out.println(msg.getTelephone() == "18358158536");

         String str = "1,3,4,2";
        String result = "";
        String[] split = str.split(",");
        Arrays.sort(split);
        for (String str1 : split){
            result = result + str1 + ",";
        }
        System.out.println(result);

    }

    /**
     * 登录测试
     */
    @Test
    public void testUserLogin(){
//        System.out.println(userService.getUserByName("aaa").getUseraccount());
//        UserXt xtUser = userService.userxtLogin("zmp", "hvit");
//        System.out.println(xtUser.getLoginname());
        //UserXt userXt = userService.getUserByid(610);
        //System.out.println(userXt.getLoginname());
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
        blast.setRegionid("330000");
        blast.setWeight(BigDecimal.valueOf(200));
        blast.setbId(BigDecimal.valueOf(21));
        blastService.addBlast(blast);
        System.out.println("success add");
//        blastService.updateBlast(blast);
//        System.out.println("update success");


//        HashMap<String,Object> condMap = new HashMap<>();
//        condMap.put("applicant","spider");
//        condMap.put("applyunit","11' or 1=1 or 1='1");
//        PageHelper.Page<Ls_Blast> blasts = blastService.getBlastByCond(1, 10,condMap);
//        System.out.println(blasts.getTotal());

    }

    /**
     * 地震响应
     */
    @Test
    public void responseTest(){
        System.out.println(responseService.getResponseByid(46).getYjname());

//        ls_responsemessage message = new ls_responsemessage();
//        message.setDepartment("市政府");
//        message.setDutycontent("贯彻市指挥部的指示和部署，协调有关县级人民政府、指挥部成员单位之间、地震现场指挥部以及各级地震救援队伍的应急工作。特别重大或重大地震灾害时，承担市指挥部办公室职责。");
//        message.setContact("陈重,钟建安");
//        message.setPosition("副市长,副秘书长");
//        message.setPhone("632453");
//        message.setRmlevel("1,2,3,4");
//        message.setYjid(BigDecimal.valueOf(21));
//        responseService.addEarthMessage(message);

//        ls_earthquakeresponse response = new ls_earthquakeresponse();
//        response.setYjname("丽水地震应急预案");
//        response.setYjpath("D://丽水地震应急预案.doc");
//        responseService.addEarthquake(response);

//        Map<String,Object> responseMap = new HashMap<String,Object>();
//        responseMap.put("yjid",21);
//        responseMap.put("rmlevel","1");
//        List message = responseService.getResponsebyid(responseMap);
//        System.out.println(message.size());

//        PageHelper.Page<ls_earthquakeresponse> earthquake = responseService.getresponseByCond(1, 10, null);
//        System.out.println(earthquake.getTotal());

//        HashMap<String,Object> responsemsgMap = new HashMap<String,Object>();
//        responsemsgMap.put("yjid",21);
//        PageHelper.Page<ls_responsemessage> respMsg = responseService.getEarthmsgByid(1, 10, responsemsgMap);
//        System.out.println(respMsg.getTotal());

//        List earthResponse = responseService.getEarthResponse();
//        System.out.println(earthResponse.size());

//        HashMap<String,Object> msgMap = new HashMap<String,Object>();
//        msgMap.put("rmid",26);
//        responseService.deleteEarthMsg(msgMap);
//        System.out.println("delete");



    }

    @Test
    public void testfiles(){
        HashMap<String,Object> condMap = new HashMap<>();
//        condMap.put("filename","PNG");
//        PageHelper.Page<LS_files> files = fileService.getfilesbycond(1, 10,condMap);
//        System.out.println(files.getTotal());

//        PageHelper.Page<LS_files> provincefiles = fileService.getprovincefilesbycond(1, 10, condMap);
//        System.out.println(provincefiles.getTotal());

        condMap.put("fid",36);
        fileService.deleteFileByid(condMap);
        System.out.println("delete files");

    }

//    @Test
//    public void testPoint(){
//        HashMap<String,Object> condMap = new HashMap<>();
////        DataSourceContextHolder.setDbType("yjxydataSource");
//        PageHelper.Page<Pointinfo> provincefiles = ponitService.getPonitByconds(1, 10, condMap);
//        System.out.println(provincefiles.getTotal());
//    }

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

    @Test
    public void testSendMessage106() throws IOException {
        String tel = "18358158536";
        String msg = "[地震应急响应]XX地发生3级地震，根据应急响应，请XXXXXXXXX";

        CommonCode code = new CommonCode();

        code.sendmsg2(tel,msg);

    }


    /**
     * 获取服务器上的文件（URL）
     */
    @Test
    public void getFile(){
        String urlPath = "http://121.40.143.127:8089/photofile/shenhebiao.doc";
        String filePath = "E:\\testfiles\\";
        try {
            URL url = new URL(urlPath);
            downloadFile(url,filePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadFile(URL url, String filePath) throws IOException {
        File dirfile = new File(filePath);
        if(!dirfile.exists()){
            dirfile.mkdir();
        }
        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        FileOutputStream os = new FileOutputStream(filePath + "shenhebiao.doc");
        byte[] buffer = new byte[4 * 1024];
        int read;
        while((read=in.read(buffer)) > 0){
            os.write(buffer,0,read);
        }
        os.close();
        in.close();
    }


    @Test
    public void testFile(){
        //方法一：org.apache.commons.io.FileUtils.copyFileToDirectory
//        try {
//            FileUtils.copyFileToDirectory(new File("d:\\WordTest.java"), new File("E:\\testfiles"), true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //方法二：文件流对文件操作
        boolean issuccess = copyFile("\\\\192.168.1.142\\WeeklyPlan\\README.txt","E:\\testfiles\\README.txt",true);
        System.out.println(issuccess);
      //方法三：文件流对文件夹操作
//         boolean dirsuccess = copyDirectory("D:\\projectfile","E:\\testfiles\\",true);
//        System.out.println(dirsuccess);
    }

    private boolean copyFile(String srcFilename,String destFilename,boolean overlay){
        String MESSAGE = "";
        File srcFile = new File(srcFilename);
        //
        if(!srcFile.exists()){
            MESSAGE = "源文件" + srcFilename + "不存在";
            JOptionPane.showMessageDialog(null,MESSAGE);
            return false;
        }else if(!srcFile.isFile()){
            MESSAGE = "复制文件失败：源文件："+ srcFilename + "不是个文件";
            JOptionPane.showMessageDialog(null,MESSAGE);
            return false;
        }

        //
        File destFile = new File(destFilename);
        if(destFile.exists()){
            //
            if (overlay){
                //
                new File(destFilename).delete();
            }
        } else {
            //
            if(!destFile.getParentFile().exists()){
                //
                if(!destFile.getParentFile().mkdir()){
                    //
                    return false;
                }
            }
        }
        //
        int byteread = 0 ;
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while((byteread = in.read(buffer))!= -1){
                out.write(buffer,0,byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
            if(out != null){
                out.close();
            }
            if (in != null){
                in.close();
            }
            }catch (IOException e) {
            e.printStackTrace();
        }
        }
    }

    private boolean copyDirectory(String srcDirname,String destDirname,boolean overload){
        String MESSAGE = "";
        File srcDir = new File(srcDirname);
        if (!srcDir.exists()){
            MESSAGE = "复制目录失败：源目录" + srcDirname + "不存在";
            JOptionPane.showMessageDialog(null,MESSAGE);
            return false;
        }else if (!srcDir.isDirectory()){
            MESSAGE = "复制目录失败：" + srcDirname + "不是目录";
            JOptionPane.showMessageDialog(null,MESSAGE);
            return false;
        }

        if (!destDirname.endsWith(File.separator)){
            destDirname = destDirname + File.separator;
        }
        File destDir = new File(destDirname);
        //
        if (destDir.exists()){
            //
            if (overload){
                new File(destDirname).delete();
            } else {
                MESSAGE = "复制目录失败：目的目录：" + destDirname + "已存在";
                JOptionPane.showMessageDialog(null,MESSAGE);
                return false;
            }
        }

        boolean flag = true;
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length;i++){
            //
            if (files[i].isFile()){
                flag = copyFile(files[i].getAbsolutePath(),destDirname+files[i].getName(),overload);
                if (!flag){
                    break;
                }
            }else if(files[i].isDirectory()){
                flag = copyDirectory(files[i].getAbsolutePath(),destDirname+files[i].getName(),overload);
                if (!flag){
                    break;
                }
            }
        }
        if (!flag){
            MESSAGE = "复制目录" + srcDirname + "至" + destDirname + "失败";
            JOptionPane.showMessageDialog(null,MESSAGE);
            return false;
        } else {
            return true;
        }
    }

}
