package top.orosirian.myspring.utils;

public class ClassUtils {

    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch(Throwable ex) {
            cl = ClassUtils.class.getClassLoader();     // 无法使用上下文类加载器时，使用系统默认的类加载器
        }
        return cl;
    }
    
}
