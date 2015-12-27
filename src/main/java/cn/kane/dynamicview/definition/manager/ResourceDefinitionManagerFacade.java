package cn.kane.dynamicview.definition.manager;

import java.util.List;
import java.util.Map;

import cn.kane.dynamicview.definition.entity.AbstractDefinition;
import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.definition.service.ResourceDefinitionManager;

public class ResourceDefinitionManagerFacade implements	ResourceDefinitionManager<AbstractDefinition> {

	private Map<String,ResourceDefinitionManager<AbstractDefinition>> managerMapping ;
	
	private ResourceDefinitionManager<AbstractDefinition> choose(DefinitionKey key) {
		return managerMapping.get(key.getType()) ;
	}

	@Override
	public boolean add(AbstractDefinition definition) {
		return choose(definition.getKey()).add(definition);
	}

	@Override
	public boolean edit(AbstractDefinition definition) {
		return choose(definition.getKey()).edit(definition);
	}

	@Override
	public AbstractDefinition get(DefinitionKey key) {
		return choose(key).get(key);
	}

	@Override
	public boolean remove(DefinitionKey key) {
		return choose(key).remove(key);
	}

	@Override
	public List<AbstractDefinition> list(DefinitionKey fromKey,	DefinitionKey toKey) {
		return choose(fromKey).list(fromKey, toKey);
	}
	
	public Map<String,ResourceDefinitionManager<AbstractDefinition>> getManagerMapping() {
		return managerMapping;
	}

	public void setManagerMapping(Map<String,ResourceDefinitionManager<AbstractDefinition>> managerMapping) {
		this.managerMapping = managerMapping;
	}

}
