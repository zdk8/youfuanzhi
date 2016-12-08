package cn.com.hvit.workspace.service;

import cn.com.hvit.workspace.model.Ls_User;
import cn.com.hvit.workspace.model.UserXt;

/**
 * Created by Administrator on 2016/11/9.
 */
public interface IUserService {
    Ls_User userLogin(String useraccount,String userpwd);

    Ls_User getUserByName(String useraccount);

    UserXt userxtLogin(String useraccount,String userpwd);

    void addUser(Ls_User user);
}
