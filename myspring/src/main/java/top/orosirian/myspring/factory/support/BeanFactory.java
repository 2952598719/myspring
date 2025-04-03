package top.orosirian.myspring.factory.support;

import top.orosirian.myspring.BeansException;

// 表示通过name获取Bean的功能
public interface BeanFactory {
    
    Object getBean(String name) throws BeansException;

}
