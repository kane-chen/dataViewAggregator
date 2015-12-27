package cn.kane.dynamicview.definition.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class AbstractDefinition implements Serializable {

	private static final long serialVersionUID = 3645319783567942743L;

	private DefinitionKey key;
	private String applyVersion ;
	private String description;
	private String operator ;
	private Date ctime ;
	private Date mtime ;
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getMtime() {
		return mtime;
	}

	public void setMtime(Date mtime) {
		this.mtime = mtime;
	}

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

	public String getApplyVersion() {
		return applyVersion;
	}
	
	public void setApplyVersion(String applyVersion) {
		this.applyVersion = applyVersion;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}
}
