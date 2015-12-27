package cn.kane.dynamicview.definition.entity;

import java.io.Serializable;
import java.util.List;

public class WidgetDefinition extends TemplateDefinition implements Serializable {

	private static final long serialVersionUID = -6049040867286471907L;
	/**
	 * data-template-definition
	 */
	private DefinitionKey dataTemplateDefinition;
	/**
	 * view-template-definition
	 */
	private DefinitionKey viewTemplateDefinition;
	/**
	 * css-definition
	 */
	private DefinitionKey cssDefinition;
	/**
	 * js-definition
	 */
	private DefinitionKey jsDefinition;
	/**
	 * data-read-service-definitions
	 */
	private List<DefinitionKey> dataReaderDefinitions;

	public DefinitionKey getDataTemplateDefinition() {
		return dataTemplateDefinition;
	}

	public void setDataTemplateDefinition(DefinitionKey dataTemplateDefinition) {
		this.dataTemplateDefinition = dataTemplateDefinition;
	}

	public DefinitionKey getViewTemplateDefinition() {
		return viewTemplateDefinition;
	}

	public void setViewTemplateDefinition(DefinitionKey viewTemplateDefinition) {
		this.viewTemplateDefinition = viewTemplateDefinition;
	}

	public DefinitionKey getCssDefinition() {
		return cssDefinition;
	}

	public void setCssDefinition(DefinitionKey cssDefinition) {
		this.cssDefinition = cssDefinition;
	}

	public DefinitionKey getJsDefinition() {
		return jsDefinition;
	}

	public void setJsDefinition(DefinitionKey jsDefinition) {
		this.jsDefinition = jsDefinition;
	}

	public List<DefinitionKey> getDataReaderDefinitions() {
		return dataReaderDefinitions;
	}

	public void setDataReaderDefinitions(List<DefinitionKey> dataReaderDefinitions) {
		this.dataReaderDefinitions = dataReaderDefinitions;
	}

}
