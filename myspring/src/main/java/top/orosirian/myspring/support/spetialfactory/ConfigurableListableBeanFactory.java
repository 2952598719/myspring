package top.orosirian.myspring.support.spetialfactory;

import top.orosirian.myspring.definition.BeanDefinition;
import top.orosirian.myspring.utils.BeansException;

public interface ConfigurableListableBeanFactory extends AutowireCapableBeanFactory, ListableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;
    
}
