package cn.com.hvit.workspace.dao;

import cn.com.hvit.workspace.model.Ls_User;
import java.math.BigDecimal;

public interface Ls_UserMapper {

    Ls_User userLogin(String useraccount,String userpwd);

}