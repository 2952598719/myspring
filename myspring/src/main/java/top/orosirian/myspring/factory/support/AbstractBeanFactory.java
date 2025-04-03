package top.orosirian.myspring.factory.support;

import top.orosirian.myspring.BeansException;

public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override 
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if(bean != null) {
            return bean;
        }
        return createBean(name, getBeanDefinition(name));
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;
    
}
