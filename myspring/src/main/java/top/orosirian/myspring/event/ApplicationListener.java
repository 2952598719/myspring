package top.orosirian.myspring.event;

import java.util.EventListener;

public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    void onApplicationEvent(E event);
    
}
