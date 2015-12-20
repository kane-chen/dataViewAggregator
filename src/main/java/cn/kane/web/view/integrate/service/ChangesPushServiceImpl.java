package cn.kane.web.view.integrate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.web.view.aggregator.pojo.definition.AbstractDefinition;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.service.manager.ResourceDefinitionManager;
import cn.kane.web.view.integrate.pojo.Requirement;

public class ChangesPushServiceImpl implements ChangesPushService {

	@Autowired
	private RequirementManageService requirementManageService ;
	@Autowired
	private ChangesManageService changesManageService ; 
	@Autowired
	private ResourceDefinitionManager<AbstractDefinition> resourceDefinitionManagerFacade ;
	@Autowired
	private MockChangesSynchronizeService mockChangesSynchronizeService ;
	@Override
	public void push(String requirementId) {
		//requirement
		Requirement requirement = this.requirementManageService.get(requirementId) ;
		if(null == requirement){
			throw new IllegalArgumentException(String.format("Requirement[id=%s] not found", requirementId));
		}
		if(!"new".equalsIgnoreCase(requirement.getStatus())){
			throw new IllegalMonitorStateException(String.format("Requirement[id=%s] status is[%s],cannot push", requirementId,requirement.getStatus()));
		}
		//changes
		List<DefinitionKey> defKeys = changesManageService.list(requirementId) ;
		if(null == defKeys || defKeys.isEmpty()){
			throw new IllegalArgumentException(String.format("Requirement[id=%s] not found", requirementId));
		}
		//pushing-status
		requirement.setStatus("pushing") ;
		requirementManageService.edit(requirement) ;
		//push changes
		List<AbstractDefinition> defs = this.getDefsByKeys(defKeys) ;
		mockChangesSynchronizeService.sync(requirement, defKeys, defs) ;
		//pushing-status
		requirement.setStatus("pushed") ;
		requirementManageService.edit(requirement) ;
	}
	
	private List<AbstractDefinition> getDefsByKeys(List<DefinitionKey> defKeys){
		if(null == defKeys || defKeys.isEmpty()){
			return null ;
		}
		List<AbstractDefinition> defs = new ArrayList<AbstractDefinition>(defKeys.size()) ;
		for(DefinitionKey key : defKeys){
			AbstractDefinition def = resourceDefinitionManagerFacade.get(key);
			defs.add(def) ;
		}
		return defs ;
	}
}
