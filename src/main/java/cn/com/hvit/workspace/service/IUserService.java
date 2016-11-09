package cn.com.hvit.workspace.service;

import cn.com.hvit.workspace.model.Ls_User;

/**
 * Created by Administrator on 2016/11/9.
 */
public interface IUserService {
    Ls_User userLogin(String useraccount,String userpwd);
}
