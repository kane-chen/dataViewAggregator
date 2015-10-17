package cn.kane.web.view.aggregator.pojo.definition;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class AbstractDefinition implements Serializable {

	private static final long serialVersionUID = 3645319783567942743L;

	private DefinitionKey key;
	private String description;

	public DefinitionKey getKey() {
		return key;
	}

	public void setKey(DefinitionKey key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

}
