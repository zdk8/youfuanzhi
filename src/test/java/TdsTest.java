import cn.com.hvit.framework.kon.util.PageHelper;
import cn.com.hvit.workspace.model.Ls_Log;
import cn.com.hvit.workspace.service.ILogService;
import cn.com.hvit.workspace.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class TdsTest {
    private IUserService userService;
    private ILogService logService;

    @Before
    public void before(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:conf/spring.xml","classpath:conf/spring-mybatis.xml");
        userService = (IUserService) context.getBean("userServiceImpl");
        logService = (ILogService) context.getBean("logServiceImpl");
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
}
