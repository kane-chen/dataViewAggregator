package cn.kane.web.view.aggregator.util.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;

public class GuavaCacheManager {

    private static final Long               CONFIG_MAX_SIZE        = 1000L;
    private static final Long               CONFIG_REFRESH_SECONDS = 30L;
    private static final Long               CONFIG_EXPIRE_SECONDS  = 300L;

    private Map<String, LoadingCache<?, ?>> caches;

    private static GuavaCacheManager        instance               = new GuavaCacheManager();

    private GuavaCacheManager(){
        caches = new ConcurrentHashMap<String, LoadingCache<?, ?>>();
    }

    public static GuavaCacheManager getInstance() {
        return instance;
    }

    public LoadingCache<?, ?> getLocalCacheByName(String cacheName) {
        if (null == cacheName) {
            return null;
        }
        LoadingCache<?, ?> localCache = caches.get(cacheName);
        return localCache;
    }

    public <K, V> LoadingCache<K, V> newLocalCache(String cacheName, CacheLoader<K, V> cacheLoader) {
        return this.newLocalCache(cacheName, cacheLoader, CONFIG_MAX_SIZE, CONFIG_REFRESH_SECONDS,
                                  CONFIG_EXPIRE_SECONDS);
    }

    public <K, V> LoadingCache<K, V> newLocalCache(String cacheName, CacheLoader<K, V> cacheLoader, long maxSize,
                                                   long freshSeconds, long expireSeconds) {
        return this.newLocalCache(cacheName, cacheLoader, maxSize, true,true ,freshSeconds, expireSeconds, null);
    }

    public <K, V> LoadingCache<K, V> newLocalCache(String cacheName, CacheLoader<K, V> cacheLoader, long maxSize,
                                                   boolean weakKey, boolean softValues, long freshSeconds,
                                                   long expireSeconds, RemovalListener<K, V> removaListener) {
        // required-param
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        if(maxSize > 0){
            cacheBuilder.maximumSize(maxSize);
        }
        if(weakKey){
            cacheBuilder.weakKeys() ;
        }
        if(softValues){
            cacheBuilder.softValues() ;
        }
        if(freshSeconds > 0){
            cacheBuilder.refreshAfterWrite(freshSeconds,TimeUnit.SECONDS);
        }
        if(expireSeconds > 0){
            cacheBuilder.expireAfterWrite(expireSeconds,TimeUnit.SECONDS) ;
        }
        // remova-listener
        if (null != removaListener) {
            cacheBuilder.removalListener(removaListener);
        }
        // builder
        LoadingCache<K, V> localCache = cacheBuilder.build(cacheLoader);
        caches.put(cacheName, localCache);
        return localCache;
    }

}
