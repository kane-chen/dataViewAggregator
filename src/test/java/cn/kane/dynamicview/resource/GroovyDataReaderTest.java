package cn.kane.dynamicview.resource;

import java.io.IOException;
import java.io.InputStream;

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
import cn.kane.dynamicview.resource.entity.DataReader;
import cn.kane.dynamicview.resource.service.DataReaderLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/definition-manager-test.xml"
		,"classpath:/resource-datareader-loader-test.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroovyDataReaderTest {
	
	@Autowired
	private TemplateDefinitionManager templateDefinitionManager ;
	@Autowired
	private DataReaderLoader dataReaderLoader ;
	private DefinitionKey dataReaderKey ;
	
	@Before
	public void init(){
		dataReaderKey = new DefinitionKey() ;
		dataReaderKey.setType("dataReadService");
		dataReaderKey.setName("dataReader1");
		dataReaderKey.setVersion("version1");
	}
	
	@Test
	public void test001_adddef() throws IOException{
		TemplateDefinition definition = new TemplateDefinition();
		definition.setKey(dataReaderKey);
		String code = this.getCodeInFile("/scripts/data/DataReadServiceCode.txt");
		definition.setContent(code);
		templateDefinitionManager.add(definition) ;
	}

	@Test
	public void test002_load(){
		DataReader dataReader = dataReaderLoader.getResourceByKey(dataReaderKey) ;
		Object data = dataReader.getData(null) ;
		Assert.assertEquals("HELLO-WORLD", data);
	}
	
	@Test
	public void test003_reload() throws IOException{
		//edit
		TemplateDefinition definition = templateDefinitionManager.get(dataReaderKey) ;
		String newCode = this.getCodeInFile("/scripts/data/DataReadServiceCodeNew.txt");
		definition.setContent(newCode );
		templateDefinitionManager.edit(definition) ;
		//reload
		DataReader dataReader = dataReaderLoader.getResourceBykeyWithoutCache(dataReaderKey) ;
		Object data = dataReader.getData(null) ;
		Assert.assertEquals("HELLO-WORLD-NEW", data);
		
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
