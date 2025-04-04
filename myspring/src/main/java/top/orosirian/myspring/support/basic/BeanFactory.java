package top.orosirian.myspring.support.basic;

import top.orosirian.myspring.utils.BeansException;

// 表示通过name获取Bean的功能
public interface BeanFactory {
    
    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

}
