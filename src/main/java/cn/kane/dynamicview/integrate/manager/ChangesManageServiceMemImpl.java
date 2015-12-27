package cn.kane.dynamicview.integrate.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.dynamicview.definition.entity.AbstractDefinition;
import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.definition.service.ResourceDefinitionManager;
import cn.kane.dynamicview.integrate.entity.Changes;
import cn.kane.dynamicview.integrate.service.ChangesManageService;


public class ChangesManageServiceMemImpl implements ChangesManageService {

	private ConcurrentHashMap<String,Changes> store = new ConcurrentHashMap<String, Changes>() ;
	private ConcurrentHashMap<String,Changes> backupStore = new ConcurrentHashMap<String, Changes>() ;
	@Autowired
	private ResourceDefinitionManager<AbstractDefinition> resourceDefinitionManagerFacade ;
	
	@Override
	public void add(String requirementId, DefinitionKey key) {
		if(null == requirementId || null == key){
			throw new IllegalArgumentException("requirementId & definitionKey cannot be null") ;
		}
		Changes stored = store.get(requirementId) ;
		if(null == stored){
			stored = new Changes() ;
			stored.setId(requirementId) ;
			Changes preStored = store.putIfAbsent(requirementId, stored) ;
			if(null != preStored){
				stored = preStored ;
			}
		}
		stored.getChanges().add(key) ;
	}

	@Override
	public List<DefinitionKey> list(String requirementId) {
		if(!store.containsKey(requirementId)){
			return null;
		}
		return new ArrayList<DefinitionKey>(store.get(requirementId).getChanges()) ;
	}

	@Override
	public void backup(String requirementId) {
		//changed-keys
		List<DefinitionKey> changedKeys = this.list(requirementId) ;
		if(null == requirementId || null == changedKeys || changedKeys.isEmpty()){
			//WARN
			return ;
		}
		//backup-keys
		List<DefinitionKey> backupKeys = this.getBackupOfChangedKeys(changedKeys) ;
		if(null == backupKeys || backupKeys.isEmpty()){
			//nothing need to backup
			return ;
		}
		//ReEntrant,no check duplicate-backup
//		Changes stored = backupStore.get(requirementId) ;
//		if(null != stored){
//			throw new IllegalArgumentException(String.format("Requirement[%s] existed", requirementId)) ;
//		}
		//backup
		Changes store = new Changes() ;
		store.setId(requirementId) ;
		store.getChanges().addAll(backupKeys) ;
		backupStore.put(requirementId, store) ;
	}
	
	private List<DefinitionKey> getBackupOfChangedKeys(List<DefinitionKey> changedKeys){
		List<DefinitionKey> backupKeys = new ArrayList<DefinitionKey>(changedKeys.size()) ;
		for(DefinitionKey key : changedKeys){
			DefinitionKey tmpKey = this.clone(key) ;
			tmpKey.setVersion(TRUNK_VERSION) ;
			AbstractDefinition prevDef = resourceDefinitionManagerFacade.get(tmpKey) ;
			if(null!=prevDef){
				tmpKey.setVersion(prevDef.getApplyVersion()) ;
			}else{
				tmpKey.setVersion(REMOVED_VERSION);
			}
			backupKeys.add(tmpKey) ;
		}
		return backupKeys ;
	}

	private DefinitionKey clone(DefinitionKey key){
		if(key == null){
			return null ;
		}
		DefinitionKey clone = new DefinitionKey() ;
		clone.setType(key.getType());
		clone.setName(key.getName());
		clone.setVersion(key.getVersion());
		return clone ;
	}
	
	@Override
	public List<DefinitionKey> listBackup(String requirementId) {
		Changes stored = backupStore.get(requirementId) ;
		if(null == stored){
			return  null ;
		}
		return new ArrayList<DefinitionKey>(stored.getChanges()) ;
	}

	@Override
	public void writeTrunk(List<DefinitionKey> keys) {
		//TODO non-transaction
		this.removeTrunk(keys);
		List<AbstractDefinition> definitions = this.getDefinitions(keys) ;
		this.writeToTrunk(definitions);
	}

	private void removeTrunk(List<DefinitionKey> keys){
		for(DefinitionKey key : keys){
			//add >> RollBack >> remove
			if(REMOVED_VERSION.equalsIgnoreCase(key.getVersion())){
				DefinitionKey newKey = this.clone(key) ;
				newKey.setVersion(TRUNK_VERSION) ;
				resourceDefinitionManagerFacade.remove(newKey);
			}
		}
	}
	
	private void writeToTrunk(List<AbstractDefinition> defs){
		for(AbstractDefinition def : defs){
			//apply-version
			String applyVersion = def.getKey().getVersion() ;
			//key.clone
			DefinitionKey newKey = this.clone(def.getKey()) ;
			newKey.setVersion(TRUNK_VERSION) ;
			//switch trunk
			def.setApplyVersion(applyVersion);
			def.setKey(newKey);
			//write trunk
			if(null!=resourceDefinitionManagerFacade.get(newKey)){
				resourceDefinitionManagerFacade.edit(def) ;
			}else{
				resourceDefinitionManagerFacade.add(def) ;
			}
		}
	}
	
	private List<AbstractDefinition> getDefinitions(List<DefinitionKey> keys){
		List<AbstractDefinition> defs = new ArrayList<AbstractDefinition>(keys.size());
		for(DefinitionKey key : keys){
			AbstractDefinition def = resourceDefinitionManagerFacade.get(key) ;
			if(null != def){
				defs.add(def) ;
			}
		}
		return defs ;
	}
}
