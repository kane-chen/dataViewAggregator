package cn.kane.dynamicview.integrate.entity;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

import cn.kane.dynamicview.definition.entity.DefinitionKey;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;

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
