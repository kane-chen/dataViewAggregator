package cn.kane.dynamicview.resource.loader.synchronize;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.resource.entity.Resource;
import cn.kane.dynamicview.resource.loader.builder.ResourceBuilder;
import cn.kane.dynamicview.resource.loader.cache.ResourceCacher;
import cn.kane.dynamicview.resource.loader.compare.ResourceComparator;

public class ScheduledResourceSynchronizer implements ResourceSynchronizer<Resource> {

	// scheduler
	private long schedulerInitDalaySeconds = 3 * 60;
	private long schedulePeriodSeconds = 60;
	// executor-pool
	private int corePoolSize = 1;
	private int maximumPoolSize = 5;
	private long keepAliveSeconds = 3;
	private int queueSize = 100;
	private ExecutorService execThreadPool;
	// reference
	private ResourceBuilder<Resource> resourceBuilder;
	private ResourceCacher<DefinitionKey, Resource> resourceCacher;
	private ResourceComparator<Resource> resourceComparator;

	public void init() {
		// executor-thread-pool
		execThreadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 
				keepAliveSeconds, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize), 
				new ThreadPoolExecutor.DiscardPolicy());
		// scheduler
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				ScheduledResourceSynchronizer.this.execSynchronizer();
			}
		}, schedulerInitDalaySeconds, schedulePeriodSeconds, TimeUnit.SECONDS);
	}

	@Override
	public Resource get(DefinitionKey key) {
		if (null == key) {
			return null;
		}
		return resourceCacher.get(key);
	}

	@Override
	public Resource sync(DefinitionKey key) {
		if (null == key) {
			return null;
		}
		Resource cachedResource = resourceCacher.get(key);
		Resource newResource = resourceBuilder.build(key);
		if (!resourceComparator.isEquals(cachedResource, newResource)) {
			resourceCacher.put(key, newResource);
			return newResource;
		} else {
			return resourceCacher.get(key);
		}
	}

	public void execSynchronizer() {
		for (DefinitionKey key : resourceCacher.getAllKeys()) {
			if (null != key) {
				execThreadPool.submit(this.buildSyncRunnable(key));
			}
		}
	}

	private Runnable buildSyncRunnable(final DefinitionKey key) {
		return new Runnable() {
			@Override
			public void run() {
				ScheduledResourceSynchronizer.this.sync(key);
			}
		};
	}

	public void setResourceBuilder(ResourceBuilder<Resource> resourceBuilder) {
		this.resourceBuilder = resourceBuilder;
	}

	public void setResourceCacher(ResourceCacher<DefinitionKey, Resource> resourceCacher) {
		this.resourceCacher = resourceCacher;
	}

	public void setResourceComparator(ResourceComparator<Resource> resourceComparator) {
		this.resourceComparator = resourceComparator;
	}

	public void setSchedulePeriodSeconds(long schedulePeriodSeconds) {
		this.schedulePeriodSeconds = schedulePeriodSeconds;
	}

	public void setSchedulerInitDalaySeconds(long schedulerInitDalaySeconds) {
		this.schedulerInitDalaySeconds = schedulerInitDalaySeconds;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public void setKeepAliveSeconds(long keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

}
