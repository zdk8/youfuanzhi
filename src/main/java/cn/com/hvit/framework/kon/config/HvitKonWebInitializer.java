package cn.com.hvit.framework.kon.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.io.File;

public class HvitKonWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        System.out.println("配置rootconfig");
        return new Class<?>[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        System.out.println("配置servletconfig");
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
        super.registerContextLoaderListener(servletContext);

        //servletContext.setInitParameter("log4jConfigLocation", "classpath:conf/log4j.properties");
        //servletContext.setInitParameter("log4jRefreshInterval", "6000");
    }

    /*
       * DispatcherServlet的映射路径
       */
    @Override
    protected String[] getServletMappings() {
        System.out.println("配置mapping");
        return new String[]{"/"};
    }

// 使用了onStartUp其他方法就无效了
//  @Override
//  public void onStartup(ServletContext servletContext) throws ServletException {
//    System.out.println("on MyStartUp");
//  }


    /*
         * 注册过滤器，映射路径与DispatcherServlet一致，路径不一致的过滤器需要注册到另外的WebApplicationInitializer中
         */
    @Override
    protected Filter[] getServletFilters() {
        System.out.println("配置filters");
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[]{characterEncodingFilter};
    }


    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        String tmpDir="/tmp/hvit/uploads";
        if (System.getProperty("os.name").contains("Windows")) {
            tmpDir="c:/hvit/uploads";
        }
        File file = new File(tmpDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        registration.setMultipartConfig(
                new MultipartConfigElement(tmpDir, 20971520, 41943040, 0));
    }

    public static void main(String[] args) {
        System.getProperty("os.name").equals("Windows");
        System.out.println(System.getProperty("os.name").contains("Windows"));
    }

}