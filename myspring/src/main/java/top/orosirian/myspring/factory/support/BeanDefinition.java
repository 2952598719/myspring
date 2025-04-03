package top.orosirian.myspring.factory.support;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@SuppressWarnings({"rawtypes"})
public class BeanDefinition {
    
    private Class beanClass;

}
