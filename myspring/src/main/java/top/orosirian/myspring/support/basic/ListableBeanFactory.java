package top.orosirian.myspring.support.basic;

import java.util.Map;

import top.orosirian.myspring.utils.BeansException;

public interface ListableBeanFactory extends BeanFactory {

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    String[] getBeanDefinitionNames();
    
}
