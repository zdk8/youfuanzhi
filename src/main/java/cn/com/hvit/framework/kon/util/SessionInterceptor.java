package cn.com.hvit.framework.kon.util;


import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Repository
public class SessionInterceptor extends HandlerInterceptorAdapter {

     /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
      *
      *
      * 判断有无登陆
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {


        String requestURI=request.getRequestURI();
        Object user=request.getSession().getAttribute("user");
        if (user!=null || requestURI.contains("loginpage") || requestURI.contains("loginaction")
                || requestURI.contains("filedownload")
                ) {
            return true;//super.preHandle(request, response, handler);
        }else{
            /*PrintWriter out = response.getWriter();
            StringBuilder builder = new StringBuilder();
            builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
            builder.append("alert(\"页面过期，请重新登录\");");
            builder.append("window.top.location.href=\"");
            builder.append("loginpage\";</script>");
            out.print(builder.toString());
            out.close();*/
            return true;
        }

    }

}