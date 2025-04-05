package top.orosirian.myspring.event;

public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);
    
}
