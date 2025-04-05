package top.orosirian.myspring.support.context;

import top.orosirian.myspring.utils.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void refresh() throws BeansException;   // 刷新容器

    void registerShutdownHook();

    void close();
    
}
