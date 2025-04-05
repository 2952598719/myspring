package top.orosirian.proj.bean;

import java.util.HashMap;
import java.util.Map;

// 这个bean的内容全是static，全由JVM负责执行初始化
public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();

    public void initDataMethod(){
        System.out.println("执行：userDao.init-method");
        hashMap.put("10001", "小傅哥");
        hashMap.put("10002", "八杯水");
        hashMap.put("10003", "阿毛");
    }

    public void destroyDataMethod(){
        System.out.println("执行：userDao.destroy-method");     // 而不是实现DisposableBean并重写destroy()
        hashMap.clear();
    }

    public String queryUserName(String uid) {
        return hashMap.get(uid);
    }
    
}
