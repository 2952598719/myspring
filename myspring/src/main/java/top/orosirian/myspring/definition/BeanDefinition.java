package top.orosirian.myspring.definition;

import lombok.Data;
import top.orosirian.myspring.support.spetialfactory.ConfigurableBeanFactory;

@Data
@SuppressWarnings({"rawtypes"})
public class BeanDefinition {
    
    private Class beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    private String scope = ConfigurableBeanFactory.SCOPE_SINGLETON;     // 默认是单例作用域

    private boolean singleton = true;

    private boolean prototype = false;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = (propertyValues == null) ? new PropertyValues() : propertyValues;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = scope.equals(ConfigurableBeanFactory.SCOPE_SINGLETON);
        this.prototype = scope.equals(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
    }

}
