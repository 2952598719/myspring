package top.orosirian.myspring.support.context;

import java.util.Map;

import top.orosirian.myspring.io.resource.DefaultResourceLoader;
import top.orosirian.myspring.process.processor.ApplicationContextAwareProcessor;
import top.orosirian.myspring.process.processor.BeanFactoryPostProcessor;
import top.orosirian.myspring.process.processor.BeanPostProcessor;
import top.orosirian.myspring.support.spetialfactory.ConfigurableListableBeanFactory;
import top.orosirian.myspring.utils.BeansException;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 1. 创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        // 2.获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        // 3. 自行定义用于感知上下文的BeanPostProcessor，不走createBean流程
        // 此处的目的是为了让所有BeanFactoryPostProcessor、BeanPostProcessor都能够获取到上下文
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        // 4.在Bean实例化之前，执行xml中以bean格式定义的BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);
        // 5.注册BeanPostProcessor
        registerBeanPostProcessors(beanFactory);
        // 6.提前实例化单例的Bean对象
        beanFactory.preInstantiateSingletons();
    }

    protected abstract void refreshBeanFactory() throws BeansException;     // 留待子类实现

    protected abstract ConfigurableListableBeanFactory getBeanFactory();    // 留待子类实现
    
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for(BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for(BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }

    // 下面这些重写都没什么意思。就是这个Context用什么beanFactory从中获取

    // AbstractApplicationContext -> ConfigurableApplicationContext -> ApplicationContext -> ListableBeanFactory
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    // AbstractApplicationContext -> ConfigurableApplicationContext -> ApplicationContext -> ListableBeanFactory -> BeanFactory
    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }
    
}
