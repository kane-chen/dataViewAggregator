package cn.kane.dynamicview.integrate.state;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.integrate.entity.Requirement;
import cn.kane.dynamicview.integrate.service.RequirementManageService;
import cn.kane.dynamicview.integrate.service.State;

public class StateFacade implements State {
	@Autowired
	private RequirementManageService requirementManageService ;
	private Map<String,State> stateMapping ;
	
	@Override
	public String status(String requirementId) {
		return this.getState(requirementId).status(requirementId);
	}

	@Override
	public String actionName(String requirementId) {
		return this.getState(requirementId).actionName(requirementId);
	}

	@Override
	public String backwardName(String requirementId) {
		return this.getState(requirementId).backwardName(requirementId);
	}

	@Override
	public void add(String requirementId, DefinitionKey key) {
		this.getState(requirementId).add(requirementId,key);
	}

	@Override
	public void action(String requirementId) {
		this.getState(requirementId).action(requirementId);
	}

	@Override
	public void backward(String requirementId) {
		this.getState(requirementId).backward(requirementId);
	}

	@Override
	public void disable(String requirementId) {
		this.getState(requirementId).disable(requirementId);
	}

	@Override
	public void rollback(String requirementId) {
		this.getState(requirementId).rollback(requirementId);
	}

	private State getState(String requirementId){
		Requirement requirement = requirementManageService.get(requirementId) ;
		if(null == requirement){
			throw new IllegalArgumentException(String.format("Requirement[%s] not found", requirementId));
		}
		State targetState = stateMapping.get(requirement.getStatus()) ;
		if(null == targetState){
			throw new IllegalArgumentException(String.format("Requirement[%s] status[%s] not support", requirementId,requirement.getStatus()));
		}
		return targetState ;
	}

	public Map<String,State> getStateMapping() {
		return stateMapping;
	}

	public void setStateMapping(Map<String,State> stateMapping) {
		this.stateMapping = stateMapping;
	}
	
}
