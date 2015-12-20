package cn.kane.web.view.aggregator.manager;

import java.util.List;

import cn.kane.web.view.aggregator.pojo.definition.AbstractDefinition;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.service.manager.PageDefinitionManager;
import cn.kane.web.view.aggregator.service.manager.ResourceDefinitionManager;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import cn.kane.web.view.aggregator.service.manager.WidgetDefinitionManager;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ResourceDefinitionManagerFacade implements	ResourceDefinitionManager<AbstractDefinition> {

	private StringResourceDefinitionManager stringResourceDefinitionManager;
	private WidgetDefinitionManager widgetDefinitionManager;
	private PageDefinitionManager pageDefinitionManager;

	private ResourceDefinitionManager choose(DefinitionKey key) {
		switch (key.getType()) {
		case "string":
			return stringResourceDefinitionManager;
		case "dataReadService":
			return stringResourceDefinitionManager;
		case "css":
			return stringResourceDefinitionManager;
		case "js":
			return stringResourceDefinitionManager;
		case "dataTemplate":
			return stringResourceDefinitionManager;
		case "viewTemplate":
			return stringResourceDefinitionManager;
		case "layout":
			return stringResourceDefinitionManager;
		case "sys":
			return stringResourceDefinitionManager;
		case "widget":
			return widgetDefinitionManager;
		case "page":
			return pageDefinitionManager;
		default:
			return null;
		}
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
	public List<AbstractDefinition> list(DefinitionKey fromKey,	DefinitionKey toKey) {
		return choose(fromKey).list(fromKey, toKey);
	}
	

	public StringResourceDefinitionManager getStringResourceDefinitionManager() {
		return stringResourceDefinitionManager;
	}

	public void setStringResourceDefinitionManager(
			StringResourceDefinitionManager stringResourceDefinitionManager) {
		this.stringResourceDefinitionManager = stringResourceDefinitionManager;
	}

	public WidgetDefinitionManager getWidgetDefinitionManager() {
		return widgetDefinitionManager;
	}

	public void setWidgetDefinitionManager(
			WidgetDefinitionManager widgetDefinitionManager) {
		this.widgetDefinitionManager = widgetDefinitionManager;
	}

	public PageDefinitionManager getPageDefinitionManager() {
		return pageDefinitionManager;
	}

	public void setPageDefinitionManager(PageDefinitionManager pageDefinitionManager) {
		this.pageDefinitionManager = pageDefinitionManager;
	}

}
