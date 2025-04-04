package top.orosirian.myspring.InstantiationStrategy;

import java.lang.reflect.Constructor;

import top.orosirian.myspring.BeansException;
import top.orosirian.myspring.definition.BeanDefinition;

public interface InstantiationStrategy {

    Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor<?> ctor, Object[] args) throws BeansException;
    
}
