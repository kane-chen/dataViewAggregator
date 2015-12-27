package cn.kane.web.view.aggregator.service.manager;

import java.util.List;

import cn.kane.web.view.aggregator.pojo.definition.AbstractDefinition;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;

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
