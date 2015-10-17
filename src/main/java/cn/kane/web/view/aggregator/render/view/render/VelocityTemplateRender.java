package cn.kane.web.view.aggregator.render.view.render;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import cn.kane.web.view.aggregator.util.DefinitionKeyUtils;

import com.google.common.base.Charsets;

public class VelocityTemplateRender implements TemplateRender {

	// reference
	private StringResourceDefinitionManager stringResourceDefinitionManager;
	// instance
	private VelocityEngine velocityEngine;

	public void init() throws IOException {
		Properties prop = new Properties();
		prop.load(this.getClass().getResourceAsStream("/render/velocity.properties"));
		velocityEngine = new VelocityEngine(prop);
		velocityEngine.addProperty("templateManager", stringResourceDefinitionManager);
		velocityEngine.init();
	}

	@Override
	public String render(DefinitionKey templateKey, Map<String, Object> param) {
		Context context = new VelocityContext(param);
		StringWriter writer = new StringWriter();
		boolean result = velocityEngine.mergeTemplate(DefinitionKeyUtils.format(templateKey),
				Charsets.ISO_8859_1.toString(), context, writer);
		if (result) {
			return writer.toString();
		} else {
			return null;
		}
	}

	@Override
	public String merge(List<DefinitionKey> templateKeys, Map<String, Object> params) {
		if (null == templateKeys || templateKeys.isEmpty()) {
			return null;
		}
		List<String> resources = new ArrayList<String>(templateKeys.size());
		for (DefinitionKey templateKey : templateKeys) {
			String context = this.render(templateKey, params);
			resources.add(context);
		}
		Map<String, Object> mergedParams = new HashMap<String, Object>(1);
		mergedParams.put("resources", resources);
		// merge-template-key
		DefinitionKey mergeTemplateKey = new DefinitionKey();
		mergeTemplateKey.setName("mergeTemplate");
		mergeTemplateKey.setType("sys");
		mergeTemplateKey.setVersion("1");
		return this.render(mergeTemplateKey, mergedParams);
	}

	public StringResourceDefinitionManager getStringResourceDefinitionManager() {
		return stringResourceDefinitionManager;
	}

	public void setStringResourceDefinitionManager(StringResourceDefinitionManager stringResourceDefinitionManager) {
		this.stringResourceDefinitionManager = stringResourceDefinitionManager;
	}

}
