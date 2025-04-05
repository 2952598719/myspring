package top.orosirian.proj.event;

import top.orosirian.myspring.event.ApplicationListener;
import top.orosirian.myspring.event.ContextClosedEvent;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("关闭事件: " + this.getClass().getName());
    }
    
}
