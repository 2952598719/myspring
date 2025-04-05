package top.orosirian.myspring.support.basic;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import top.orosirian.myspring.definition.BeanDefinition;
import top.orosirian.myspring.process.processor.BeanPostProcessor;
import top.orosirian.myspring.proxy.FactoryBean;
import top.orosirian.myspring.utils.BeansException;
import top.orosirian.myspring.utils.ClassUtils;;

@SuppressWarnings("unchecked")
@Getter
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

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
        Object sharedInstance = getSingleton(name);
        if(sharedInstance != null) {
            return (T)getObjectForBeanInstance(name, sharedInstance);
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);
        return (T) getObjectForBeanInstance(name, bean);
    }

    private Object getObjectForBeanInstance(String beanName, Object beanInstance) {
        if(!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = getCachedObjectForFactoryBean(beanName);
        if(object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(beanName, factoryBean);
        }
        return object;
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
