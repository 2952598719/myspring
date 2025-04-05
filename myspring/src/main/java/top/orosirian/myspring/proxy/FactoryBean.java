package top.orosirian.myspring.proxy;

public interface FactoryBean<T> {

    T getObject() throws Exception;
    
    Class<?> getObjectType();

    boolean isSingleton();

}
