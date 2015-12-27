package cn.kane.web.view.aggregator.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.web.view.aggregator.pojo.definition.AbstractDefinition;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.service.manager.PageDefinitionManager;
import cn.kane.web.view.aggregator.service.manager.ResourceDefinitionManager;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import cn.kane.web.view.aggregator.service.manager.WidgetDefinitionManager;

public class ResourceDefinitionManagerFacade implements	ResourceDefinitionManager<AbstractDefinition> {

	@Autowired
	private StringResourceDefinitionManager stringResourceDefinitionManager;
	@Autowired
	private WidgetDefinitionManager widgetDefinitionManager;
	@Autowired
	private PageDefinitionManager pageDefinitionManager;

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
