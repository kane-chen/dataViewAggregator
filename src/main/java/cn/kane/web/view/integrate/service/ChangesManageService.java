package cn.kane.web.view.integrate.service;

import java.util.List;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;

public interface ChangesManageService {

	void add(String requirementId,DefinitionKey key) ;
	
	List<DefinitionKey> list(String requirementId) ;
	
	void addAll(String requirementId,List<DefinitionKey> key) ;
	
	void addAllBackup(String requirementId,List<DefinitionKey> keys) ;

	List<DefinitionKey> listBackup(String requirementId) ;
}
