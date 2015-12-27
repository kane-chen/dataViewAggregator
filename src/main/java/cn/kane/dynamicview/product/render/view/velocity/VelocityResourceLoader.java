package cn.kane.dynamicview.product.render.view.velocity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.definition.entity.TemplateDefinition;
import cn.kane.dynamicview.definition.service.TemplateDefinitionManager;
import cn.kane.dynamicview.product.render.view.TemplateRender;
import cn.kane.dynamicview.util.DefinitionKeyUtils;

import com.google.common.hash.Hashing;

public class VelocityResourceLoader extends ResourceLoader {

	private Charset templateCharset = TemplateRender.TEMPLATE_CHARSET ;
	private TemplateDefinitionManager templateDefinitionManager;

	@Override
	public void init(ExtendedProperties configuration) {
		templateDefinitionManager = (TemplateDefinitionManager) this.rsvc.getProperty("templateManager");
	}

	@Override
	public InputStream getResourceStream(String source) throws ResourceNotFoundException {
		String content = this.getContentByKey(source);
		if (StringUtils.isBlank(content)) {
			return null;
		}
		return new ByteArrayInputStream(content.getBytes(templateCharset));
	}

	private String getContentByKey(String sourceName) {
		DefinitionKey key = DefinitionKeyUtils.parse(sourceName);
		if (null == key) {
			return null;
		}
		TemplateDefinition definition = this.templateDefinitionManager.get(key);
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
		return Hashing.md5().hashString(content,templateCharset).asLong();
	}

	public TemplateDefinitionManager getTemplateDefinitionManager() {
		return templateDefinitionManager;
	}

	public void setTemplateDefinitionManager(TemplateDefinitionManager templateDefinitionManager) {
		this.templateDefinitionManager = templateDefinitionManager;
	}

}
