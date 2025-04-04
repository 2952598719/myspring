package top.orosirian.myspring.factory.support;

import java.lang.reflect.Constructor;

import cn.hutool.core.bean.BeanUtil;
import top.orosirian.myspring.factory.use.BeansException;
import top.orosirian.myspring.factory.use.InstantiationStrategy.InstantiationStrategy;
import top.orosirian.myspring.factory.use.InstantiationStrategy.SimpleInstantiationStrategy;
import top.orosirian.myspring.factory.use.definition.BeanDefinition;
import top.orosirian.myspring.factory.use.definition.BeanReference;
import top.orosirian.myspring.factory.use.definition.PropertyValue;
import top.orosirian.myspring.factory.use.definition.PropertyValues;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean;
        try {
            bean = createBeanInstance(beanName, beanDefinition, args);  // 根据beanDefinition和args得到对应的构造函数，传入args来初始化
            applyPropertyValues(beanName, bean, beanDefinition);        // 填充bean中未被构造器初始化的属性
        } catch (Exception e) {
            throw new BeansException("bean的实例化失败", e);
        }
        addSingleton(beanName, bean);
        return bean;
    }

    protected Object createBeanInstance(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Constructor<?> constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        for(Constructor<?> ctor : declaredConstructors) {
            if(args != null && ctor.getParameterTypes().length == args.length) {    // 这里的校验比较简单，args不为空且参数长度匹配，spring中实际检查代码，更为复杂
                constructorToUse = ctor;
                break;
            }
        }
        return instantiationStrategy.instantiate(beanName, beanDefinition, constructorToUse, args);
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for(PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                String name = propertyValue.getName();      // 属性名
                Object value = propertyValue.getValue();    // 属性值
                // 如果是个引用，就要getBean去获取其实例，如果实例还没创建好，会递归地调用到此处
                // 这里暂时还不考虑循环依赖，默认不会冲突
                if(value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference)value;
                    value = getBean(beanReference.getBeanName());
                }
                BeanUtil.setFieldValue(bean, name, value);
                
            }
        } catch(Exception e) {
            throw new BeansException("设置[" + beanName + "]属性时出错");
        }
    }
    
}
