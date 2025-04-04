package top.orosirian.proj.bean;

import java.util.HashMap;
import java.util.Map;

// 这个bean的内容全是static，全由JVM负责执行初始化
public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "特朗普");
        hashMap.put("10002", "习近平");
        hashMap.put("10003", "普京");
    }

    public String queryUserName(String uid) {
        return hashMap.get(uid);
    }
    
}
