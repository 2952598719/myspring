package top.orosirian.myspring.support.spetialfactory;

import top.orosirian.myspring.processor.BeanPostProcessor;
import top.orosirian.myspring.support.basic.SingletonBeanRegistry;

public interface ConfigurableBeanFactory extends HierachicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
    
}
