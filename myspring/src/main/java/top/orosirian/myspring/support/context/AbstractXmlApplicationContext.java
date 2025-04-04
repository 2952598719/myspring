package top.orosirian.myspring.support.context;

import top.orosirian.myspring.io.reader.XmlBeanDefinitionReader;
import top.orosirian.myspring.support.DefaultListableBeanFactory;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        // 所以AbstractXmlApplicationContext -> AbstractRefreshableApplicationContext -> AbstractApplicationContext extends DefaultResourceLoader
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if(configLocations != null) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();

}
