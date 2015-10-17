package cn.kane.web.view.aggregator.loader.build;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.Resource;

public interface ResourceBuilder<T extends Resource> {

    T build(DefinitionKey key) ;
    
}
