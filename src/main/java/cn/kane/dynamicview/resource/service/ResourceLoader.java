package cn.kane.dynamicview.resource.service;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.resource.entity.Resource;

public interface ResourceLoader<T extends Resource> {

    T getResourceByKey(DefinitionKey definitionKey) ;
    
    T getResourceBykeyWithoutCache(DefinitionKey definitionKey) ;
}
