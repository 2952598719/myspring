package top.orosirian.myspring.definition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertyValue {

    private final String name;

    private final Object value;
    
}
