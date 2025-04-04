package top.orosirian.myspring.factory.support;

import top.orosirian.myspring.factory.use.definition.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
    
}
