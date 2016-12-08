package cn.com.hvit.workspace.dao;


import cn.com.hvit.workspace.model.UserXt;

public interface UserxtMapper {

    UserXt xtuserLogin(String useraccount, String userpwd);

    UserXt getUserByid(int userid);
}