package top.orosirian.myspring.support.basic;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import top.orosirian.myspring.definition.BeanDefinition;
import top.orosirian.myspring.process.processor.BeanPostProcessor;
import top.orosirian.myspring.support.spetialfactory.ConfigurableBeanFactory;
import top.orosirian.myspring.utils.BeansException;
import top.orosirian.myspring.utils.ClassUtils;;

@SuppressWarnings("unchecked")
@Getter
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

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

    // 如果某个processor存在，则从processors中间移除放到末端
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }
    
}
