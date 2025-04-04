package top.orosirian.myspring.factory.use.InstantiationStrategy;

import java.lang.reflect.Constructor;

import top.orosirian.myspring.factory.use.BeansException;
import top.orosirian.myspring.factory.use.definition.BeanDefinition;

public interface InstantiationStrategy {

    Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor<?> ctor, Object[] args) throws BeansException;
    
}
