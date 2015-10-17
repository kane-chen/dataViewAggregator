package cn.kane.web.view.aggregator.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.PageDefinition;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.pojo.definition.WidgetDefinition;
import cn.kane.web.view.aggregator.pojo.model.Page;
import cn.kane.web.view.aggregator.service.loader.PageLoader;
import cn.kane.web.view.aggregator.service.manager.PageDefinitionManager;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import cn.kane.web.view.aggregator.service.manager.WidgetDefinitionManager;

public class CachedPageLoaderTest extends TestCase {

    private PageLoader pageLoader ;
    private PageDefinitionManager pageDefinitionManager ;
    private WidgetDefinitionManager widgetDefinitionManager ;
    private StringResourceDefinitionManager stringResourceManager ;
    private DefinitionKey key ;
    
    @SuppressWarnings("resource")
    public void setUp() throws IOException{
        ApplicationContext appContext = new ClassPathXmlApplicationContext("/resource/loader/page-loader-test.xml");
        stringResourceManager = (StringResourceDefinitionManager)appContext.getBean("stringResourceDefinitionManager");
        widgetDefinitionManager = (WidgetDefinitionManager)appContext.getBean("widgetDefinitionManager") ;
        pageDefinitionManager = (PageDefinitionManager) appContext.getBean("pageDefinitionManager") ;
        pageLoader = (PageLoader) appContext.getBean("pageLoader");
        key = this.buildKey("page", "pageName", "1") ;
    }
    
    public void testLoad() throws IOException{
        Page page = pageLoader.getResourceByKey(key) ;
        Assert.assertEquals(this.getCodeInFile(MockStringResourceDefinitionManager.layoutFile), page.getLayout());
        Assert.assertEquals(this.getCodeInFile(MockStringResourceDefinitionManager.cssFile), page.getCss());
        Assert.assertEquals(this.getCodeInFile(MockStringResourceDefinitionManager.jsFile), page.getJs());
    }
    
    public void testUpdated() throws IOException{
        Page page = pageLoader.getResourceByKey(key) ;
        Page page1 = pageLoader.getResourceByKey(key) ;
        Assert.assertEquals(page, page1);
        //updated
        StringResourceDefinition dataTempDef2 = this.buildResource("dataTemplate", "dataTemplateName", "1", this.getCodeInFile(MockStringResourceDefinitionManager.dataTemplateFile2)) ;
        stringResourceManager.add(dataTempDef2) ;
        WidgetDefinition widget2 = this.buildWidgetDefinition(this.buildKey("widget", "widgetName2", "1")) ;
        widget2.setDataTemplateDefinition(dataTempDef2.getKey());
        widgetDefinitionManager.add(widget2) ;
        PageDefinition pageDef = pageDefinitionManager.get(key) ;
        pageDef.getWidgetDefinitions().add(widget2.getKey()) ;
        pageDefinitionManager.edit(pageDef) ;
        Page page2 = pageLoader.getResourceBykeyWithoutCache(key) ;
        Assert.assertEquals(2,page2.getWidgetKeys().size());
        
    }
    
    private WidgetDefinition buildWidgetDefinition(DefinitionKey key){
        WidgetDefinition definition = new WidgetDefinition() ;
        definition.setKey(key);
        definition.setCssDefinition(this.buildKey("css", "cssName", "1"));
        definition.setJsDefinition(this.buildKey("js", "jsName", "1"));
        definition.setDataTemplateDefinition(this.buildKey("dataTemplate", "dataTemplateName", "1"));
        definition.setViewTemplateDefinition(this.buildKey("viewTemplate", "viewTemplateName", "1"));
        List<DefinitionKey> dataReaderDefs = new ArrayList<DefinitionKey>() ;
        dataReaderDefs.add(this.buildKey("dataReadService", "dataReadServiceName", "1"));
        definition.setDataReaderDefinitions(dataReaderDefs);
        return definition ;
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
