package top.orosirian.proj;

import top.orosirian.myspring.factory.DefaultListableBeanFactory;
import top.orosirian.myspring.factory.use.definition.BeanDefinition;
import top.orosirian.myspring.factory.use.definition.BeanReference;
import top.orosirian.myspring.factory.use.definition.PropertyValue;
import top.orosirian.myspring.factory.use.definition.PropertyValues;
import top.orosirian.proj.bean.UserDao;
import top.orosirian.proj.bean.UserService;

public class Main {

    public static void main(String[] args) {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.1 注册UserDao
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));
        // 2.2 设置属性
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uid", "10002"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));
        // 2.3 根据属性注册UserService
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3.获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }
    
}
