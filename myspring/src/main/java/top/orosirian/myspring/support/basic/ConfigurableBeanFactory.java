package top.orosirian.myspring.support.basic;

import top.orosirian.myspring.process.processor.BeanPostProcessor;

public interface ConfigurableBeanFactory extends HierachicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
    
}
