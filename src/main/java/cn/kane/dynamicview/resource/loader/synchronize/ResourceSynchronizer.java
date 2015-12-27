package cn.kane.dynamicview.resource.loader.synchronize;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.resource.entity.Resource;

public interface ResourceSynchronizer<T extends Resource> {

	T sync(DefinitionKey key);

	T get(DefinitionKey key);
}
