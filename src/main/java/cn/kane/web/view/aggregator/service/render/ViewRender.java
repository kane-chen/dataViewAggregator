package cn.kane.web.view.aggregator.service.render;

import java.util.Map;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.Resource;
import cn.kane.web.view.aggregator.pojo.model.ViewModel;

public interface ViewRender<T extends Resource> {

    ViewModel reander(DefinitionKey defKey,Map<String,Object> params) ;
    
}
