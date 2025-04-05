package top.orosirian.myspring.process.bean;

public interface InitializingBean {

    // 在属性填充后进行，因为是用户自定义逻辑，所以抛出Exception而不是BeansException
    void afterPropertiesSet() throws Exception;
    
}
