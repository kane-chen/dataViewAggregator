package cn.kane.web.view.integrate.pojo;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;

public class Changes {

	private String id ;
	private Set<DefinitionKey> changes = new ConcurrentHashSet<DefinitionKey>() ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Set<DefinitionKey> getChanges() {
		return changes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
