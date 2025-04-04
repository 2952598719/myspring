package top.orosirian.myspring.support;

import top.orosirian.myspring.definition.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    boolean containsBeanDefinition(String beanName);
    
}
