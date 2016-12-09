package cn.com.hvit.test.mapper;

//import cn.com.hvit.workspace.dao.AttachmentMapper;
//import cn.com.hvit.workspace.model.Attachment;
import cn.com.hvit.workspace.dao.XtRoleFuncMapper;
import cn.com.hvit.workspace.model.XtRoleFunc;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import util.MyBatisSqlSessionFactory;

import java.text.ParseException;

public class XtRoleFuncTest {



    /**
     *
     * @throws ParseException
     */
    @Test
    public void deleteRoleFunctionsById() throws ParseException {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            XtRoleFuncMapper xtRoleFuncMapper = sqlSession.getMapper(XtRoleFuncMapper.class);

            XtRoleFunc info = new XtRoleFunc();

            xtRoleFuncMapper.deleteByRoleId(1);

            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void selectAll() throws ParseException {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
  /*          AttachmentMapper attachmentMapper = sqlSession.getMapper(AttachmentMapper.class);

            for (Attachment attachment : attachmentMapper.selectAll()) {
                System.out.println(attachment);
            }
*/
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}
