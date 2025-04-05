package top.orosirian.myspring.process.bean;

import java.lang.reflect.Method;

import cn.hutool.core.util.StrUtil;
import top.orosirian.myspring.definition.BeanDefinition;
import top.orosirian.myspring.utils.BeansException;

public class DisposableBeanAdapter implements DisposableBean {

    private final String beanName;
    private final Object bean;
    private String destroyMethodName;

    public DisposableBeanAdapter(String beanName, Object bean, BeanDefinition beanDefinition) {
        this.beanName = beanName;
        this.bean = bean;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();     // destroy-method
    }

    @Override
    // @PreDestroy -> destroy() -> destroy-method
    public void destroy() throws Exception {
        // 1.类型转换bean到InitializingBean
        if(bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        // 2.通过反射调用初始化方法
        if(StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && this.destroyMethodName.equals("destroy"))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if(destroyMethod == null) {
                throw new BeansException("找不到bean" + beanName + "上名为" + destroyMethod + "的摧毁方法");
            }
            destroyMethod.invoke(bean);
        }
    }
    
}
