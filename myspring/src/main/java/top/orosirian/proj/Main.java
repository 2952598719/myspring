package top.orosirian.proj;

import top.orosirian.myspring.support.context.ClassPathXmlApplicationContext;
import top.orosirian.proj.event.CustomEvent;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1L, "成功了"));
        applicationContext.registerShutdownHook();
    }


}
