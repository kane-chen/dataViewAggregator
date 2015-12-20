package cn.kane.web.view.aggregator.loader;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.pojo.definition.WidgetDefinition;
import cn.kane.web.view.aggregator.pojo.model.Widget;
import cn.kane.web.view.aggregator.service.loader.WidgetLoader;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import cn.kane.web.view.aggregator.service.manager.WidgetDefinitionManager;
import junit.framework.TestCase;

public class CachedWidgetLoaderTest extends TestCase {
    
    private WidgetLoader widgetLoader ;
    private WidgetDefinitionManager widgetDefinitionManager ;
    private StringResourceDefinitionManager stringResourceManager ;
    
    private DefinitionKey key ;

    public void setUp() throws IOException{
        ApplicationContext appContext = new ClassPathXmlApplicationContext("/resource/loader/widget-loader-test.xml");
        stringResourceManager = (StringResourceDefinitionManager)appContext.getBean("stringResourceDefinitionManager");
        widgetDefinitionManager = (WidgetDefinitionManager)appContext.getBean("widgetDefinitionManager") ;
        widgetLoader = (WidgetLoader) appContext.getBean("widgetLoader");
        key = this.buildKey("widget", "widgetName", "1") ;
    }
    
    public void testLoad() throws IOException{
        Widget widget = widgetLoader.getResourceByKey(key) ;
        String dataTemplateContent = this.getCodeInFile(MockStringResourceDefinitionManager.dataTemplateFile) ;
        Assert.assertEquals(dataTemplateContent, widget.getDataTemplate());
    }
    
    public void testUpdated() throws IOException{
        Widget widget = widgetLoader.getResourceByKey(key) ;
        Widget widget1 = widgetLoader.getResourceByKey(key) ;
        Assert.assertEquals(widget, widget1);
        //update-template
        stringResourceManager.add(this.buildResource("dataTemplate", "dataTemplateName2", "1", this.getCodeInFile(MockStringResourceDefinitionManager.dataTemplateFile2)));
        WidgetDefinition widgetDef2 = widgetDefinitionManager.get(key) ;
        widgetDef2.setDataTemplateDefinition(this.buildKey("dataTemplate", "dataTemplateName2", "1"));
        widgetDefinitionManager.edit(widgetDef2) ;
        Widget widget2 = widgetLoader.getResourceBykeyWithoutCache(key) ;
        String dataTemplateContent = this.getCodeInFile(MockStringResourceDefinitionManager.dataTemplateFile2) ;
        Assert.assertEquals(dataTemplateContent, widget2.getDataTemplate());
        //update-mapping
        stringResourceManager.add(this.buildResource("dataReadService", "dataReadServiceName2", "1", this.getCodeInFile(MockStringResourceDefinitionManager.dataReadServiceFile2))) ;
        WidgetDefinition widgetDef3 = widgetDefinitionManager.get(key) ;
        widgetDef3.getDataReaderDefinitions().add(this.buildKey("dataReadService", "dataReadServiceName2", "1")) ;
        widgetDefinitionManager.edit(widgetDef3) ;
        Widget widget3 = widgetLoader.getResourceBykeyWithoutCache(key) ;
        Assert.assertTrue(2 == widget3.getDataReaderKeys().size());
    }
    
    private StringResourceDefinition buildResource(String type,String name,String version,String content){
        StringResourceDefinition resource = new StringResourceDefinition() ;
        resource.setKey(this.buildKey(type, name, version));
        resource.setContent(content);
        return resource ;
    }
    
    private DefinitionKey buildKey(String type,String name,String version){
        DefinitionKey key = new DefinitionKey() ;
        key.setType(type);
        key.setName(name);
        key.setVersion(version);
        return key ;
    }
    
    private String getCodeInFile(String file) throws IOException{
        String result = null ;
        InputStream in = this.getClass().getResourceAsStream(file);
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        result = new String(bytes);
        return result;
    }
}
