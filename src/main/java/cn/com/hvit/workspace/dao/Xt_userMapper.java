package cn.com.hvit.workspace.dao;


import cn.com.hvit.workspace.model.Xt_user;

import java.util.List;
import java.util.Map;

public interface Xt_userMapper {
    int deleteByPrimaryKey(String userid);

    int insert(Xt_user record);

    int insertSelective(Xt_user record);

    Xt_user selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(Xt_user record);

    int updateByPrimaryKey(Xt_user record);

    List findXt_user(Map map);

    List findUserByRegionId(String regionid);

    Xt_user xtuserLogin(String useraccount, String userpwd);

    Xt_user getUserByid(int userid);
}