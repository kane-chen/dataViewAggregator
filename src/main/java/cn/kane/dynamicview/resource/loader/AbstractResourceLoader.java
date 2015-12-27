package cn.kane.dynamicview.resource.loader;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.resource.entity.Resource;
import cn.kane.dynamicview.resource.loader.synchronize.ResourceSynchronizer;
import cn.kane.dynamicview.resource.service.ResourceLoader;

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
