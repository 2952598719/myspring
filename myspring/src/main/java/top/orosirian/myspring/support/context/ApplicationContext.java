package top.orosirian.myspring.support.context;

import top.orosirian.myspring.event.ApplicationEventPublisher;
import top.orosirian.myspring.io.resource.ResourceLoader;
import top.orosirian.myspring.support.basic.HierachicalBeanFactory;
import top.orosirian.myspring.support.basic.ListableBeanFactory;

public interface ApplicationContext extends ListableBeanFactory, HierachicalBeanFactory, ResourceLoader, ApplicationEventPublisher {
    
}
