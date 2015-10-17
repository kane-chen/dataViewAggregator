package cn.kane.web.view.aggregator.manager;

import java.util.List;

import cn.kane.web.view.aggregator.pojo.definition.AbstractDefinition;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.service.manager.ResourceDefinitionManager;

public abstract class AbstractOTSResourceDefinitionManager<T extends AbstractDefinition>
		implements ResourceDefinitionManager<T> {

	private Storager storager;

	public abstract Class<T> getTargetClass();

	public abstract T formatBeforeWrite(T definition);

	public abstract T parseAfterRead(T definition);

	@Override
	public boolean add(T definition) {
		// check
		if (null == definition || null == definition.getKey()) {
			return false;
		}
		// column
		definition = this.formatBeforeWrite(definition);
		return storager.save(definition);
	}

	@Override
	public boolean edit(T definition) {
		// check
		if (null == definition || null == definition.getKey()) {
			return false;
		}
		// column
		definition = this.formatBeforeWrite(definition);
		return storager.update(definition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(DefinitionKey key) {
		T definition = (T) storager.get(key);
		definition = this.parseAfterRead(definition);
		return definition;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> list(DefinitionKey fromKey, DefinitionKey toKey) {
		if (null == fromKey || null == toKey) {
			return null;
		}
		List<T> definitions = (List<T>) storager.list(fromKey, toKey);
		for (T definition : definitions) {
			definition = this.parseAfterRead(definition);
		}
		return definitions;
	}

}
