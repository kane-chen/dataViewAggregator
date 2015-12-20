package cn.kane.web.view.viewresolver.support.velocity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.pojo.model.Page;
import cn.kane.web.view.aggregator.pojo.model.Widget;
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
		if("page".equals(key.getType())){
			Page page = cn.kane.web.view.viewresolver.support.velocity.ContainerBeanFactory.getInstance().getPageLoader().getResourceByKey(key) ;
			return page.getLayout() ;
		}else if("widget".equals(key.getType())){
			Widget widget = ContainerBeanFactory.getInstance().getWidgetLoader().getResourceByKey(key) ;
			StringBuilder temp = new StringBuilder();
			temp.append(widget.getDataTemplate()).append(widget.getViewTemplate()) ;
			return temp.toString() ;
		}else if("js".equals(key.getType()) || "css".equals(key.getType())  || "layout".equals(key.getType())
				|| "dataTemplate".equals(key.getType()) 
				|| "viewTemplate".equals(key.getType())){
			StringResourceDefinition definition = this.stringResourceDefinitionManager.get(key);
			return definition.getContent();
		}else{
			throw new UnsupportedOperationException("not suport source:"+sourceName);
		}
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
