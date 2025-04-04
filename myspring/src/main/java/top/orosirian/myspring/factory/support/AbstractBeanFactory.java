package top.orosirian.myspring.factory.support;

import top.orosirian.myspring.factory.use.BeansException;
import top.orosirian.myspring.factory.use.definition.BeanDefinition;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override 
    public Object getBean(String name) throws BeansException {
        return dogetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return dogetBean(name, args);
    }

    @SuppressWarnings("unchecked")
    protected <T> T dogetBean(final String name, final Object[] args) {
        Object bean = getSingleton(name);
        if(bean != null) {
            return (T)bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T)createBean(name, beanDefinition, args);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;
    
}
