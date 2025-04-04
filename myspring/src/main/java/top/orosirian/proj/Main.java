package top.orosirian.proj;

import top.orosirian.myspring.DefaultListableBeanFactory;
import top.orosirian.myspring.io.reader.XmlBeanDefinitionReader;
import top.orosirian.proj.bean.UserService;
import top.orosirian.proj.plugin.MyBeanFactoryPostProcessor;
import top.orosirian.proj.plugin.MyBeanPostProcessor;

public class Main {

    public static void main(String[] args) {
        // 1.初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.读取配置文件，注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");
        // 3.1 BeanDefinition 加载完成后，Bean实例化前，修改BeanDefinition
        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        // 3.2 给Bean增加一个后处理器，在bean实例化后执行
        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);    // 注意，这是给beanFactory加上的，有几个bean，这个postProcessor就会调用
        System.out.println("增加BeanPostProcessor成功");
        // 4.获取Bean对象，调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        System.out.println(userService.queryUserInfo());

    }


}
