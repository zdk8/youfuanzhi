import cn.com.hvit.workspace.service.IUserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2016/11/9.
 */
public class TdsTest {
    private IUserService userService;

    @Before
    public void before(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:conf/spring.xml","classpath:conf/spring-mybatis.xml");
        userService = (IUserService) context.getBean("userServiceImpl");
    }

    @Test
    public void testUserLogin(){
        System.out.println(userService.userLogin("admin","123456").getUseraccount());
    }
}
