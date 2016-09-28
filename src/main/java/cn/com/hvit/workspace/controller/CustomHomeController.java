package cn.com.hvit.workspace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by wp on 16-9-22.
 */
@Controller
public class CustomHomeController {


    /*
    主界面
     */
    @RequestMapping(value={"/custom-home"},method = RequestMethod.GET)
    public String home(){
        return "custom-home";
    }


}
