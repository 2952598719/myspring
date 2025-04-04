package top.orosirian.myspring.processor;

import top.orosirian.myspring.utils.BeansException;

public interface BeanPostProcessor {

    // Bean初始化前，执行此方法
    Object postProcessBeforeInitialization(String beanName, Object bean) throws BeansException;

    // Bean初始化后，执行此方法
    Object postProcessAfterInitialization(String beanName, Object bean) throws BeansException;
    
}
