package cn.kane.web.view.aggregator.loader;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.pojo.model.DataReader;
import cn.kane.web.view.aggregator.service.inner.DataReadService;
import cn.kane.web.view.aggregator.service.loader.DataReaderLoader;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import cn.kane.web.view.aggregator.util.groovy.GroovyBeanFcatory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/resource/loader/datareader-loader-test.xml"})

public class GroovyDataReaderLoaderTest{
	@Autowired
    private StringResourceDefinitionManager stringResourceManager ;
    @Autowired
	private DataReaderLoader dataReaderLoader ;
    @Autowired
    private GroovyBeanFcatory groovyBeanFactory ;
    
    private static final String dataReadServiceFile2 = "/resource/loader/script/DataReadServiceCodeNew.txt";
    
    private DefinitionKey key ;
    
    @Before
    public void setUp() throws IOException{
        //key
        key = new DefinitionKey() ;
        key.setType("dataReadService");
        key.setName("dataReadServiceName");
        key.setVersion("1");
    }
    
    @Test
    public void testLoad(){
        DataReader dataReader = dataReaderLoader.getResourceByKey(key) ;
        Assert.assertTrue("HELLO-WORLD".equals(dataReader.getDataReadService().getData(null))) ;
    }
    
    @Test
    public void testUpdated() throws IOException{
        DataReader dataReader = dataReaderLoader.getResourceByKey(key) ;
        //use cache
        DataReader dataReader2 = dataReaderLoader.getResourceByKey(key) ;
        Assert.assertEquals(dataReader, dataReader2);
        //update
        StringResourceDefinition definition = stringResourceManager.get(key);
        definition.setContent(this.getCodeInFile(dataReadServiceFile2));
        boolean editResult = stringResourceManager.edit(definition);
        Assert.assertTrue(editResult);
        //not-refresh
        DataReader dataReader3 = dataReaderLoader.getResourceByKey(key) ;
        Assert.assertEquals(dataReader, dataReader3);
        //refresh
        DataReader dataReader4 = dataReaderLoader.getResourceBykeyWithoutCache(key) ;
        Assert.assertTrue(dataReader4 != dataReader);
    }
    
    private String getCodeInFile(String file) throws IOException{
        String result = null ;
        InputStream in = this.getClass().getResourceAsStream(file);
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        result = new String(bytes);
        return result;
    }
    
    public void testGroovyClassLoader() throws IOException{
    	String content = this.getCodeInFile("/templates/scripts/CommonDataReader.dat") ;
    	DataReadService dataService = groovyBeanFactory.getBeanIntance("AAA", content, DataReadService.class) ;
    	System.out.println(dataService.getData(null));
    }
}
