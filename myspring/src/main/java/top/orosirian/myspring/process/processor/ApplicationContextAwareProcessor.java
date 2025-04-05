package top.orosirian.myspring.process.processor;

import lombok.AllArgsConstructor;
import top.orosirian.myspring.aware.ApplicationContextAware;
import top.orosirian.myspring.support.context.ApplicationContext;
import top.orosirian.myspring.utils.BeansException;


@AllArgsConstructor
// ApplicationContext不像BeanFactory等等，在初始化Bean时可以很容易获取
// 它需要在refresh操作时，创建一个ApplicationContextAwareProcessor将context放进去，在初始化bean前后执行才能获取
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) throws BeansException {
        if(bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) throws BeansException {
        return bean;
    }

    
    
}
