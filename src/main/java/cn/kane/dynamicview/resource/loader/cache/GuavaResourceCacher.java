package cn.kane.dynamicview.resource.loader.cache;

import java.util.Set;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.resource.entity.Resource;
import cn.kane.dynamicview.util.GuavaCacheManager;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class GuavaResourceCacher implements ResourceCacher<DefinitionKey, Resource> {

	private String cacheName;
	private long maxSize = 1000L;
	private LoadingCache<DefinitionKey, Resource> localCache;

	public void init() {
		// cache init
		localCache = GuavaCacheManager.getInstance().newLocalCache(cacheName,
				new CacheLoader<DefinitionKey, Resource>() {
					@Override
					public Resource load(DefinitionKey key) throws Exception {
						return null;
					}
				}, maxSize, 0, 0);
	}

	@Override
	public boolean put(DefinitionKey key, Resource value) {
		localCache.put(key, value);
		return false;
	}

	@Override
	public Resource get(DefinitionKey key) {
		return localCache.getIfPresent(key);
	}

	@Override
	public boolean invalidate(DefinitionKey key) {
		localCache.invalidate(key);
		return false;
	}

	@Override
	public Set<DefinitionKey> getAllKeys() {
		return localCache.asMap().keySet();
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

}
