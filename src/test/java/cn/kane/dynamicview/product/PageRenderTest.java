package cn.kane.dynamicview.product;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import cn.kane.dynamicview.definition.entity.PageDefinition;
import cn.kane.dynamicview.definition.entity.TemplateDefinition;
import cn.kane.dynamicview.definition.entity.WidgetDefinition;
import cn.kane.dynamicview.definition.service.PageDefinitionManager;
import cn.kane.dynamicview.definition.service.TemplateDefinitionManager;
import cn.kane.dynamicview.definition.service.WidgetDefinitionManager;
import cn.kane.dynamicview.product.service.ViewRender;
import cn.kane.dynamicview.product.vo.ViewModel;
import cn.kane.dynamicview.resource.entity.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/product-render-test.xml"
		,"classpath:/definition-manager-test.xml"
		,"classpath:/resource-datareader-loader-test.xml"
		,"classpath:/resource-widget-loader-test.xml"
		,"classpath:/resource-page-loader-test.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PageRenderTest {

	@Autowired
	private ViewRender<Page> pageViewRender ;
	@Autowired
	private TemplateDefinitionManager templateDefinitionManager ;
	@Autowired
	private WidgetDefinitionManager widgetDefinitionManager ; 
	@Autowired
	private PageDefinitionManager pageDefinitionManager ;
	
	private DefinitionKey pageKey ;
	private DefinitionKey layoutKey ;
	private DefinitionKey widgetKey ;
	private DefinitionKey cssKey ;
	private DefinitionKey viewTemplateKey ;
	private DefinitionKey dataTemplateKey ;
	private DefinitionKey dataReaderKey ;
	private String operator = "kane" ;
	
	@Before
	public void init(){
		pageKey = this.build("page", "pageName", "1") ;
		layoutKey = this.build("layout", "layoutname", "1") ;
		widgetKey = this.build("widget", "widgetName1", "1") ;
		cssKey = this.build("css", "cssname", "1") ;
		viewTemplateKey = this.build("viewTemplate", "vt-name", "1") ;
		dataTemplateKey = this.build("dataTemplate", "dt-name", "1") ;
		dataReaderKey = this.build("dataReadService", "dataReadServicename", "1") ;
	}
	
	@Test
	public void test001_addtemplate() throws IOException{
		TemplateDefinition cssDef = this.build(cssKey, this.getCodeInFile("/scripts/view/css.txt"), operator);
		templateDefinitionManager.add(cssDef );
		TemplateDefinition viewDef = this.build(viewTemplateKey, this.getCodeInFile("/scripts/view/viewTemplate.txt"), operator);
		templateDefinitionManager.add(viewDef );
		TemplateDefinition dataDef = this.build(dataTemplateKey, this.getCodeInFile("/scripts/view/dataTemplate.txt"), operator);
		templateDefinitionManager.add(dataDef );
		TemplateDefinition dataReaderDef = this.build(dataReaderKey, this.getCodeInFile("/scripts/data/DataReadServiceCode.txt"), operator);
		templateDefinitionManager.add(dataReaderDef );
	}
	
	@Test
	public void test002_addwidget(){
		WidgetDefinition widgetDef = new WidgetDefinition() ;
		widgetDef.setKey(widgetKey);
		widgetDef.setCssDefinition(cssKey);
		widgetDef.setViewTemplateDefinition(viewTemplateKey);
		widgetDef.setDataTemplateDefinition(dataTemplateKey);
		List<DefinitionKey> dataReaderDefs = new ArrayList<DefinitionKey>();
		dataReaderDefs.add(dataReaderKey);
		widgetDef.setDataReaderDefinitions(dataReaderDefs );
		widgetDefinitionManager.add(widgetDef);
	}
	
	@Test
	public void test003_addpage() throws IOException{
		PageDefinition pageDef = new PageDefinition() ;
		pageDef.setKey(pageKey);
		//layout
		TemplateDefinition layoutDef = this.build(layoutKey, this.getCodeInFile("/scripts/view/layout.txt"), operator);
		templateDefinitionManager.add(layoutDef);
		pageDef.setLayoutDefinition(layoutKey);
		//widgets
		List<DefinitionKey> widgetDefinitions = new ArrayList<DefinitionKey>(1);
		widgetDefinitions.add(widgetKey) ;
		pageDef.setWidgetDefinitions(widgetDefinitions);
		pageDefinitionManager.add(pageDef);
	}
	
	@Test
	public void test004_render() throws IOException{
		Map<String, Object> params = new HashMap<String, Object>(1);
		ViewModel viewModel = pageViewRender.reander(pageKey, params );
		Assert.assertEquals(this.getCodeInFile("/scripts/view/css.txt"), viewModel.getCss());
		Assert.assertTrue(viewModel.getContent().indexOf("HELLO-WORLD")>-1);
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
