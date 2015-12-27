package cn.kane.dynamicview.definition.service;

import java.util.List;

import cn.kane.dynamicview.definition.entity.AbstractDefinition;
import cn.kane.dynamicview.definition.entity.DefinitionKey;

public interface ResourceDefinitionManager<T extends AbstractDefinition> {

    boolean add(T definition) ;
    
    boolean edit(T definition) ;
    
    boolean remove(DefinitionKey key) ;
    
    T get(DefinitionKey key) ;
    
    /**
     * select range(key.type must be special)
     * @param fromKey (inclusive)
     * @param toKey   (exclusive)
     * @return
     */
    List<T> list(DefinitionKey fromKey,DefinitionKey toKey) ;
}
