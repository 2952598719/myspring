package top.orosirian.myspring.definition;

import lombok.Data;

@Data
@SuppressWarnings({"rawtypes"})
public class BeanDefinition {
    
    private Class beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = (propertyValues == null) ? new PropertyValues() : propertyValues;
    }

}
