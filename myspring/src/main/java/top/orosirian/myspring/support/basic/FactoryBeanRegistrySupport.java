package top.orosirian.myspring.support.basic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import top.orosirian.myspring.proxy.FactoryBean;
import top.orosirian.myspring.utils.BeansException;

@SuppressWarnings("rawtypes")
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    // 从缓存中拿对应对象，如果缓存存的是NULL_OBJECT，说明getObjectFromFactoryBean放入时就发现获取到的FactoryBean内为空，因此也返回null
    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object == NULL_OBJECT) ? null : object;
    }

    // 如果不是singleton(else),则从FactoryBean拿出就可以返回了
    // 如果是singleton,则从FactoryBean中拿出还要放入缓存中。为了不放入null，在object为null时放入NULL_OBJECT
    protected Object getObjectFromFactoryBean(String beanName, FactoryBean factory) {
        if(factory.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if(object == null) {
                object = doGetObjectFromFactoryBean(beanName, factory);
                this.factoryBeanObjectCache.put(beanName, (object == null) ? NULL_OBJECT : object);
            }
            return (object == null) ? null : object;
        } else {
            return doGetObjectFromFactoryBean(beanName, factory);
        }
    }

    private Object doGetObjectFromFactoryBean(final String beanName, final FactoryBean factory) {
        try {
            return factory.getObject();
        } catch(Exception e) {
            throw new BeansException("创建[" + beanName + "]时FactoryBean抛出异常", e);
        }
    }
    
}
