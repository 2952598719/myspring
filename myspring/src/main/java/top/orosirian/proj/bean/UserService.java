package top.orosirian.proj.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    private String uid;

    private UserDao userDao;

    public void queryUserInfo(){
        System.out.println("查询用户信息：" + userDao.queryUserName(uid));
    }
    
}
