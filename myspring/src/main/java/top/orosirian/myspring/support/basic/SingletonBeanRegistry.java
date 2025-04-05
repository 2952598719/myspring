package top.orosirian.myspring.support.basic;

public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    void destroySingletons();
    
}
