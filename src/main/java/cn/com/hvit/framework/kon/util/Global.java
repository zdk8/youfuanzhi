package cn.com.hvit.framework.kon.util;



import cn.com.hvit.workspace.model.Xt_user;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by wp on 16-7-25.
 */
public class Global {


    /**
     * 获取当前的登陆用户
     * @param request
     * @return
     */
    public static Xt_user getCurrentUser(HttpServletRequest request) {
        Xt_user xt_user = (Xt_user) request.getSession().getAttribute("user");
        return xt_user;
    }

    /**
     * 根据请求，获取高级查询条件集合
     * @param request
     */
    public static List<Map> getIntelligentSearchList(HttpServletRequest request) {
        final String INTELLIGENTSEARCH="intelligentsearch";
        //s.substring(s.indexOf("[")+1,s.indexOf("[")+2)
        int start=INTELLIGENTSEARCH.length()+1;
        int end=start+1;

        Enumeration<String> iNames= request.getParameterNames();
        List<Map> list=new ArrayList<Map>();
        int i=0;
        while (iNames.hasMoreElements()) {

            String paramName=iNames.nextElement();
            if (paramName.startsWith(INTELLIGENTSEARCH)) {
                String queryKey=paramName.substring(paramName.lastIndexOf("[")+1,paramName.lastIndexOf("]"));
                int index= Integer.parseInt(paramName.substring(start,end));
                if (list.size()>index) {
                    Map m=list.get(index);
                    m.put(queryKey,request.getParameter(paramName));

                }else{
                    Map m2=new HashMap();
                    m2.put(queryKey,request.getParameter(paramName));
                    list.add(m2);
                }
            }
        }

        return list;
    }

    /**
     * 根据请求，获取高级查询条件集合,只包含name和value
     * @param request
     */
    public static Map getIntelligentSearchMap(HttpServletRequest request) {
        final String INTELLIGENTSEARCH="intelligentsearch";
        Enumeration<String> iNames= request.getParameterNames();
        Map myMap=new HashMap();
        int start=INTELLIGENTSEARCH.length()+1;
        int end=start+1;
        while (iNames.hasMoreElements()) {
            String paramName=iNames.nextElement();
            if (paramName.startsWith(INTELLIGENTSEARCH) && paramName.contains("[name]")) {
                int index= Integer.parseInt(paramName.substring(start,end));
                String key=request.getParameter(INTELLIGENTSEARCH+"["+index+"][name]");
                String value=request.getParameter(INTELLIGENTSEARCH+"["+index+"][value]");
                myMap.put(key,value);
            }
        }


        return myMap;
    }


}
