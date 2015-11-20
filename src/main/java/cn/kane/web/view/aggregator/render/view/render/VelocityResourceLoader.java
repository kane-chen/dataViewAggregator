package cn.kane.web.view.aggregator.render.view.render;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import cn.kane.web.view.aggregator.util.DefinitionKeyUtils;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class VelocityResourceLoader extends ResourceLoader {

	private StringResourceDefinitionManager stringResourceDefinitionManager;

	@Override
	public void init(ExtendedProperties configuration) {
		stringResourceDefinitionManager = (StringResourceDefinitionManager) this.rsvc.getProperty("templateManager");
	}

	@Override
	public InputStream getResourceStream(String source) throws ResourceNotFoundException {
		String content = this.getContentByKey(source);
		if (StringUtils.isBlank(content)) {
			return null;
		}
		return new ByteArrayInputStream(content.getBytes(Charsets.ISO_8859_1));
	}

	private String getContentByKey(String sourceName) {
		DefinitionKey key = DefinitionKeyUtils.parse(sourceName);
		if (null == key) {
			return null;
		}
		StringResourceDefinition definition = this.stringResourceDefinitionManager.get(key);
		if (null == definition || StringUtils.isBlank(definition.getContent())) {
			return null;
		}
		return definition.getContent();
	}

	@Override
	public boolean isSourceModified(Resource resource) {
		if (null == resource || StringUtils.isBlank(resource.getName())) {
			return false;
		}
		long cachedValue = resource.getLastModified();
		long nowValue = this.getLastModified(resource);
		return cachedValue != nowValue;
	}

	@Override
	public long getLastModified(Resource resource) {
		if (null == resource) {
			return 0;
		}
		String content = this.getContentByKey(resource.getName());
		if (StringUtils.isBlank(content)) {
			return 0;
		}
		return Hashing.md5().hashString(content, Charsets.ISO_8859_1).asLong();
	}

	public void setStringResourceDefinitionManager(StringResourceDefinitionManager stringResourceDefinitionManager) {
		this.stringResourceDefinitionManager = stringResourceDefinitionManager;
	}

}
