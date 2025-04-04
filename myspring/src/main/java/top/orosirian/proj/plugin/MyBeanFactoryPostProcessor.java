package top.orosirian.proj.plugin;

import top.orosirian.myspring.definition.BeanDefinition;
import top.orosirian.myspring.definition.PropertyValue;
import top.orosirian.myspring.definition.PropertyValues;
import top.orosirian.myspring.processor.BeanFactoryPostProcessor;
import top.orosirian.myspring.support.spetialfactory.ConfigurableListableBeanFactory;
import top.orosirian.myspring.utils.BeansException;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues PropertyValues = beanDefinition.getPropertyValues();
        PropertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
        System.out.println("修改BeanDefinition成功");
    }
    
}
