package top.orosirian.proj;

import top.orosirian.myspring.DefaultListableBeanFactory;
import top.orosirian.myspring.io.reader.XmlBeanDefinitionReader;
import top.orosirian.proj.bean.UserService;

public class Main {

    public static void main(String[] args) {
        // 1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.读取配置文件，注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");
        // 3.获取Bean对象，调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        System.out.println(userService.queryUserInfo());

    }


}
