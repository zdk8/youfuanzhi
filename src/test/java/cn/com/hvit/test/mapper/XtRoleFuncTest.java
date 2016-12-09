package cn.com.hvit.test.mapper;

//import cn.com.hvit.workspace.dao.AttachmentMapper;
//import cn.com.hvit.workspace.model.Attachment;
import cn.com.hvit.framework.kon.util.KeyLowerMapUtil;
import cn.com.hvit.workspace.dao.XtRoleFuncMapper;
import cn.com.hvit.workspace.dao.XtRoleMapper;
import cn.com.hvit.workspace.model.XtRole;
import cn.com.hvit.workspace.model.XtRoleFunc;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import util.MyBatisSqlSessionFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            xtRoleFuncMapper.deleteByRoleId(421);

            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

        private List parse2LowerList(List<Map> list) {
        List<Map> resultList = new ArrayList<>();
        for (Map map : list) {
            resultList.add(KeyLowerMapUtil.parse(map));
        }
        return resultList;
    }
    /**
     *
     * @throws ParseException
     */
    @Test
    public void findAll() throws ParseException {
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            XtRoleFuncMapper xtRoleFuncMapper = sqlSession.getMapper(XtRoleFuncMapper.class);
            XtRoleMapper xtRoleMapper= sqlSession.getMapper(XtRoleMapper.class);
            XtRoleFunc info = new XtRoleFunc();

            Map params = new HashMap();
            params.put("functionid", "businessmenu");
            params.put("roleid", 367);
            List<Map> list=parse2LowerList(xtRoleMapper.selectPrivilegesByRole(params));

            for (Map map : list) {
                System.out.println(map);
            }

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
