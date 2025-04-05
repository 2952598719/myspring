package top.orosirian.myspring.aware;

import top.orosirian.myspring.support.context.ApplicationContext;
import top.orosirian.myspring.utils.BeansException;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
    
}
