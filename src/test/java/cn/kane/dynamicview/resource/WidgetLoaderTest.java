package cn.kane.dynamicview.resource;

import java.io.IOException;
import java.io.InputStream;





import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.definition.entity.TemplateDefinition;
import cn.kane.dynamicview.definition.entity.WidgetDefinition;
import cn.kane.dynamicview.definition.service.TemplateDefinitionManager;
import cn.kane.dynamicview.definition.service.WidgetDefinitionManager;
import cn.kane.dynamicview.resource.entity.Widget;
import cn.kane.dynamicview.resource.service.WidgetLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/definition-manager-test.xml"
		,"classpath:/resource-datareader-loader-test.xml"
		,"classpath:/resource-widget-loader-test.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WidgetLoaderTest {

	@Autowired
	private TemplateDefinitionManager templateDefinitionManager ;
	@Autowired
	private WidgetDefinitionManager widgetDefinitionManager ; 
	@Autowired
	private WidgetLoader widgetLoader ;
	
	private DefinitionKey widgetKey ;
	private DefinitionKey cssKey ;
	private DefinitionKey viewTemplateKey ;
	private DefinitionKey dataReaderKey ;
	private String operator = "kane" ;
	
	@Before
	public void init(){
		widgetKey = this.build("widget", "widgetName1", "1") ;
		cssKey = this.build("css", "cssname", "1") ;
		viewTemplateKey = this.build("viewTemplate", "vt-name", "1") ;
		dataReaderKey = this.build("dataReadService", "dataReadServicename", "1") ;
	}
	
	@Test
	public void test001_addtemplate() throws IOException{
		TemplateDefinition cssDef = this.build(cssKey, this.getCodeInFile("/scripts/view/css.txt"), operator);
		templateDefinitionManager.add(cssDef );
		TemplateDefinition viewDef = this.build(viewTemplateKey, this.getCodeInFile("/scripts/view/viewTemplate.txt"), operator);
		templateDefinitionManager.add(viewDef );
		TemplateDefinition dataReaderDef = this.build(dataReaderKey, this.getCodeInFile("/scripts/data/DataReadServiceCode.txt"), operator);
		templateDefinitionManager.add(dataReaderDef );
	}
	
	@Test
	public void test002_addwidget(){
		WidgetDefinition widgetDef = new WidgetDefinition() ;
		widgetDef.setKey(widgetKey);
		widgetDef.setCssDefinition(cssKey);
		List<DefinitionKey> dataReaderDefs = new ArrayList<DefinitionKey>();
		dataReaderDefs.add(dataReaderKey);
		widgetDef.setDataReaderDefinitions(dataReaderDefs );
		widgetDefinitionManager.add(widgetDef);
	}
	
	@Test
	public void test003_load() throws IOException{
		Widget widget = widgetLoader.getResourceByKey(widgetKey);
		Assert.assertEquals(this.getCodeInFile("/scripts/view/css.txt"), widget.getCss());
	}
	
	@Test
	public void test004_edit() throws IOException{
		WidgetDefinition definition  = widgetDefinitionManager.get(widgetKey) ;
		definition.setViewTemplateDefinition(viewTemplateKey);
		widgetDefinitionManager.edit(definition);
		Widget widget = widgetLoader.getResourceBykeyWithoutCache(widgetKey) ;
		Assert.assertEquals(this.getCodeInFile("/scripts/view/viewTemplate.txt"), widget.getViewTemplate());
	}
	
	private DefinitionKey build(String type,String name,String version){
		DefinitionKey key = new DefinitionKey() ;
		key.setType(type);
		key.setName(name);
		key.setVersion(version);
		return key ;
	}
	
	private TemplateDefinition build(DefinitionKey key,String content,String operator){
		TemplateDefinition definition = new TemplateDefinition() ;
		definition.setKey(key);
		definition.setContent(content);
		definition.setOperator(operator);
		return definition ;
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
