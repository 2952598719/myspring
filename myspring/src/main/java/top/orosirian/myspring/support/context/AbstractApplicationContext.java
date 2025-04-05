package top.orosirian.myspring.support.context;

import java.util.Collection;
import java.util.Map;

import top.orosirian.myspring.event.ApplicationEvent;
import top.orosirian.myspring.event.ApplicationEventMulticaster;
import top.orosirian.myspring.event.ApplicationListener;
import top.orosirian.myspring.event.ContextClosedEvent;
import top.orosirian.myspring.event.ContextRefreshedEvent;
import top.orosirian.myspring.event.SimpleApplicationEventMulticaster;
import top.orosirian.myspring.io.resource.DefaultResourceLoader;
import top.orosirian.myspring.process.processor.ApplicationContextAwareProcessor;
import top.orosirian.myspring.process.processor.BeanFactoryPostProcessor;
import top.orosirian.myspring.process.processor.BeanPostProcessor;
import top.orosirian.myspring.support.basic.ConfigurableListableBeanFactory;
import top.orosirian.myspring.utils.BeansException;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

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
        // 6.初始化事件发布者
        initApplicationEventMulticaster();
        // 7.注册事件监听器();
        registerListeners();
        // 8.提前实例化单例的Bean对象
        beanFactory.preInstantiateSingletons();
        // 9.发布容器刷新完成事件
        finishRefresh();
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
        // 发布容器关闭event
        publishEvent(new ContextClosedEvent(this));
        // 销毁单例bean
        getBeanFactory().destroySingletons();
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    @SuppressWarnings("rawtypes")
    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for(ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
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
