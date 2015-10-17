package cn.kane.web.view.aggregator.loader.cache;

import java.util.Set;

import cn.kane.web.view.aggregator.pojo.model.Resource;


public interface ResourceCacher<DefinitionKey,T extends Resource> {

    boolean put(DefinitionKey key,T value) ;
    
    T get(DefinitionKey key) ;
    
    boolean invalidate(DefinitionKey key) ;
    
    Set<DefinitionKey> getAllKeys() ;
}
