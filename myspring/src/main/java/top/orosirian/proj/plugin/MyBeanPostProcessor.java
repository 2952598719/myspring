package top.orosirian.proj.plugin;

import top.orosirian.myspring.processor.BeanPostProcessor;
import top.orosirian.myspring.utils.BeansException;
import top.orosirian.proj.bean.UserService;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) throws BeansException {
        if(beanName.equals("userService")) {
            UserService userService = (UserService)bean;
            userService.setLocation("改为：北京");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) throws BeansException {
        System.out.println("初始化后处理");
        return bean;
    }
    
}
