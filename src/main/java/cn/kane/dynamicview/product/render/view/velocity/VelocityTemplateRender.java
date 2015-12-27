package cn.kane.dynamicview.product.render.view.velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.definition.entity.TemplateDefinition;
import cn.kane.dynamicview.definition.service.TemplateDefinitionManager;
import cn.kane.dynamicview.product.render.view.TemplateRender;
import cn.kane.dynamicview.util.DefinitionKeyUtils;

public class VelocityTemplateRender implements TemplateRender {

	// reference
	@Autowired
	private TemplateDefinitionManager templateDefinitionManager;
	// instance
	private VelocityEngine velocityEngine;
	private ToolContext toolContext ;
	private String velocityPropertiesPath = "/velocity/velocity.properties" ;
	private String velocityToolsPath = "/velocity/tools.xml" ;

	public void init() throws IOException {
		//velocity-engine
		Properties prop = new Properties();
		prop.load(this.getClass().getResourceAsStream(velocityPropertiesPath));
		velocityEngine = new VelocityEngine(prop);
		velocityEngine.addProperty("templateManager", templateDefinitionManager);
		velocityEngine.init();
		//velocity-tools
		ToolManager manager = new ToolManager(); 
		manager.configure(velocityToolsPath); 
		toolContext = manager.createContext(); 
	}

	@Override
	public String render(DefinitionKey templateKey, Map<String, Object> param) {
		//TODO
		TemplateDefinition templateDef = templateDefinitionManager.get(templateKey) ;
		if(null == templateDef || StringUtils.isBlank(templateDef.getContent())){
			//WARN,template is blank,cannot exception.need LOG 
			return null;
		}
		Context context = new VelocityContext(toolContext);
		this.params2Context(context,param);
		StringWriter writer = new StringWriter();
		boolean result = velocityEngine.mergeTemplate(DefinitionKeyUtils.format(templateKey),
				TEMPLATE_CHARSET.toString(), context, writer);
		if (result) {
			this.context2Params(context, param);
			return writer.toString();
		} else {
			return null;
		}
	}
	
	private void params2Context(Context context,Map<String, Object> param){
		if(null!=param){
			for(String key : param.keySet()){
				context.put(key, param.get(key));
			}
		}
	}
	
	private void context2Params(Context context,Map<String, Object> param){
		if(null!=context.getKeys() && null!=param){
			for(Object key : context.getKeys()){
				String strKey = (String)key ;
				param.put(strKey, context.get(strKey));
			}
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

	public String getVelocityPropertiesPath() {
		return velocityPropertiesPath;
	}

	public void setVelocityPropertiesPath(String velocityPropertiesPath) {
		this.velocityPropertiesPath = velocityPropertiesPath;
	}

	public String getVelocityToolsPath() {
		return velocityToolsPath;
	}
	
	public void setVelocityToolsPath(String velocityToolsPath) {
		this.velocityToolsPath = velocityToolsPath;
	}
	
}
