package cn.kane.web.view.integrate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.web.view.aggregator.pojo.definition.AbstractDefinition;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.service.manager.ResourceDefinitionManager;
import cn.kane.web.view.integrate.pojo.Requirement;

public class ChangesPublishServiceImpl implements ChangesPublishService {
	
	private static final String TRUNK_VERSION = "trunk" ;
	
	@Autowired
	private RequirementManageService olRequirementManageService ;
	@Autowired
	private ChangesManageService olChangesManageService ;
	@Autowired
	private ResourceDefinitionManager<AbstractDefinition> olResourceDefinitionManagerFacade ;
	@Override
	public void publish(String requirementId) {
		//requirement
		Requirement requirement = olRequirementManageService.get(requirementId) ;
		if(null == requirement){
			throw new IllegalArgumentException(String.format("Requirement[id=%s] not found", requirementId));
		}
		if(!"pushed".equalsIgnoreCase(requirement.getStatus())){
			throw new IllegalMonitorStateException(String.format("Requirement[id=%s] status is[%s],cannot push", requirementId,requirement.getStatus()));
		}
		requirement.setStatus("publishing");
		olRequirementManageService.edit(requirement) ;
		//changes
		List<DefinitionKey> defKeys = olChangesManageService.list(requirementId) ;
		this.backupChangedKey(requirementId,defKeys) ;
		List<AbstractDefinition> defs = this.getDefs(defKeys);
		this.writeToTrunk(defs);
		requirement.setStatus("published");
		olRequirementManageService.edit(requirement);
	}

	private void backupChangedKey(String requirementId,List<DefinitionKey> keys){
		List<DefinitionKey> backupKeys = this.getApplyedVersionOfChangedKeys(keys) ;
		olChangesManageService.addAllBackup(requirementId, backupKeys) ;
	}
	
	private List<DefinitionKey> getApplyedVersionOfChangedKeys(List<DefinitionKey> changedKeys){
		List<DefinitionKey> backupKeys = new ArrayList<DefinitionKey>(changedKeys.size()) ;
		for(DefinitionKey key : changedKeys){
			DefinitionKey tmpKey = new DefinitionKey() ;
			tmpKey.setType(key.getType());
			tmpKey.setName(key.getName()) ;
			tmpKey.setVersion(TRUNK_VERSION) ;
			AbstractDefinition prevDef = olResourceDefinitionManagerFacade.get(tmpKey) ;
			if(null!=prevDef){
				tmpKey.setVersion(prevDef.getApplyVersion()) ;
				backupKeys.add(tmpKey) ;
			}
		}
		return backupKeys ;
	}
	
	private void writeToTrunk(List<AbstractDefinition> defs){
		for(AbstractDefinition def : defs){
			def.setApplyVersion(def.getKey().getVersion());
			DefinitionKey key = def.getKey() ;
			key.setVersion(TRUNK_VERSION) ;
			if(null!=olResourceDefinitionManagerFacade.get(key)){
				olResourceDefinitionManagerFacade.edit(def) ;
			}else{
				olResourceDefinitionManagerFacade.add(def) ;
			}
		}
	}
	
	private List<AbstractDefinition> getDefs(List<DefinitionKey> keys){
		List<AbstractDefinition> defs = new ArrayList<AbstractDefinition>(keys.size());
		for(DefinitionKey key : keys){
			AbstractDefinition def = olResourceDefinitionManagerFacade.get(key) ;
			if(null != def){
				defs.add(def) ;
			}
		}
		return defs ;
	}

	@Override
	public void rollback(String requirementId) {
		Requirement requirement = olRequirementManageService.get(requirementId) ;
		if(null == requirement){
			throw new IllegalArgumentException(String.format("Requirement[id=%s] not found", requirementId));
		}
		if(!"published".equalsIgnoreCase(requirement.getStatus())){
			throw new IllegalMonitorStateException(String.format("Requirement[id=%s] status is[%s],cannot push", requirementId,requirement.getStatus()));
		}
		requirement.setStatus("rollbacking");
		olRequirementManageService.edit(requirement) ;
		
		List<DefinitionKey> backupKeys = olChangesManageService.listBackup(requirementId) ;
		List<AbstractDefinition> backupDefs = this.getDefs(backupKeys) ;
		this.writeToTrunk(backupDefs);
		
		requirement.setStatus("rollbacked");
		olRequirementManageService.edit(requirement);
	}

}
