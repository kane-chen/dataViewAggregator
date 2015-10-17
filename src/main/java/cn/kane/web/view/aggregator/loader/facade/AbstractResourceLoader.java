package cn.kane.web.view.aggregator.loader.facade;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.Resource;
import cn.kane.web.view.aggregator.service.loader.ResourceLoader;
import cn.kane.web.view.aggregator.loader.sync.ResourceSynchronizer;

public class AbstractResourceLoader<T extends Resource> implements ResourceLoader<T> {

    private ResourceSynchronizer<Resource> resourceSynchronzier ;
    
    @SuppressWarnings("unchecked")
    @Override
    public T getResourceByKey(DefinitionKey definitionKey) {
        if(null == definitionKey){
            return null;
        }
        Resource resource = resourceSynchronzier.get(definitionKey) ;
        if(null != resource){
            return (T)resource ;
        }else{
            return this.getResourceBykeyWithoutCache(definitionKey) ;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getResourceBykeyWithoutCache(DefinitionKey definitionKey) {
        if(null == definitionKey){
            return null;
        }
        return (T) resourceSynchronzier.sync(definitionKey) ;
    }

    public void setResourceSynchronzier(ResourceSynchronizer<Resource> resourceSynchronzier) {
        this.resourceSynchronzier = resourceSynchronzier;
    }

}
