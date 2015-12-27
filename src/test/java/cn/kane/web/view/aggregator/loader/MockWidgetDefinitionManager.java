package cn.kane.web.view.aggregator.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import java.util.Map;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.WidgetDefinition;
import cn.kane.web.view.aggregator.service.manager.WidgetDefinitionManager;

public class MockWidgetDefinitionManager implements WidgetDefinitionManager {

    private Map<DefinitionKey,WidgetDefinition> mapping = new HashMap<DefinitionKey, WidgetDefinition>();

    public void init(){
        DefinitionKey key = this.buildKey("widget", "widgetName", "1") ;
        WidgetDefinition definition = new WidgetDefinition() ;
        definition.setKey(key);
        definition.setCssDefinition(this.buildKey("css", "cssName", "1"));
        definition.setJsDefinition(this.buildKey("js", "jsName", "1"));
        definition.setDataTemplateDefinition(this.buildKey("dataTemplate", "dataTemplateName", "1"));
        definition.setViewTemplateDefinition(this.buildKey("viewTemplate", "viewTemplateName", "1"));
        List<DefinitionKey> dataReaderDefs = new ArrayList<DefinitionKey>() ;
        dataReaderDefs.add(this.buildKey("dataReadService", "dataReadServiceName", "1"));
        definition.setDataReaderDefinitions(dataReaderDefs);
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
    public boolean add(WidgetDefinition definition) {
        mapping.put(definition.getKey(), definition) ;
        return true;
    }

    @Override
    public boolean edit(WidgetDefinition definition) {
        mapping.put(definition.getKey(), definition) ;
        return true;
    }

    @Override
    public WidgetDefinition get(DefinitionKey key) {
        return this.mapping.get(key);
    }

    @Override
    public List<WidgetDefinition> list(DefinitionKey fromKey, DefinitionKey toKey) {
        return null;
    }
    
    @Override
	public boolean remove(DefinitionKey key) {
		mapping.remove(key);
		return true;
	}
    
}
