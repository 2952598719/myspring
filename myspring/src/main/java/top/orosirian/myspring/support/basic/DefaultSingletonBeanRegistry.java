package top.orosirian.myspring.support.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import top.orosirian.myspring.process.bean.DisposableBean;
import top.orosirian.myspring.utils.BeansException;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected static final Object NULL_OBJECT = new Object();

    private final Map<String, Object> singletonObjects = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    protected void addSingleton(String beanName, Object singletonObject) {  // 允许同文件夹、子类调用
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    // Context销毁时，调用了destroySingletons，来调用每个bean的destroy和destroy-method方法
    // 注意，这里是按照创建的相反顺序执行销毁
    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        Object[] disposableBeanNames = keySet.toArray();
        for(int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch(Exception e) {
                throw new BeansException("bean[" + beanName + "]的销毁操作抛出异常", e);
            }
        }
    }
    
}
