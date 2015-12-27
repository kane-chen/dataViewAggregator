package cn.kane.dynamicview.resource.loader.builder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.definition.entity.PageDefinition;
import cn.kane.dynamicview.definition.entity.TemplateDefinition;
import cn.kane.dynamicview.definition.service.PageDefinitionManager;
import cn.kane.dynamicview.definition.service.TemplateDefinitionManager;
import cn.kane.dynamicview.resource.entity.Page;


public class PageBuilder implements ResourceBuilder<Page> {
	@Autowired
    private PageDefinitionManager pageDefinitionManager ;
    @Autowired
	private TemplateDefinitionManager templateDefinitionManager ;

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
        TemplateDefinition layoutDefinition = templateDefinitionManager.get(definition.getLayoutDefinition()) ;
        if(null!=layoutDefinition){
            page.setLayout(layoutDefinition.getContent());
        }
        //css
        TemplateDefinition cssDefinition = templateDefinitionManager.get(definition.getCssDefinition()) ;
        if(null!=cssDefinition){
            page.setCss(cssDefinition.getContent());
        }
        //js
        TemplateDefinition jsDefinition = templateDefinitionManager.get(definition.getJsDefinition()) ;
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

}
