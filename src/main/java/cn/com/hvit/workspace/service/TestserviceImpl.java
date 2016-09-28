package cn.com.hvit.workspace.service;

/**
 * Created by wp on 16/9/25.
 */
public class TestserviceImpl implements TestService,Runnable {

    @Override
    public String getServiceName() {
        return null;
    }


    @Override
    public void setServiceName(String serviceName) {

    }

    @Override
    public void run() {
        String s = "xxxxxx";
        System.out.println(s);
    }

    @Override
    public String toString() {
        String s = "TestserviceImpl{}";
        if (s.isEmpty()) {

        }
        return s;
    }
}
