package cn.kane.web.view.aggregator.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.util.Map;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.PageDefinition;
import cn.kane.web.view.aggregator.service.manager.PageDefinitionManager;

public class MockPageDefinitionManager implements PageDefinitionManager {

    private Map<DefinitionKey,PageDefinition> mapping = new HashMap<DefinitionKey, PageDefinition>();

    public void init(){
        DefinitionKey key = this.buildKey("page", "pageName", "1") ;
        PageDefinition definition = new PageDefinition() ;
        definition.setKey(key);
        definition.setLayoutDefinition(this.buildKey("layout", "layoutName", "1"));
        definition.setCssDefinition(this.buildKey("css", "cssName", "1"));
        definition.setJsDefinition(this.buildKey("js", "jsName", "1"));
        List<DefinitionKey> dataReaderDefs = new ArrayList<DefinitionKey>() ;
        dataReaderDefs.add(this.buildKey("dataReadService", "dataReadServiceName", "1"));
        definition.setDataReaderDefinitions(dataReaderDefs);
        List<DefinitionKey> widgetDefinitions = new ArrayList<DefinitionKey>() ;
        widgetDefinitions.add(this.buildKey("widget", "widgetName", "1"));
        definition.setWidgetDefinitions(widgetDefinitions);
        this.add(definition) ;
    }
    
    private DefinitionKey buildKey(String type,String name,String version){
        DefinitionKey key = new DefinitionKey() ;
        key.setType(type);
        key.setName(name);
        key.setVersion(version);
        return key ;
    }
    
    @Override
    public boolean add(PageDefinition definition) {
        mapping.put(definition.getKey(),definition) ;
        return true;
    }

    @Override
    public boolean edit(PageDefinition definition) {
        mapping.put(definition.getKey(),definition) ;
        return true;
    }

    @Override
    public PageDefinition get(DefinitionKey key) {
        return this.mapping.get(key);
    }

    @Override
    public List<PageDefinition> list(DefinitionKey fromKey, DefinitionKey toKey) {
        return null;
    }
    
}
