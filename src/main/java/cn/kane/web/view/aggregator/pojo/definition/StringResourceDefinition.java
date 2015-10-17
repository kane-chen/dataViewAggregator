package cn.kane.web.view.aggregator.pojo.definition;

import java.io.Serializable;

public class StringResourceDefinition extends AbstractDefinition implements Serializable {

	private static final long serialVersionUID = -5816398616203308727L;

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
