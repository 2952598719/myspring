package top.orosirian.myspring.support;

import top.orosirian.myspring.BeansException;
import top.orosirian.myspring.definition.BeanDefinition;

@SuppressWarnings("unchecked")
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    @Override 
    public Object getBean(String name) throws BeansException {
        return dogetBean(name, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return dogetBean(name, args);
    }

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
