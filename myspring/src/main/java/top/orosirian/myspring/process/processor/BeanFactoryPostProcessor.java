package top.orosirian.myspring.process.processor;

import top.orosirian.myspring.support.basic.ConfigurableListableBeanFactory;
import top.orosirian.myspring.utils.BeansException;

// 所有的BeanDefinition加载后，实例化Bean前，本接口可以修改BeanDefinition
public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
    
}
