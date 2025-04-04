package top.orosirian.myspring.io.reader;

import lombok.Getter;
import top.orosirian.myspring.io.resource.DefaultResourceLoader;
import top.orosirian.myspring.io.resource.ResourceLoader;
import top.orosirian.myspring.support.BeanDefinitionRegistry;

@Getter
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }


    
}
