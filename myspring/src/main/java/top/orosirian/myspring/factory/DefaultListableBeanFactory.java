package top.orosirian.myspring.factory;

import java.util.HashMap;
import java.util.Map;

import top.orosirian.myspring.factory.support.AbstractAutowireCapableBeanFactory;
import top.orosirian.myspring.factory.support.BeanDefinitionRegistry;
import top.orosirian.myspring.factory.use.BeansException;
import top.orosirian.myspring.factory.use.definition.BeanDefinition;

public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if(beanDefinition == null) throw new BeansException("已定义Bean中无" + beanName);
        return beanDefinition;
    }
    
}
