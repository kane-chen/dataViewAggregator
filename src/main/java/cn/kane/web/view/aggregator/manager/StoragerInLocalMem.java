package cn.kane.web.view.aggregator.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.kane.web.view.aggregator.pojo.definition.AbstractDefinition;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;

public class StoragerInLocalMem implements Storager {

	private Map<DefinitionKey,AbstractDefinition> store = new ConcurrentHashMap<DefinitionKey,AbstractDefinition>() ;
	
	@Override
	public boolean save(AbstractDefinition res) {
		getStore().put(res.getKey(), res);
		return true;
	}

	@Override
	public boolean update(AbstractDefinition res) {
		getStore().put(res.getKey(), res);
		return true;
	}

	@Override
	public AbstractDefinition get(DefinitionKey key) {
		return getStore().get(key);
	}

	@Override
	public List<AbstractDefinition> list(DefinitionKey fromKey, DefinitionKey toKey) {
		throw new UnsupportedOperationException("not-support list yet");
	}

	public Map<DefinitionKey,AbstractDefinition> getStore() {
		return store;
	}

	public void setStore(Map<DefinitionKey,AbstractDefinition> store) {
		this.store = store;
	}

}
