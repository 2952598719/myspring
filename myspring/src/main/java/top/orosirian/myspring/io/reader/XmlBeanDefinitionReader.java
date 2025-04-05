package top.orosirian.myspring.io.reader;

import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import top.orosirian.myspring.definition.BeanDefinition;
import top.orosirian.myspring.definition.BeanReference;
import top.orosirian.myspring.definition.PropertyValue;
import top.orosirian.myspring.io.resource.Resource;
import top.orosirian.myspring.io.resource.ResourceLoader;
import top.orosirian.myspring.support.basic.BeanDefinitionRegistry;
import top.orosirian.myspring.utils.BeansException;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for(String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) {
        for(Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();    // 不用管具体是哪个ResourceLoader加载
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            InputStream inputStream = resource.getInputStream();
            doLoadBeanDefinitions(inputStream);
        } catch(IOException | ClassNotFoundException e) {
            throw new BeansException("从xml文档" + resource + "转换过程中出错", e);
        }
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for(int i = 0; i <= childNodes.getLength() - 1; i++) {
            if(!(childNodes.item(i) instanceof Element)) continue;  // 如果不是元素，就跳过
            if(!childNodes.item(i).getNodeName().equals("bean")) continue;  // 如果这个xml元素的名字不是bean，就不是想要加载的，也跳过

            // 1.解析元素
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethod = bean.getAttribute("init-method");
            String destroyMethod = bean.getAttribute("destroy-method");

            // 获取class和beanName
            Class<?> clazz = Class.forName(className);
            String beanName = StrUtil.isEmpty(id) ? name : id;   // isEmpty：null，""
            if(StrUtil.isEmpty(beanName)) {                     // 如果id和name都为空，就根据class获取
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 2.根据获取的class定义和填充bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethod);

            for(int j = 0; j <= bean.getChildNodes().getLength() - 1; j++) {
                if(!(bean.getChildNodes().item(j) instanceof Element)) continue;
                if(!bean.getChildNodes().item(j).getNodeName().equals("property")) continue;
                // 说明此时是一个属性元素，需要填充进去
                Element property = (Element) bean.getChildNodes().item(j);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");      // 表明这是个引用对象
                Object value = StrUtil.isEmpty(attrRef) ? attrValue : new BeanReference(attrRef);
                PropertyValue pv = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(pv);
            }
            if(getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("重复的beanName[" + beanName + "]");
            }
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }
    
}
