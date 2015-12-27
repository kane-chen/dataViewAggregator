package cn.kane.dynamicview.definition.manager;

import java.util.List;

import cn.kane.dynamicview.definition.entity.AbstractDefinition;
import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.definition.service.ResourceDefinitionManager;
import cn.kane.dynamicview.storage.Storager;


public abstract class AbstractResourceDefinitionManager<T extends AbstractDefinition> implements ResourceDefinitionManager<T> {

	private Storager<DefinitionKey,T> storager;

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

	@Override
	public boolean remove(DefinitionKey key) {
		return storager.remove(key);
	}
	
	@Override
	public T get(DefinitionKey key) {
		T definition = (T) storager.get(key);
		definition = this.parseAfterRead(definition);
		return definition;
	}

	@Override
	public List<T> list(DefinitionKey fromKey, DefinitionKey toKey) {
		if (null == fromKey || null == toKey) {
			return null;
		}
		List<T> definitions = (List<T>) storager.query(fromKey, toKey);
		for (T definition : definitions) {
			definition = this.parseAfterRead(definition);
		}
		return definitions;
	}

	public void setStorager(Storager<DefinitionKey,T> storager) {
		this.storager = storager;
	}

}
