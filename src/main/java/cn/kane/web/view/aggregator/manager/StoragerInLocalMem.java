package cn.kane.web.view.aggregator.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;

import cn.kane.web.view.aggregator.pojo.definition.AbstractDefinition;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.PageDefinition;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.pojo.definition.WidgetDefinition;

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
	public boolean remove(DefinitionKey key) {
		store.remove(key) ;
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

	public void init() throws IOException{
		//templates
		StringResourceDefinition css = this.buildStringResourceDefinition("css", "simple", "1", "/templates/simple.css") ;
		store.put(css.getKey(), css);
		StringResourceDefinition js = this.buildStringResourceDefinition("js", "simple", "1", "/templates/simple.js") ;
		store.put(js.getKey(), js);
		StringResourceDefinition dt = this.buildStringResourceDefinition("dataTemplate", "simple", "1", "/templates/simple.dt") ;
		store.put(dt.getKey(), dt);
		StringResourceDefinition vt = this.buildStringResourceDefinition("viewTemplate", "simple", "1", "/templates/simple.vt") ;
		store.put(vt.getKey(), vt);
		StringResourceDefinition macro = this.buildStringResourceDefinition("macro", "globalMacro", "1", "/render/macro.vm") ;
		store.put(macro.getKey(), macro);
		//services
		StringResourceDefinition service = this.buildStringResourceDefinition("dataReadService", "simple", "1", "/templates/scripts/simpleService.dat") ;
		store.put(service.getKey(), service);
		StringResourceDefinition groovyService = this.buildStringResourceDefinition("dataReadService", "future", "1", "/templates/scripts/futureDataService.dat") ;
		store.put(groovyService.getKey(), groovyService);
		//widgets
		DefinitionKey widgetKey = this.buildDefinitionKey("widget", "simple", "1") ;
		WidgetDefinition simpleWidget = new WidgetDefinition() ;
		simpleWidget.setKey(widgetKey);
		simpleWidget.setCssDefinition(css.getKey());
		simpleWidget.setJsDefinition(js.getKey());
		simpleWidget.setViewTemplateDefinition(vt.getKey());
		simpleWidget.setDataTemplateDefinition(dt.getKey());
		List<DefinitionKey> dataReaderDefinitions = new ArrayList<DefinitionKey>();
		dataReaderDefinitions.add(service.getKey());
		simpleWidget.setDataReaderDefinitions(dataReaderDefinitions );
		simpleWidget.setContent(this.keys2Json(simpleWidget));
		store.put(simpleWidget.getKey(), simpleWidget) ;
		//test
		this.addPage4Test();
		this.addWidget("10");
		this.addWidget("100");
		this.addWidget("150");
		this.addWidget("200");
	}
	
	private void addPage4Test() throws IOException{
		StringResourceDefinition layoutDefinition = this.buildStringResourceDefinition("layout", "test", "1", "/pagetest/page.layout") ;
		store.put(layoutDefinition.getKey(), layoutDefinition);
		DefinitionKey pageKey = this.buildDefinitionKey("page", "test", "1") ;
		PageDefinition pageDef = new PageDefinition() ;
		pageDef.setKey(pageKey);
		pageDef.setLayoutDefinition(layoutDefinition.getKey());
		pageDef.setContent(this.keys2Json(pageDef));
		store.put(pageKey, pageDef);
	}
	
	private void addWidget(String widgetName) throws IOException{
		StringResourceDefinition css = this.buildStringResourceDefinition("css", widgetName, "1", "/pagetest/widgets/"+widgetName+"/style.css") ;
		store.put(css.getKey(), css);
		StringResourceDefinition dt = this.buildStringResourceDefinition("dataTemplate", widgetName, "1", "/pagetest/widgets/"+widgetName+"/data.dt") ;
		store.put(dt.getKey(), dt);
		StringResourceDefinition vt = this.buildStringResourceDefinition("viewTemplate",widgetName, "1", "/pagetest/widgets/"+widgetName+"/view.vt") ;
		store.put(vt.getKey(), vt);
		StringResourceDefinition dataReader = this.buildStringResourceDefinition("dataReadService",widgetName, "1", "/pagetest/scripts/Data"+widgetName+".dat") ;
		store.put(dataReader.getKey(), dataReader);
		DefinitionKey widgetKey = this.buildDefinitionKey("widget", widgetName, "1") ;
		WidgetDefinition widget = new WidgetDefinition() ;
		widget.setKey(widgetKey);
		widget.setViewTemplateDefinition(vt.getKey());
		widget.setDataTemplateDefinition(dt.getKey());
		widget.setContent(this.keys2Json(widget));
		store.put(widget.getKey(), widget) ;
	}
	
	private String keys2Json(PageDefinition definition) {
		PageDefinition cloneDefinition = null;
		try {
			cloneDefinition = (PageDefinition) BeanUtils.cloneBean(definition);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("cloneBean[%s] error", definition), e);
		}
		cloneDefinition.setKey(null);
		cloneDefinition.setDescription(null);
		return JSON.toJSONString(cloneDefinition);
	}
	
	private String keys2Json(WidgetDefinition definition) {
		WidgetDefinition cloneDefinition = null;
		try {
			cloneDefinition = (WidgetDefinition) BeanUtils.cloneBean(definition);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("cloneBean[%s] error", definition), e);
		}
		cloneDefinition.setKey(null);
		cloneDefinition.setDescription(null);
		return JSON.toJSONString(cloneDefinition);
	}
	
	private StringResourceDefinition buildStringResourceDefinition(String type, String name, String version, String file) throws IOException{
		DefinitionKey key = this.buildDefinitionKey(type, name, version) ;
		StringResourceDefinition def = new StringResourceDefinition() ;
		def.setKey(key);
		def.setContent(this.getContentInFile(file));
		return def ;
	}
	
	private DefinitionKey buildDefinitionKey(String type,String name,String version){
		DefinitionKey key = new DefinitionKey() ;
		key.setType(type);
		key.setName(name);
		key.setVersion(version);
		return key ;
	}
	
	private String getContentInFile(String file) throws IOException{
		 String result = null ;
        InputStream in = this.getClass().getResourceAsStream(file);
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        result = new String(bytes);
        return result;
	}

}
