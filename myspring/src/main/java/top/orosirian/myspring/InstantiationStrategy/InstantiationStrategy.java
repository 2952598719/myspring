package top.orosirian.myspring.InstantiationStrategy;

import java.lang.reflect.Constructor;

import top.orosirian.myspring.definition.BeanDefinition;
import top.orosirian.myspring.utils.BeansException;

public interface InstantiationStrategy {

    Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor<?> ctor, Object[] args) throws BeansException;
    
}
