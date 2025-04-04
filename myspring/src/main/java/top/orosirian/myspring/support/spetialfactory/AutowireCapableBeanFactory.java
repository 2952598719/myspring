package top.orosirian.myspring.support.spetialfactory;

import top.orosirian.myspring.support.basic.BeanFactory;
import top.orosirian.myspring.utils.BeansException;

public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessorsBeforeInitialization(String beanName, Object existingBean) throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(String beanName, Object existingBean) throws BeansException;

}