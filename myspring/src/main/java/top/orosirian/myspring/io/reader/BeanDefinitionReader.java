package top.orosirian.myspring.io.reader;

import top.orosirian.myspring.BeansException;
import top.orosirian.myspring.io.resource.Resource;
import top.orosirian.myspring.io.resource.ResourceLoader;
import top.orosirian.myspring.support.BeanDefinitionRegistry;

public interface BeanDefinitionReader {

    ResourceLoader getResourceLoader();

    BeanDefinitionRegistry getRegistry();

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(Resource resource) throws BeansException;
    
}
