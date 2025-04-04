package top.orosirian.myspring.io.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import cn.hutool.core.lang.Assert;
import top.orosirian.myspring.utils.ClassUtils;

// 输入字节码得到bean
public class ClassPathResource implements Resource {    

    private final String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, (ClassLoader)null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, "路径不能为空");
        this.path = path;
        this.classLoader = (classLoader == null) ? ClassUtils.getDefaultClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = classLoader.getResourceAsStream(path);
        if(is == null) throw new FileNotFoundException("无法打开" + this.path + ", 文件不存在");
        return is;
    }
    
}
