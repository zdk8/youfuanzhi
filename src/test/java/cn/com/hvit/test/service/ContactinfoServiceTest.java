package cn.com.hvit.test.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wp on 16-6-30.
 */
public class ContactinfoServiceTest {

	//private ContactsInfoService contactsInfoService;

	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:conf/spring.xml"
				,"classpath:conf/spring-mybatis.xml");
		//contactsInfoService = (ContactsInfoService) context.getBean("contactsInfoServiceImpl");
	}


	@Test
	public void testQueryWithMap() throws Exception {

	}

	@Test
	public void testInsert() throws Exception {
		/*ContactsInfo info=new ContactsInfo();
		 info.setContactname("我是谁");
            info.setContacttel("7348174693");
            info.setOldid(123);
		contactsInfoService.insert(info);*/
	}
}