package top.orosirian.myspring.factory.support;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
    
}
