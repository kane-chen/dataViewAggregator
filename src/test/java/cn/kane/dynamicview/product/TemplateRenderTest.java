package cn.kane.dynamicview.product;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
import cn.kane.dynamicview.product.render.view.TemplateRender;
import cn.kane.dynamicview.util.DefinitionKeyUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/definition-manager-test.xml"
		,"classpath:/product-template-render-test.xml"
		,"classpath:/resource-datareader-loader-test.xml"
		,"classpath:/resource-widget-loader-test.xml"
		,"classpath:/resource-page-loader-test.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TemplateRenderTest {

	@Autowired
	private TemplateRender templateRender ;
	@Autowired
	private TemplateDefinitionManager templateDefinitionManager ;

	private DefinitionKey viewTemplateKey ;
	private DefinitionKey dataTemplateKey ;
	private DefinitionKey dataReaderKey ;
	
	@Before
	public void init(){
		viewTemplateKey = this.build("viewTemplate", "vt-name", "1") ;
		dataTemplateKey = this.build("dataTemplate", "dt-name", "1") ;
		dataReaderKey = this.build("dataReadService", "dataReadServicename", "1") ;
	}
	
	@Test
	public void test001_adddef(){
		TemplateDefinition dtdef = this.build(dataTemplateKey, "#set($key1='value1')", null) ;
		templateDefinitionManager.add(dtdef) ;
		TemplateDefinition vtdef = this.build(viewTemplateKey, "$key1", null) ;
		templateDefinitionManager.add(vtdef) ;
	}
	
	@Test
	public void test002_render(){
		Map<String, Object> param = new HashMap<String, Object>(1);
		templateRender.render(dataTemplateKey, param) ;
		Assert.assertTrue(param.containsKey("key1"));
	}
	
	@Test
	public void test003_tools(){
		//edit(tool)
		TemplateDefinition dtdef = templateDefinitionManager.get(dataTemplateKey) ;
		String formatted = DefinitionKeyUtils.format(dataTemplateKey) ;
		dtdef.setContent("$DefinitionKeyUtils.formatStr('dataTemplate','dt-name','1')");
		//render
		Map<String, Object> param = new HashMap<String, Object>(1);
		String view = templateRender.render(dataTemplateKey, param) ;
		Assert.assertEquals(formatted, view);
	}
	
	@Test
	public void test004_tool_set(){
		//edit(tool&set)
		TemplateDefinition dtdef = templateDefinitionManager.get(dataTemplateKey) ;
		String formatted = DefinitionKeyUtils.format(dataTemplateKey) ;
		dtdef.setContent("#set($key1=\"$DefinitionKeyUtils.formatStr('dataTemplate','dt-name','1')\")");
		templateDefinitionManager.edit(dtdef);
		//render
		Map<String, Object> param = new HashMap<String, Object>(1);
		templateRender.render(dataTemplateKey, param) ;
		Assert.assertEquals(formatted, param.get("key1"));
	}
	
	@Test
	public void test005_directive() throws IOException{
		TemplateDefinition dataReaderDef = this.build(dataReaderKey, this.getCodeInFile("/scripts/data/DataReadServiceCode.txt"), null);
		templateDefinitionManager.add(dataReaderDef );
		//edit(directive)
		TemplateDefinition dtdef = templateDefinitionManager.get(dataTemplateKey) ;
		dtdef.setContent("#data($DefinitionKeyUtils.formatStr('dataReadService','dataReadServicename','1'),$!param,'key1')");
		templateDefinitionManager.edit(dtdef);
		//render
		Map<String, Object> param = new HashMap<String, Object>(1);
		templateRender.render(dataTemplateKey, param) ;
		Assert.assertEquals("HELLO-WORLD", param.get("key1"));
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
