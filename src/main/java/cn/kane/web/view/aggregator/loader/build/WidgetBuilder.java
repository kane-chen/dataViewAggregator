package cn.kane.web.view.aggregator.loader.build;

import java.util.List;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.pojo.definition.WidgetDefinition;
import cn.kane.web.view.aggregator.pojo.model.Widget;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import cn.kane.web.view.aggregator.service.manager.WidgetDefinitionManager;


public class WidgetBuilder implements ResourceBuilder<Widget> {

    private WidgetDefinitionManager widgetDefinitionManager ;
    private StringResourceDefinitionManager stringResourceDefinitionManager ;

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
        StringResourceDefinition definition = stringResourceDefinitionManager.get(definitionKey) ;
        if(null == definition){
            return null ;
        }
        return definition.getContent() ;
    }
    
    public void setWidgetDefinitionManager(WidgetDefinitionManager widgetDefinitionManager) {
        this.widgetDefinitionManager = widgetDefinitionManager;
    }

    public void setStringResourceDefinitionManager(StringResourceDefinitionManager stringResourceDefnitionManager) {
        this.stringResourceDefinitionManager = stringResourceDefnitionManager;
    }

}
