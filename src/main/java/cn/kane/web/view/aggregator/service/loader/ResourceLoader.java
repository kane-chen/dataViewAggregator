package cn.kane.web.view.aggregator.service.loader;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.Resource;


public interface ResourceLoader<T extends Resource> {

    T getResourceByKey(DefinitionKey definitionKey) ;
    
    T getResourceBykeyWithoutCache(DefinitionKey definitionKey) ;
}
