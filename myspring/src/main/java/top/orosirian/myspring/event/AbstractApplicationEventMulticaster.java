package top.orosirian.myspring.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import lombok.Setter;
import top.orosirian.myspring.aware.BeanFactoryAware;
import top.orosirian.myspring.support.basic.BeanFactory;
import top.orosirian.myspring.utils.BeansException;
import top.orosirian.myspring.utils.ClassUtils;

@Setter
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    // 因为要经常插入删除，所以用LinkedHashSet
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;
    
    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    // 这个函数会遍历所有listener，将符合event类型的listener放入allListeners返回去执行
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        LinkedList<ApplicationListener> allListeners = new LinkedList<ApplicationListener>();
        for(ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if(supportsEvent(listener, event)) allListeners.add(listener);
        }
        return allListeners;
    }

    // 检查Listener是否监听event所属类型
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        // 1.获取class信息
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();
        // 2.如果这个listener用CGLib初始化，则它实际上是通过继承用户listener来构造的，实际要检查的是当前listener的父类
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass.getClass()) ? listenerClass.getSuperclass() : listenerClass;
        // 3.提取泛型接口类型
        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        // 4.根据listener的泛型接口获取对应的event类型
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch(ClassNotFoundException e) {
            throw new BeansException("事件类名错误: " + className);
        }
        // 5.检查真实的event类型是否和eventClassName对应
        return event.getClass().isAssignableFrom(eventClassName);
    }
    
}
