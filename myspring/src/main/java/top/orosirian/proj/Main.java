package top.orosirian.proj;

import top.orosirian.myspring.support.context.ClassPathXmlApplicationContext;
import top.orosirian.proj.bean.UserService;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();
        UserService userService = applicationContext.getBean("userService", UserService.class);
        System.out.println(userService.queryUserInfo());
    }


}
