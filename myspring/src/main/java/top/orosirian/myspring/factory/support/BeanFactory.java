package top.orosirian.myspring.factory.support;

import top.orosirian.myspring.factory.use.BeansException;

// 表示通过name获取Bean的功能
public interface BeanFactory {
    
    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

}
