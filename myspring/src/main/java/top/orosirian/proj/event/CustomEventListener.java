package top.orosirian.proj.event;

import java.util.Date;

import top.orosirian.myspring.event.ApplicationListener;

public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("收到: " + event.getSource() + "消息; 时间:" + new Date());
        System.out.println("消息: " + event.getId() + ":" + event.getMessage());
    }
    
}
