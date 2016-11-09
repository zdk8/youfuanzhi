package cn.com.hvit.workspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/11/9.
 */
@Controller
public class UserLoginController {

    public void userLogin(@RequestParam String useraccount,@RequestParam String userpwd, HttpServletRequest request, HttpServletResponse response){
        System.out.println(111111);
    }
}
