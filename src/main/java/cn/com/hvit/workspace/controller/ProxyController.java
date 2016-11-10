package cn.com.hvit.workspace.controller;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wp on 16-11-9.
 */
@Controller
public class ProxyController {




    public Map makePostGeoserverProxy(String destUrl, String body, String contentType) {
        String myResult = null;
        Map resultMap = new HashMap();
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(destUrl);
        httppost.setHeader("Content-Type",contentType);

        try {
            httppost.setEntity(new StringEntity(new String(body.getBytes("utf8"),"iso-8859-1")));
            System.out.println("executing request " + httppost.getURI());



            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println(response.getStatusLine());


                if (entity != null) {
                    System.out.println("--------------------------------------");
                    myResult = EntityUtils.toString(entity, "UTF-8");
                    System.out.println("Response content: " + myResult);
                    System.out.println("--------------------------------------");

                    //封装一些重要的信息
                    resultMap.put("headers",response.getAllHeaders());
                    resultMap.put("body", myResult);

                }
            } finally {
                response.close();
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

    public Map makeGetGeoserverProxy(String destUrl,  String contentType) {
        String myResult = null;
        Map resultMap = new HashMap();
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(destUrl);
        //httpget.setHeader("Content-Type",contentType);
        System.out.println("executing request " + destUrl);

        try {
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();
                System.out.println(response.getStatusLine());


                if (entity != null) {
                    System.out.println("--------------------------------------");
                    myResult = EntityUtils.toString(entity, "UTF-8");
                    System.out.println("Response content: " + myResult);
                    System.out.println("--------------------------------------");

                    //封装一些重要的信息
                    resultMap.put("headers",response.getAllHeaders());
                    resultMap.put("body", myResult);

                }
            } finally {
                response.close();
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }

     /**
     * 可能不支持重定向等无内容的请求响应体
     * 返回的数据包括Content-Type和body
     *
     * @param url
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "auth/proxy", method = RequestMethod.POST)
    public void proxyPost(
            //@PathVariable String fileName,
            @RequestParam String url,
            @RequestBody String payloadBody,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map proxyResult = makePostGeoserverProxy(url, payloadBody, request.getContentType());

        for (Header header : (Header[]) proxyResult.get("headers")) {
            String headerName=header.getName();
            if(headerName.equals("Content-Disposition") || headerName.equals("Content-Type")){
                response.setHeader(headerName,header.getValue());
            }
        }
        PrintWriter out = response.getWriter();
        out.write(proxyResult.get("body").toString());
        out.close();
    }

    /**
     * 可能不支持重定向等无内容的请求响应体
     * 返回的数据包括Content-Type和body
     *
     * @param url
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "auth/proxy", method = RequestMethod.GET)
    public void proxyGet(
            //@PathVariable String fileName,
            @RequestParam String url,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

                //重新指定url2
        //String url2=request.getQueryString().substring("url=".length());
        String url2=request.getQueryString().substring(4);

        Map proxyResult = makeGetGeoserverProxy(url2, request.getContentType());


        for (Header header : (Header[]) proxyResult.get("headers")) {
            String headerName=header.getName();
            if(headerName.equals("Content-Disposition") || headerName.equals("Content-Type")){
                response.setHeader(headerName,header.getValue());
            }
        }
        PrintWriter out = response.getWriter();
        out.write(proxyResult.get("body").toString());
        out.close();
    }


}
