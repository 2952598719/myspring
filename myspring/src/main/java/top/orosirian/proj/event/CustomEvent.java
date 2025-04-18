package top.orosirian.proj.event;

import lombok.Getter;
import lombok.Setter;
import top.orosirian.myspring.event.ApplicationContextEvent;

@Getter
@Setter
public class CustomEvent extends ApplicationContextEvent {

    private Long id;
    private String message;

    public CustomEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }
    
}
