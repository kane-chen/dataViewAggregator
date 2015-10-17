package cn.kane.web.view.aggregator.loader.build;

import java.util.List;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.PageDefinition;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.pojo.model.Page;
import cn.kane.web.view.aggregator.service.manager.PageDefinitionManager;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;


public class PageBuilder implements ResourceBuilder<Page> {

    private PageDefinitionManager pageDefinitionManager ;
    private StringResourceDefinitionManager stringResourceDefinitionManager ;

    @Override
    public Page build(DefinitionKey definitionKey) {
        if(null == definitionKey){
            return null ;
        }
        return this.buildResourceInstance(definitionKey);
    }

    
    private Page buildResourceInstance(DefinitionKey definitionKey) {
        if(null == definitionKey){
            return null;
        }
        PageDefinition pageDefinition = pageDefinitionManager.get(definitionKey) ;
        Page page = this.buildPage(pageDefinition) ;
        return page ;
    }
    
    private Page buildPage(PageDefinition definition){
        if(null == definition){
            return null ;
        }
        Page page = new Page() ;
        page.setPageDefinition(definition);
        page.setDefinitionKey(definition.getKey());
        //layout
        StringResourceDefinition layoutDefinition = stringResourceDefinitionManager.get(definition.getLayoutDefinition()) ;
        if(null!=layoutDefinition){
            page.setLayout(layoutDefinition.getContent());
        }
        //css
        StringResourceDefinition cssDefinition = stringResourceDefinitionManager.get(definition.getCssDefinition()) ;
        if(null!=cssDefinition){
            page.setCss(cssDefinition.getContent());
        }
        //js
        StringResourceDefinition jsDefinition = stringResourceDefinitionManager.get(definition.getJsDefinition()) ;
        if(null!=jsDefinition){
            page.setJs(jsDefinition.getContent());
        }
        //widget
        List<DefinitionKey> widgetDefinitions = definition.getWidgetDefinitions() ;
        if(null!=widgetDefinitions){
            page.setWidgetKeys(widgetDefinitions);
        }
        //dataReader
        List<DefinitionKey> dataReaderDefinitionKeys = definition.getDataReaderDefinitions() ;
        if(null!=dataReaderDefinitionKeys && !dataReaderDefinitionKeys.isEmpty()){
            page.setDataReaderKeys(dataReaderDefinitionKeys);
        }
        return page ;
    }

    public void setPageDefinitionManager(PageDefinitionManager pageDefinitionManager) {
        this.pageDefinitionManager = pageDefinitionManager;
    }

    public void setStringResourceDefinitionManager(StringResourceDefinitionManager stringResourceDefinitionManager) {
        this.stringResourceDefinitionManager = stringResourceDefinitionManager;
    }

}
