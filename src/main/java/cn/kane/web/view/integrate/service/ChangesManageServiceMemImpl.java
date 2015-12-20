package cn.kane.web.view.integrate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.integrate.pojo.Changes;

public class ChangesManageServiceMemImpl implements ChangesManageService {

	private ConcurrentHashMap<String,Changes> store = new ConcurrentHashMap<String, Changes>() ;
	private ConcurrentHashMap<String,Changes> backupStore = new ConcurrentHashMap<String, Changes>() ;
	
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
	public void addAll(String requirementId, List<DefinitionKey> keys) {
		Changes stored = store.get(requirementId) ;
		if(null != stored){
			throw new IllegalArgumentException(String.format("Requirement[%s] existed", requirementId)) ;
		}
		stored = new Changes() ;
		stored.setId(requirementId) ;
		stored.getChanges().addAll(keys) ;
		store.put(requirementId, stored) ;
	}

	@Override
	public void addAllBackup(String requirementId, List<DefinitionKey> keys) {
		if(null == requirementId || null == keys || keys.isEmpty()){
			//WARN
			return ;
		}
		Changes stored = backupStore.get(requirementId) ;
		if(null != stored){
			throw new IllegalArgumentException(String.format("Requirement[%s] existed", requirementId)) ;
		}
		stored = new Changes() ;
		stored.setId(requirementId) ;
		stored.getChanges().addAll(keys) ;
		backupStore.put(requirementId, stored) ;
	}

	@Override
	public List<DefinitionKey> listBackup(String requirementId) {
		Changes stored = backupStore.get(requirementId) ;
		if(null == stored){
			return  null ;
		}
		return new ArrayList<DefinitionKey>(stored.getChanges()) ;
	}

}
