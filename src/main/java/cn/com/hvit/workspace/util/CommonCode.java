package cn.com.hvit.workspace.util;

import cn.com.hvit.framework.kon.util.AttachmentNameBean;
import cn.com.hvit.workspace.config.Shout;
import cn.com.hvit.workspace.model.XtUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/10.
 */
public class CommonCode {


    /**
     * 日志增加的方法，（未实现单例）
     * @param request
     * @param message
     */

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


    public String stringSort(String str,String sort){
        String result = "";
        String[] split = str.split(sort);
        Arrays.sort(split);
        for (String str1 : split){
            result = result + str1 + sort;
        }
        return result;
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



    /**
     * 发送信息
     * @param tel
     * @param msg
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public void sendmsg(String tel,String msg) throws IOException, NoSuchAlgorithmException {
        StringBuffer sb = new StringBuffer("http://api.106msg.com/TXTJK.aspx?");
        sb.append("type=send&ua=66770284");
        sb.append("&pwd="+mmd5("HVIT123"));
        sb.append("&gwid=30");
        sb.append("&mobile="+tel);
        sb.append("&msg="+ URLEncoder.encode(msg,"GB2312"));
        URL url = new URL(sb.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputline = in.readLine();
        System.out.println(inputline);

    }

    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    public static String toHexString(byte[] b) { //String to byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }
    public String mmd5(String s) throws NoSuchAlgorithmException {
        try { // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            System.out.println(toHexString(messageDigest));
            return toHexString(messageDigest); }
        catch (NoSuchAlgorithmException e)
        { e.printStackTrace(); } return "";
    }

    /**
     * 106msg平台发送短信
     * @param tel
     * @param msg
     * @throws IOException
     */
    public void sendmsg2(String tel,String msg) throws IOException {

        // 创建StringBuffer对象用来操作字符串
        StringBuffer sb = new StringBuffer("http://api.106msg.com/TXTJK.aspx?");

        // 向StringBuffer追加用户名
        sb.append("type=send&ua=66770284");

        // 向StringBuffer追加密码
        try {
            sb.append("&pwd=" + mmd5("HVIT123"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 向StringBuffer追加网关id
        sb.append("&gwid=30");

        // 向StringBuffer追加手机号码
        sb.append("&mobile=" + tel);

        // 向StringBuffer追加消息内容转URL标准码
        sb.append("&msg="+URLEncoder.encode(msg,"GB2312"));

        // 创建url对象
        URL url = new URL(sb.toString());

        // 打开url连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 设置url请求方式 ‘get’ 或者 ‘post’
        connection.setRequestMethod("POST");

        // 发送
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        // 返回发送结果
        String inputline = in.readLine();

        // 返回结果为‘100’ 发送成功
        System.out.println(inputline);
    }


    /**
     * 文件下载
     * @param filename
     * @param request
     * @return
     * @throws IOException
     */

    public ResponseEntity filedownload(@PathVariable String filename, HttpServletRequest request) throws IOException {
        File file = new File(AttachmentNameBean.getAbsolutePath(filename));
        HttpHeaders headers = new HttpHeaders();
        if (request.getParameterMap().containsKey("dlname")){
            String dlname = request.getParameter("dlname");
            if (dlname == null || dlname.trim().length() == 0){
                dlname = filename;
            }
            dlname = new String(dlname.getBytes("utf-8"),"iso8859-1");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment",dlname);
        } else if (filename.contains(".")) {
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            switch (suffix.toUpperCase()){
                case "JPG":
                case "JPEG":
                case "PNG":
                    headers.setContentType(MediaType.parseMediaType("image/" + suffix));
                    break;
                default:
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    headers.setContentDispositionFormData("attachment",new String(filename.getBytes("utf-8"),"iso8859-1"));
                    break;
            }
        }
        return new ResponseEntity(FileCopyUtils.copyToByteArray(file),headers, HttpStatus.OK);
    }


    public ResponseEntity filedownload2(@PathVariable String filename, HttpServletRequest request,String winPath) throws IOException {
//        String ATTACHMENTDIRNAME="c:/Downloads/upload/";
//        winPath = "\\\\192.168.1.142\\lishuigxtest\\"+winPath;
        String filePath =winPath + filename.replaceFirst("-", "/").replaceFirst("-", "/").replaceFirst("-", "/");
//        File file2 = new File(AttachmentNameBean.getAbsolutePath(filename));
        File file = new File(filePath);
        HttpHeaders headers = new HttpHeaders();
        if (request.getParameterMap().containsKey("dlname")){
            String dlname = request.getParameter("dlname");
            if (dlname == null || dlname.trim().length() == 0){
                dlname = filename;
            }
            dlname = new String(dlname.getBytes("utf-8"),"iso8859-1");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment",dlname);
        } else if (filename.contains(".")) {
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            switch (suffix.toUpperCase()){
                case "JPG":
                case "JPEG":
                case "PNG":
                    headers.setContentType(MediaType.parseMediaType("image/" + suffix));
                    break;
                default:
                    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    headers.setContentDispositionFormData("attachment",new String(filename.getBytes("utf-8"),"iso8859-1"));
                    break;
            }
        }
        return new ResponseEntity(FileCopyUtils.copyToByteArray(file),headers, HttpStatus.OK);
    }



    public void sendClientMessafe(SimpMessagingTemplate messaging,String msg){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        Shout shout = new Shout();
        shout.setMessage("blast最新消息【"+ df.format(new Date())+"】：" + msg);
        messaging.convertAndSend("/topic/marco", shout);
    }


}
