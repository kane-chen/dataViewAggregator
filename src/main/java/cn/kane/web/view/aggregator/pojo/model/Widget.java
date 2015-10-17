package cn.kane.web.view.aggregator.pojo.model;

import java.util.List;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.WidgetDefinition;

public class Widget extends Resource {

	private static final long serialVersionUID = -7803111154253531916L;

	private WidgetDefinition widgetDefinition;

	private String dataTemplate;

	private String viewTemplate;

	private String css;

	private String js;

	private List<DefinitionKey> dataReaderKeys;

	public String getDataTemplate() {
		return dataTemplate;
	}

	public void setDataTemplate(String dataTemplate) {
		this.dataTemplate = dataTemplate;
	}

	public String getViewTemplate() {
		return viewTemplate;
	}

	public void setViewTemplate(String viewTemplate) {
		this.viewTemplate = viewTemplate;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public List<DefinitionKey> getDataReaderKeys() {
		return dataReaderKeys;
	}

	public void setDataReaderKeys(List<DefinitionKey> dataReaderKeys) {
		this.dataReaderKeys = dataReaderKeys;
	}

	public WidgetDefinition getWidgetDefinition() {
		return widgetDefinition;
	}

	public void setWidgetDefinition(WidgetDefinition widgetDefinition) {
		this.widgetDefinition = widgetDefinition;
	}

}
