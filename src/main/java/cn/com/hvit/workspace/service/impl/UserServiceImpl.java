package cn.com.hvit.workspace.service.impl;

import cn.com.hvit.workspace.dao.Ls_UserMapper;
import cn.com.hvit.workspace.model.Ls_User;
import cn.com.hvit.workspace.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/11/9.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private Ls_UserMapper ls_userMapper;

    @Override
    public Ls_User userLogin(String useraccount, String userpwd) {
        return ls_userMapper.userLogin(useraccount,userpwd);
    }
}
