package top.orosirian.proj.plugin;

import top.orosirian.myspring.process.processor.BeanPostProcessor;
import top.orosirian.myspring.utils.BeansException;
import top.orosirian.proj.bean.UserService;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) throws BeansException {
        System.out.println("before initialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) throws BeansException {
        System.out.println("after initialization");
        return bean;
    }
    
}
