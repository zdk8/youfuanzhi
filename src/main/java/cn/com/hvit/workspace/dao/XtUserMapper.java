package cn.com.hvit.workspace.dao;


import cn.com.hvit.workspace.model.XtUser;

import java.util.List;
import java.util.Map;

public interface XtUserMapper {
    int deleteByPrimaryKey(Short userid);

    int insert(XtUser record);

    int insertSelective(XtUser record);

    XtUser selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(XtUser record);

    int updateByPrimaryKey(XtUser record);

    List findXt_user(Map map);

    List findUserByRegionId(String regionid);

    XtUser xtuserLogin(String useraccount, String userpwd);

    XtUser getUserByid(int userid);
}