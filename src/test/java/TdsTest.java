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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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
}
