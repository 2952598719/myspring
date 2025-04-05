package top.orosirian.proj.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.orosirian.myspring.aware.ApplicationContextAware;
import top.orosirian.myspring.aware.BeanClassLoaderAware;
import top.orosirian.myspring.aware.BeanFactoryAware;
import top.orosirian.myspring.aware.BeanNameAware;
import top.orosirian.myspring.process.bean.DisposableBean;
import top.orosirian.myspring.process.bean.InitializingBean;
import top.orosirian.myspring.support.basic.BeanFactory;
import top.orosirian.myspring.support.context.ApplicationContext;
import top.orosirian.myspring.utils.BeansException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserService implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    private String uid;
    private String company;
    private String location;
    private UserDao userDao;

    public String queryUserInfo(){
        return userDao.queryUserName(uid) + "," + company + "," + location;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Bean Name is：" + name);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader：" + classLoader);
    }
    
}
