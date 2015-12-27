package cn.kane.web.view.aggregator.manager;

import java.util.List;

import cn.kane.web.view.aggregator.pojo.definition.AbstractDefinition;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;

public interface Storager {

    boolean save(AbstractDefinition res) ;
    
    boolean update(AbstractDefinition res) ;
    
    boolean remove(DefinitionKey key) ;
    
    AbstractDefinition get(DefinitionKey key) ;
    
    List<AbstractDefinition> list(DefinitionKey fromKey,DefinitionKey toKey) ;
}
