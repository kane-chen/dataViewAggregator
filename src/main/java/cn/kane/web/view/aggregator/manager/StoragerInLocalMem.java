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
		StringResourceDefinition css = this.buildStringResourceDefinition("css", "simple", "1", "/templates/simple.css") ;
		store.put(css.getKey(), css);
		StringResourceDefinition js = this.buildStringResourceDefinition("js", "simple", "1", "/templates/simple.js") ;
		store.put(js.getKey(), js);
		StringResourceDefinition dt = this.buildStringResourceDefinition("dataTemplate", "simple", "1", "/templates/simple.dt") ;
		store.put(dt.getKey(), dt);
		StringResourceDefinition vt = this.buildStringResourceDefinition("viewTemplate", "simple", "1", "/templates/simple.vt") ;
		store.put(vt.getKey(), vt);
		StringResourceDefinition service = this.buildStringResourceDefinition("dataReadService", "simple", "1", "/templates/simpleService.dat") ;
		store.put(service.getKey(), service);
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
