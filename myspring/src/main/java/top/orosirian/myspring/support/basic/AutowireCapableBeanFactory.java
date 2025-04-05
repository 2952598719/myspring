package top.orosirian.myspring.support.basic;

import top.orosirian.myspring.utils.BeansException;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessorsBeforeInitialization(String beanName, Object existingBean) throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(String beanName, Object existingBean) throws BeansException;

}