package top.orosirian.proj.event;

import top.orosirian.myspring.event.ApplicationListener;
import top.orosirian.myspring.event.ContextRefreshedEvent;

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("刷新事件: " + this.getClass().getName());
    }
    
}
