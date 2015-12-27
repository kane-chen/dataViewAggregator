package cn.kane.dynamicview.resource.loader.builder;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.resource.entity.Resource;


public interface ResourceBuilder<T extends Resource> {

    T build(DefinitionKey key) ;
    
}
