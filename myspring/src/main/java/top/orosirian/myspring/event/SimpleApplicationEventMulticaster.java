package top.orosirian.myspring.event;

import top.orosirian.myspring.support.basic.BeanFactory;

public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        for(final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
    
}
