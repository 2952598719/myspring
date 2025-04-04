package top.orosirian.myspring.factory.use.InstantiationStrategy;

import java.lang.reflect.Constructor;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import top.orosirian.myspring.factory.use.BeansException;
import top.orosirian.myspring.factory.use.definition.BeanDefinition;

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor<?> ctor, Object[] args) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(NoOp.INSTANCE);    // setCallback必须有个参数，因此传入NoOp.INSTANCE表示没有操作
        if(ctor == null) return enhancer.create();                      // 调用空构造函数
        else return enhancer.create(ctor.getParameterTypes(), args);    // 调用对应参数类型的构造函数，并把参数传进去
    }
    
    
}
