package top.orosirian.myspring.io.resource;

public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";     // 原来的注释：Pseudo URL prefix for loading from the class path: "classpath:"

    Resource getResource(String location);
    
}
