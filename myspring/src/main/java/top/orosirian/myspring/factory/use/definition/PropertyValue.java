package top.orosirian.myspring.factory.use.definition;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertyValue {

    private final String name;

    private final Object value;
    
}
