package top.orosirian.myspring.definition;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    // 下面还有getPropertyValue这种打眼一看不用map没天理的函数，为什么PropertyValues非得用List呢
    // 这是为了保持属性输入的顺序，比如有时候A必须先于B初始化，还有为了重复设置同一属性名（说不定有人会这么干呢）
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        propertyValueList.add(pv);
    }

    // 暂时还不懂为什么转换成数组返回
    // 猜测是因为不希望propertyValueList被修改
    public PropertyValue[] getPropertyValues() {
        // 最基础的toArray实现返回Object[]
        // 为了让其返回对应类型数组，可以传入new PropertyValue[0]来调用其重载，这样获取返回类型就很方便
        // 此外，如果传入数组大小合适，就直接把List放到这个数组里，如果传入数组太小，就新建一个和List长度一样的，放入数据并返回
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName) {
        for(PropertyValue pv : this.propertyValueList) {
            if(pv.getName().equals(propertyName)) return pv;
        }
        return null;
    }
    
}
