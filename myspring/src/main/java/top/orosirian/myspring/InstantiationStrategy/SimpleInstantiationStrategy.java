package top.orosirian.myspring.InstantiationStrategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import top.orosirian.myspring.definition.BeanDefinition;
import top.orosirian.myspring.utils.BeansException;

// 通过反射来创建Bean，大部分情况用这个初始化就好
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor<?> ctor, Object[] args) throws BeansException {
        Class<?> clazz = beanDefinition.getBeanClass();
        try {
            if(ctor != null) {
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {    // 传入构造器为空，则默认调用无参构造器
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeansException("初始化[" + clazz.getName() + "]失败", e);
        }
        

    }

}
