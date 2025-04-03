package top.orosirian.myspring.factory.support;

import java.lang.reflect.InvocationTargetException;

import top.orosirian.myspring.BeansException;

@SuppressWarnings({"unchecked"})
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean;
        try {
            bean = beanDefinition.getBeanClass().getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BeansException("bean的实例化失败", e);
        }
        addSingleton(beanName, bean);
        return bean;
    }
    
}
