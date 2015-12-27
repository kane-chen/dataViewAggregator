package cn.kane.dynamicview.resource.loader.builder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.definition.entity.TemplateDefinition;
import cn.kane.dynamicview.definition.entity.WidgetDefinition;
import cn.kane.dynamicview.definition.service.TemplateDefinitionManager;
import cn.kane.dynamicview.definition.service.WidgetDefinitionManager;
import cn.kane.dynamicview.resource.entity.Widget;


public class WidgetBuilder implements ResourceBuilder<Widget> {
	
	@Autowired
    private WidgetDefinitionManager widgetDefinitionManager ;
	@Autowired
    private TemplateDefinitionManager templateDefinitionManager ;

    @Override
    public Widget build(DefinitionKey key) {
        if(null == key){
            return null;
        }
        return this.buildResourceInstance(key) ;
    }
    
    private Widget buildResourceInstance(DefinitionKey definitionKey) {
        if(null == definitionKey){
            return null ;
        }
        //widget
        WidgetDefinition widgetDefinition = widgetDefinitionManager.get(definitionKey) ;
        if(null == widgetDefinition){
            return null ;
        }
        Widget widget = new Widget() ;
        widget.setWidgetDefinition(widgetDefinition);
        widget.setDefinitionKey(definitionKey);
        widget.setJs(this.getContentByKey(widgetDefinition.getJsDefinition()));
        widget.setCss(this.getContentByKey(widgetDefinition.getCssDefinition()));
        widget.setDataTemplate(this.getContentByKey(widgetDefinition.getDataTemplateDefinition()));
        widget.setViewTemplate(this.getContentByKey(widgetDefinition.getViewTemplateDefinition()));
        List<DefinitionKey> dataReaderDefinitionKeys = widgetDefinition.getDataReaderDefinitions() ;
        if(null!=dataReaderDefinitionKeys && !dataReaderDefinitionKeys.isEmpty()){
            widget.setDataReaderKeys(dataReaderDefinitionKeys);
        }
        return widget;
    }
    
    private String getContentByKey(DefinitionKey definitionKey){
        if(null == definitionKey){
            return null ;
        }
        TemplateDefinition definition = templateDefinitionManager.get(definitionKey) ;
        if(null == definition){
            return null ;
        }
        return definition.getContent() ;
    }
    
}
