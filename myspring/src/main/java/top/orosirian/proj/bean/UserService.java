package top.orosirian.proj.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    private String uid;
    private String company;
    private String location;
    private UserDao userDao;

    public String queryUserInfo(){
        return userDao.queryUserName(uid) + "," + company + "," + location;
    }
    
}
