package cn.kane.dynamicview.definition;

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
import cn.kane.dynamicview.definition.entity.PageDefinition;
import cn.kane.dynamicview.definition.entity.TemplateDefinition;
import cn.kane.dynamicview.definition.entity.WidgetDefinition;
import cn.kane.dynamicview.definition.service.PageDefinitionManager;
import cn.kane.dynamicview.definition.service.TemplateDefinitionManager;
import cn.kane.dynamicview.definition.service.WidgetDefinitionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/definition-manager-test.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PageDefinitionManagerTest {

	@Autowired
	private TemplateDefinitionManager templateDefinitionManager ;
	@Autowired
	private WidgetDefinitionManager widgetDefinitionManager ;
	@Autowired
	private PageDefinitionManager pageDefinitionManager ;
	
	private DefinitionKey pageKey ;
	private DefinitionKey widgetKey ;
	private DefinitionKey cssKey ;
	private DefinitionKey viewTemplateKey ;
	private String operator = "kane" ;
	
	@Before
	public void setup(){
		pageKey = this.build("page", "pageName", "1") ;
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
	public void test002_addwidget(){
		WidgetDefinition definition = new WidgetDefinition() ;
		definition.setKey(widgetKey);
		definition.setCssDefinition(cssKey);
		definition.setViewTemplateDefinition(viewTemplateKey);
		widgetDefinitionManager.add(definition) ;
	}
	
	@Test
	public void test003_add(){
		PageDefinition page = new PageDefinition() ;
		page.setKey(pageKey);
		List<DefinitionKey> widgetKeys = new ArrayList<DefinitionKey>() ;
		widgetKeys.add(widgetKey);
		page.setWidgetDefinitions(widgetKeys);
		pageDefinitionManager.add(page);
	}
	
	@Test
	public void test004_query(){
		PageDefinition page = pageDefinitionManager.get(pageKey);
		Assert.assertTrue(page.getWidgetDefinitions().contains(widgetKey));
	}
	
	@Test
	public void test005_edit(){
		PageDefinition page = pageDefinitionManager.get(pageKey);
		Assert.assertTrue(page.getWidgetDefinitions().contains(widgetKey));
		page.getWidgetDefinitions().remove(widgetKey) ;
		pageDefinitionManager.edit(page);
		Assert.assertTrue(page.getWidgetDefinitions().isEmpty());
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
