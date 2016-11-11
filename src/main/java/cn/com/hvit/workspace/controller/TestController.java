package cn.com.hvit.workspace.controller;

import org.springframework.stereotype.Controller;

/**
 * Created by Administrator on 2016/11/11.
 */
@Controller
public class TestController {

    public void test(){
        System.out.println(111 + " " + System.currentTimeMillis());
    }
}
