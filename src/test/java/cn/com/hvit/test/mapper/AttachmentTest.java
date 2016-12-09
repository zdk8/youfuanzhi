package cn.com.hvit.test.mapper;

//import cn.com.hvit.workspace.dao.AttachmentMapper;
//import cn.com.hvit.workspace.model.Attachment;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import util.MyBatisSqlSessionFactory;

import java.text.ParseException;

public class AttachmentTest {



    /**
     * 插入一条数据，然后返回主键
     * @throws ParseException
     */
    @Test
    public void insertAttacheInfoReturnId() throws ParseException {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
/*            AttachmentMapper attachmentMapper = sqlSession.getMapper(AttachmentMapper.class);

            Attachment info = new Attachment();
            info.setNewfilename("newfilename");
            info.setOldfilename("oldfilename");
            info.setFilesize(1024L);
            info.setUrl("xxxxx.png");
            attachmentMapper.insert(info);
            //attachmentMapper.insertSelective(info);
            System.out.println("############################");
            System.out.println(info.getId());*/

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
