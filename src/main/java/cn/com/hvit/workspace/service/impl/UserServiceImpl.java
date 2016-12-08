package cn.com.hvit.workspace.service.impl;

import cn.com.hvit.workspace.dao.Ls_UserMapper;
import cn.com.hvit.workspace.dao.UserxtMapper;
import cn.com.hvit.workspace.model.Ls_User;
import cn.com.hvit.workspace.model.UserXt;
import cn.com.hvit.workspace.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/11/9.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private Ls_UserMapper userMapper;

    @Autowired
    private UserxtMapper userxtMapper;

    @Override
    public Ls_User userLogin(String useraccount, String userpwd) {
        return userMapper.userLogin(useraccount,userpwd);
    }

    @Override
    public Ls_User getUserByName(String useraccount) {
        return userMapper.getUserByName(useraccount);
    }

    @Override
    public UserXt userxtLogin(String useraccount, String userpwd) {
        return userxtMapper.xtuserLogin(useraccount,userpwd);
    }

    @Override
    public void addUser(Ls_User user) {
        userMapper.addUser(user);
    }

    @Override
    public UserXt getUserByid(int userid) {
        return userxtMapper.getUserByid(userid);
    }
}
