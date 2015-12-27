package cn.kane.dynamicview.resource.loader.cache;

import java.util.Set;

import cn.kane.dynamicview.resource.entity.Resource;

public interface ResourceCacher<DefinitionKey,T extends Resource> {

    boolean put(DefinitionKey key,T value) ;
    
    T get(DefinitionKey key) ;
    
    boolean invalidate(DefinitionKey key) ;
    
    Set<DefinitionKey> getAllKeys() ;
}
