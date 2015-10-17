package cn.kane.web.view.aggregator.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;

public class MockStringResourceDefinitionManager implements StringResourceDefinitionManager {

    Map<DefinitionKey, StringResourceDefinition>         templateMapping     = new HashMap<DefinitionKey, StringResourceDefinition>();
    
  //init-data
    public static final String dataReadServiceFile = "/resource/loader/script/DataReadServiceCode.txt";
    public static final String cssFile             = "/resource/loader/script/css.txt";
    public static final String jsFile              = "/resource/loader/script/js.txt";
    public static final String dataTemplateFile    = "/resource/loader/script/dataTemplate.txt";
    public static final String viewTemplateFile    = "/resource/loader/script/viewTemplate.txt";
    public static final String viewTemplateFile2    = "/resource/loader/script/viewTemplate2.txt";
    public static final String layoutFile          = "/resource/loader/script/layout.txt";
    public static final String dataTemplateFile2    = "/resource/loader/script/dataTemplate2.txt";
    public static final String dataReadServiceFile2 = "/resource/loader/script/DataReadServiceCodeNew.txt";
    public static final String mergeTemplateFile    = "/render/templates/merge.vm";
    
    public void init() throws IOException{
        this.add(this.buildResource("dataReadService", "dataReadServiceName", "1", this.getCodeInFile(dataReadServiceFile)));
        this.add(this.buildResource("css", "cssName", "1", this.getCodeInFile(cssFile)));
        this.add(this.buildResource("js", "jsName", "1", this.getCodeInFile(jsFile)));
        this.add(this.buildResource("dataTemplate", "dataTemplateName", "1", this.getCodeInFile(dataTemplateFile)));
        this.add(this.buildResource("viewTemplate", "viewTemplateName", "1", this.getCodeInFile(viewTemplateFile)));
        this.add(this.buildResource("viewTemplate", "viewTemplateName", "2", this.getCodeInFile(viewTemplateFile2)));
        this.add(this.buildResource("layout", "layoutName", "1", this.getCodeInFile(layoutFile)));
        this.add(this.buildResource("sys", "mergeTemplate", "1", this.getCodeInFile(mergeTemplateFile)));
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
    
    @Override
    public boolean add(StringResourceDefinition definition) {
        templateMapping.put(definition.getKey(), definition) ;
        return true;
    }

    @Override
    public boolean edit(StringResourceDefinition definition) {
        templateMapping.put(definition.getKey(), definition);
        return true;
    }

    @Override
    public StringResourceDefinition get(DefinitionKey key) {
        return templateMapping.get(key);
    }

    @Override
    public List<StringResourceDefinition> list(DefinitionKey fromKey, DefinitionKey toKey) {
        return null;
    }
}
