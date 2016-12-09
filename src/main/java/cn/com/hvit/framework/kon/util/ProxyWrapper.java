package cn.com.hvit.framework.kon.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wp on 16-11-1.
 */
public class ProxyWrapper {
    public String makeProxy(String destUrl, String body,  String contentType) {
        System.out.println("测试1b");
        String myResult = null;
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
                for (Header header : response.getAllHeaders()) {
                    System.out.println(header.getName() + ":" + header.getValue());
                }
                System.out.println();
                if (entity != null) {
                    System.out.println("--------------------------------------");
                    myResult = EntityUtils.toString(entity, "UTF-8");
                    System.out.println("Response content: " + myResult);
                    System.out.println("--------------------------------------");


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
        return myResult;
    }


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

}
