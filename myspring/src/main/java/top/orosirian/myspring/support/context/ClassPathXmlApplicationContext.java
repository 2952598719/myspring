package top.orosirian.myspring.support.context;

import lombok.Getter;
import lombok.NoArgsConstructor;
import top.orosirian.myspring.utils.BeansException;

@Getter
@NoArgsConstructor
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext(String configLocations) throws BeansException {
        this(new String[]{configLocations});
    }

    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        refresh();
    }
    
}
