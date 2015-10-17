package cn.kane.web.view.aggregator.loader;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;









import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.pojo.model.DataReader;
import cn.kane.web.view.aggregator.service.loader.DataReaderLoader;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import junit.framework.Assert;
import junit.framework.TestCase;

public class GroovyDataReaderLoaderTest extends TestCase {

    private StringResourceDefinitionManager stringResourceManager ;
    private DataReaderLoader dataReaderLoader ;
    
    private static final String dataReadServiceFile2 = "/resource/loader/script/DataReadServiceCodeNew.txt";
    
    private DefinitionKey key ;
    @SuppressWarnings("resource")
    public void setUp() throws IOException{
        //init
        ApplicationContext appContext = new ClassPathXmlApplicationContext("/resource/loader/datareader-loader-test.xml");
        stringResourceManager = (StringResourceDefinitionManager)appContext.getBean("stringResourceDefinitionManager");
        dataReaderLoader = (DataReaderLoader) appContext.getBean("dataReaderLoader");
        //key
        key = new DefinitionKey() ;
        key.setType("dataReadService");
        key.setName("dataReadServiceName");
        key.setVersion("1");
    }
    
    public void testLoad(){
        DataReader dataReader = dataReaderLoader.getResourceByKey(key) ;
        Assert.assertTrue("HELLO-WORLD".equals(dataReader.getDataReadService().getData(null))) ;
    }
    
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
}
