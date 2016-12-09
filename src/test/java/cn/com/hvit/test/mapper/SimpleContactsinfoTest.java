package cn.com.hvit.test.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import util.MyBatisSqlSessionFactory;

import java.text.ParseException;

/**
 * 联系人增删改查测试
 * Created by wp on 16-7-21.
 */


public class SimpleContactsinfoTest {



    /**
     * 插入一条数据，然后返回主键
     * @throws ParseException
     */
    @Test
    public void insertOldinfoReturnId() throws ParseException {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
/*
            ContactsInfoMapper contactsInfoMapper = sqlSession.getMapper(ContactsInfoMapper.class);

            ContactsInfo info = new ContactsInfo();
            info.setContactname("我是谁");
            info.setContacttel("7348174693");
            info.setOldid(123);
            contactsInfoMapper.insert(info);
            System.out.println("############################");
            System.out.println(info.getCiid());
*/

            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}
