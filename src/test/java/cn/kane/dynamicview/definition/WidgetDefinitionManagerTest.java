package cn.kane.dynamicview.definition;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/definition-manager-test.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WidgetDefinitionManagerTest {

	@Autowired
	private WidgetDefinitionManager widgetDefinitionManager ;
	@Autowired
	private TemplateDefinitionManager templateDefinitionManager ; 
	
	private DefinitionKey widgetKey ;
	private DefinitionKey cssKey ;
	private DefinitionKey viewTemplateKey ;
	private String operator = "kane" ;
	
	@Before
	public void setup(){
		widgetKey = this.build("widget", "widgetname", "1") ;
		cssKey = this.build("css", "cssName","1") ;
		viewTemplateKey = this.build("viewTemplate", "cssName","1") ;
	}
	
	@Test
	public void test001_addresources(){
		TemplateDefinition css = this.build(cssKey, ".p{color:red}", operator) ;
		templateDefinitionManager.add(css);
		TemplateDefinition viewTemplate = this.build(viewTemplateKey, ".p{color:red}", operator) ;
		templateDefinitionManager.add(viewTemplate) ;
	}
	
	@Test
	public void test002_add(){
		WidgetDefinition definition = new WidgetDefinition() ;
		definition.setKey(widgetKey);
		definition.setCssDefinition(cssKey);
		definition.setViewTemplateDefinition(viewTemplateKey);
		widgetDefinitionManager.add(definition) ;
	}
	
	@Test
	public void test003_query(){
		WidgetDefinition definition = widgetDefinitionManager.get(widgetKey) ;
		Assert.assertEquals(cssKey, definition.getCssDefinition());
		Assert.assertEquals(viewTemplateKey, definition.getViewTemplateDefinition());
	}
	
	@Test
	public void test004_edit(){
		WidgetDefinition definition = widgetDefinitionManager.get(widgetKey) ;
		Assert.assertEquals(cssKey, definition.getCssDefinition());
		definition.setCssDefinition(null);
		widgetDefinitionManager.edit(definition);
		WidgetDefinition editDefinition = widgetDefinitionManager.get(widgetKey) ;
		Assert.assertNull(editDefinition.getCssDefinition());
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
}
