package cn.kane.web.view.integrate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.web.view.aggregator.pojo.definition.AbstractDefinition;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import cn.kane.web.view.integrate.pojo.Requirement;

public class MockChangesSynchronizeService {
	@Autowired
	private RequirementManageService olRequirementManageService ;
	@Autowired
	private ChangesManageService olChangesManageService ;
	@Autowired
	private StringResourceDefinitionManager olStringResourceDefinitionManager ;
	
	//stored
	private volatile String requirementId ;
	
	public void sync(Requirement requirement,List<DefinitionKey> defKeys,List<AbstractDefinition> defs){
		if(null == requirement || null == requirement.getId() || null == defKeys || null ==defs){
			throw new IllegalArgumentException() ;
		}
		Requirement olStoredReq = olRequirementManageService.get(requirement.getId()) ;
		if(null!= olStoredReq){
			throw new IllegalArgumentException(String.format("Requirement[%s] already exist,status is [%s]",olStoredReq.getId(),olStoredReq.getStatus())) ;
		}
		if(null != this.requirementId){
			throw new IllegalArgumentException(String.format("Requirement[%s] is pushing,please wait", this.requirementId)) ;
		}
		String requirementId = requirement.getId() ;
		this.requirementId = requirementId ;
		requirement.setStatus("publishing") ;
		olRequirementManageService.add(requirement) ;
		olChangesManageService.addAll(requirementId, defKeys) ;
		this.addChanges(requirementId, defs) ;
	}
	
	private void addChanges(String requirementId,List<AbstractDefinition> defs){
		for(AbstractDefinition def : defs){
			if(def instanceof StringResourceDefinition){
				this.olStringResourceDefinitionManager.add((StringResourceDefinition)def) ;
			}
		}
	}
	
	public String getPushingRequirementId(){
		return this.requirementId ;
	}
	
	public void published(){
		this.requirementId = null ;
	}
}
