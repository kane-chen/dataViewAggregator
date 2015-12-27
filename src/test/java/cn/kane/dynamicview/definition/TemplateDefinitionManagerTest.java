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
import cn.kane.dynamicview.definition.service.TemplateDefinitionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/definition-manager-test.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TemplateDefinitionManagerTest {

	@Autowired
	private TemplateDefinitionManager templateDefinitionManager ;

	private static DefinitionKey key ;
	private String operator = "kane";
	private String content = "content";
	
	@Before
	public void setup(){
		key = this.build("string", "template", "1") ;
	}
	
	private DefinitionKey build(String type,String name,String version){
		DefinitionKey key = new DefinitionKey() ;
		key.setType(type);
		key.setName(name);
		key.setVersion(version);
		return key ;
	}
	
	@Test
	public void test001_add(){
		TemplateDefinition definition = new TemplateDefinition() ;
		definition.setKey(key);
		definition.setOperator(operator);
		definition.setContent(content);
		templateDefinitionManager.add(definition);
	}
	
	@Test
	public void test002_query(){
		TemplateDefinition definition = templateDefinitionManager.get(key) ;
		Assert.assertEquals(operator, definition.getOperator());
		Assert.assertEquals(content, definition.getContent());
	}
	
	@Test
	public void test003_edit(){
		TemplateDefinition definition = templateDefinitionManager.get(key) ;
		String newContent = "newcontent" ;
		String newOperator = "newoperator" ;
		definition.setOperator(newOperator);
		definition.setContent(newContent);
		templateDefinitionManager.edit(definition) ;
		TemplateDefinition queryDefinition = templateDefinitionManager.get(key) ;
		Assert.assertEquals(newOperator, queryDefinition.getOperator());
		Assert.assertEquals(newContent, queryDefinition.getContent());
	}
	
	@Test
	public void test004_remove(){
		templateDefinitionManager.remove(key);
		TemplateDefinition queryDefinition = templateDefinitionManager.get(key) ;
		Assert.assertNull(queryDefinition);
	}
}
