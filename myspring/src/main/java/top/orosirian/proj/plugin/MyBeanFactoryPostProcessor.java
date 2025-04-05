package top.orosirian.proj.plugin;

import top.orosirian.myspring.definition.BeanDefinition;
import top.orosirian.myspring.definition.PropertyValue;
import top.orosirian.myspring.definition.PropertyValues;
import top.orosirian.myspring.process.processor.BeanFactoryPostProcessor;
import top.orosirian.myspring.support.basic.ConfigurableListableBeanFactory;
import top.orosirian.myspring.utils.BeansException;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("modify bean definition");
    }
    
}
