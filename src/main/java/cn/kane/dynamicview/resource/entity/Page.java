package cn.kane.dynamicview.resource.entity;

import java.util.List;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.definition.entity.PageDefinition;


public class Page extends Resource {

	private static final long serialVersionUID = -6441941201648137276L;

	private PageDefinition pageDefinition;

	private String layout;

	private String js;

	private String css;

	private List<DefinitionKey> dataReaderKeys;

	private List<DefinitionKey> widgetKeys;

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public List<DefinitionKey> getDataReaderKeys() {
		return dataReaderKeys;
	}

	public void setDataReaderKeys(List<DefinitionKey> dataReaderKeys) {
		this.dataReaderKeys = dataReaderKeys;
	}

	public List<DefinitionKey> getWidgetKeys() {
		return widgetKeys;
	}

	public void setWidgetKeys(List<DefinitionKey> widgetKeys) {
		this.widgetKeys = widgetKeys;
	}

	public PageDefinition getPageDefinition() {
		return pageDefinition;
	}

	public void setPageDefinition(PageDefinition pageDefinition) {
		this.pageDefinition = pageDefinition;
	}

}
