package top.orosirian.proj.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import top.orosirian.myspring.proxy.FactoryBean;

public class ProxyBeanFactory implements FactoryBean<IUserDao> {
    
    @Override
    public IUserDao getObject() throws Exception {
        // 这个lambda表达式类型转换成InvocationHandler，其内容变成invoke的内容
        InvocationHandler handler = (proxy, method, args) -> {
            if(method.getName().equals("toString")) return this.toString();     // toString照旧调用

            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("10001", "Luffy");
            hashMap.put("10002", "Zoro");
            hashMap.put("10003", "Sanji");
            return "你被代理了 " + method.getName() + ":" + hashMap.get(args[0].toString());
        };
        IUserDao proxy = (IUserDao) Proxy.newProxyInstance(
                                Thread.currentThread().getContextClassLoader(), 
                                new Class[]{IUserDao.class}, 
                                handler
                            );
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
