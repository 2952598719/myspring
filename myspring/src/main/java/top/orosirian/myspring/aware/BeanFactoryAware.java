package top.orosirian.myspring.aware;

import top.orosirian.myspring.support.basic.BeanFactory;
import top.orosirian.myspring.utils.BeansException;

public interface BeanFactoryAware {
    
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
