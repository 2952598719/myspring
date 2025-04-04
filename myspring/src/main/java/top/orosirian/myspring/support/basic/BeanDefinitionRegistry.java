package top.orosirian.myspring.support.basic;

import top.orosirian.myspring.definition.BeanDefinition;
import top.orosirian.myspring.utils.BeansException;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    boolean containsBeanDefinition(String beanName);

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    String[] getBeanDefinitionNames();
    
}
