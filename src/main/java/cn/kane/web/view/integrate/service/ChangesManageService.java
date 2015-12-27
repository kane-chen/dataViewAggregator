package cn.kane.web.view.integrate.service;

import java.util.List;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;

public interface ChangesManageService {
	
	/* enable-resource-version */
	String TRUNK_VERSION = "TRUNK" ;
	/* disable-resource-version */
	String REMOVED_VERSION = "REMOVED" ;

	void add(String requirementId,DefinitionKey key) ;
	
	List<DefinitionKey> list(String requirementId) ;
	
	void backup(String requirementId) ;

	List<DefinitionKey> listBackup(String requirementId) ;
	
	void writeTrunk(List<DefinitionKey> keys) ;
}
