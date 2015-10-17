package cn.kane.web.view.aggregator.pojo.model;

import java.io.Serializable;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;

public abstract class Resource implements Serializable {

	private static final long serialVersionUID = 6606477140113563011L;

	private DefinitionKey definitionKey;

	public DefinitionKey getDefinitionKey() {
		return definitionKey;
	}

	public void setDefinitionKey(DefinitionKey definitionKey) {
		this.definitionKey = definitionKey;
	}

}
