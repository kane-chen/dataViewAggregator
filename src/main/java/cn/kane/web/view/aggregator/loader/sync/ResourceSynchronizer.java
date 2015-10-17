package cn.kane.web.view.aggregator.loader.sync;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.Resource;

public interface ResourceSynchronizer<T extends Resource> {

	T sync(DefinitionKey key);

	T get(DefinitionKey key);
}
