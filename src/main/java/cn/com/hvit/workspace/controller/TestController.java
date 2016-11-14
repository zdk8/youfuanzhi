package cn.com.hvit.workspace.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * Created by Administrator on 2016/11/11.
 */
@Component
public class TestController {
//    @Scheduled(cron="0 0/1 * * * ? ")
    public void test(){
        System.out.println(111 + " " + System.currentTimeMillis());
    }
}
