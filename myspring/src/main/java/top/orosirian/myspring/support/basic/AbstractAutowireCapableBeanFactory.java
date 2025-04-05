package top.orosirian.myspring.support.basic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import top.orosirian.myspring.InstantiationStrategy.InstantiationStrategy;
import top.orosirian.myspring.InstantiationStrategy.SimpleInstantiationStrategy;
import top.orosirian.myspring.aware.Aware;
import top.orosirian.myspring.aware.BeanClassLoaderAware;
import top.orosirian.myspring.aware.BeanFactoryAware;
import top.orosirian.myspring.aware.BeanNameAware;
import top.orosirian.myspring.definition.BeanDefinition;
import top.orosirian.myspring.definition.BeanReference;
import top.orosirian.myspring.definition.PropertyValue;
import top.orosirian.myspring.definition.PropertyValues;
import top.orosirian.myspring.process.bean.DisposableBean;
import top.orosirian.myspring.process.bean.DisposableBeanAdapter;
import top.orosirian.myspring.process.bean.InitializingBean;
import top.orosirian.myspring.process.processor.BeanPostProcessor;
import top.orosirian.myspring.support.spetialfactory.AutowireCapableBeanFactory;
import top.orosirian.myspring.utils.BeansException;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /**
     * Bean的创建与获取
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean;
        try {
            bean = createBeanInstance(beanName, beanDefinition, args);  // 根据beanDefinition和args得到对应的构造函数，传入args来初始化
            applyPropertyValues(beanName, bean, beanDefinition);        // 填充bean中未被构造器初始化的属性
            bean = initializeBean(beanName, bean, beanDefinition);      // 执行bean的前置处理、初始化、后置处理
        } catch (Exception e) {
            throw new BeansException("bean的实例化失败", e);
        }
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        addSingleton(beanName, bean);
        return bean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if(bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(beanName, bean, beanDefinition));
        }
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


    /**
     * Bean的属性填充
     */
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
    

    /**
     * 执行bean的前后处理
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        if(bean instanceof Aware) {
            if(bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if(bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if(bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(beanName, bean);
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch(Exception e) {
            throw new BeansException("bean[" + beanName + "]的初始化方法调用失败");
        }
        wrappedBean = applyBeanPostProcessorsAfterInitialization(beanName, bean);
        return wrappedBean;
    }
    @SuppressWarnings("unchecked")
    // @PostConstruct -> afterPropertiesSet() -> init-method
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 1.类型转换bean到InitializingBean
        if(bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        // 2.通过反射调用初始化方法，要判断init-method是否为afterPropertiesSet，防止重复调用afterPropertiesSet
        String initMethodName = beanDefinition.getInitMethodName();
        if(StrUtil.isNotEmpty(initMethodName) && !(bean instanceof InitializingBean && initMethodName.equals("afterPropertiesSet"))) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if(initMethod == null) {
                throw new BeansException("找不到bean" + beanName + "上名为" + initMethodName + "的初始化方法");
            }
            initMethod.invoke(bean);
        }
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(String beanName, Object existingBean) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(beanName, result);
            if(current == null) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(String beanName, Object existingBean) throws BeansException {
        Object result = existingBean;
        for(BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(beanName, result);
            if(current == null) return result;
            result = current;
        }
        return result;
    }

}
