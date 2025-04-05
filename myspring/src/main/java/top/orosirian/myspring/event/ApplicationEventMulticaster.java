package top.orosirian.myspring.event;

public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);
    
}
