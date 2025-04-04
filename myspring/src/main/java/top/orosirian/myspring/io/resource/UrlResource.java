package top.orosirian.myspring.io.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import cn.hutool.core.lang.Assert;

public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(URL url){
        Assert.notNull(url, "URL不能为空");
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection conn = this.url.openConnection();
        try {
            return conn.getInputStream();
        } catch(IOException ex) {
            if(conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).disconnect();
            }
            throw ex;
        }
    }
    
}
