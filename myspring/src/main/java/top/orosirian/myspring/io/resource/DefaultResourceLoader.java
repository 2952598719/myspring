package top.orosirian.myspring.io.resource;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import cn.hutool.core.lang.Assert;

public class DefaultResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "location不能为空");
        if(location.startsWith(CLASSPATH_URL_PREFIX)) {     // 如果以classpath:开头，说明是个.class，需要用ClassPathResource来加载
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {                                           // 否则先用URL试下，报错再用File试下
                URL url = new URI(location).toURL();
                return new UrlResource(url);
            } catch(MalformedURLException | URISyntaxException | IllegalArgumentException e) {   // 两个Exception都表示URL格式不对  
                return new FileSystemResource(location);
            }
        }
    }

}
