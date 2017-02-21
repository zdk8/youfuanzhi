package cn.com.hvit.workspace.util;

/**
 * Created by Administrator on 2017/1/15.
 */
public class LearnJava {
    String name;
    int age;

    LearnJava(String name,int age){
        this.name = name;
        this.age = age;
        System.out.println("thanks");
    }

    void bark(){
        System.out.println("bark");
    }

    void hungry(){
        System.out.println("hungry");
    }

    public static void main(String[] args) {
        LearnJava huahua = new LearnJava("huahua", 3);
        String name = huahua.name;
        int age = huahua.age;
        System.out.println("huahua's name " +name + ",age: " +age);
        huahua.bark();
        huahua.hungry();
    }
}
