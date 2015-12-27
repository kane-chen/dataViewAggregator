package cn.kane.dynamicview.integrate.service;

import cn.kane.dynamicview.definition.entity.DefinitionKey;

public interface State {
	
	String status(String requirementId) ;
	
	String actionName(String requirementId);
	
	String backwardName(String requirementId) ;
	
	void add(String requirementId,DefinitionKey key) ;
	
	void action(String requirementId) ;
	
	void backward(String requirementId) ;

	void disable(String requirementId) ;

	void rollback(String requirementId) ;
	
	
	
}
