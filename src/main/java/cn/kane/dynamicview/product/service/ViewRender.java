package cn.kane.dynamicview.product.service;

import java.util.Map;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.product.vo.ViewModel;
import cn.kane.dynamicview.resource.entity.Resource;


public interface ViewRender<T extends Resource> {

    ViewModel reander(DefinitionKey defKey,Map<String,Object> params) ;
    
}
