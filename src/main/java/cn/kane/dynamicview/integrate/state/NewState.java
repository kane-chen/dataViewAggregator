package cn.kane.dynamicview.integrate.state;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.integrate.service.ChangesManageService;
import cn.kane.dynamicview.integrate.service.IntegrateStatus;
import cn.kane.dynamicview.integrate.service.RequirementManageService;
import cn.kane.dynamicview.integrate.service.State;

public class NewState implements State {

	private static final IntegrateStatus NEW_STATE = IntegrateStatus.NEW ;
	@Autowired
	private RequirementManageService requirementManageService ;
	@Autowired
	private ChangesManageService changesManageService ;
	
	@Override
	public String status(String requirementId) {
		return NEW_STATE.status();
	}
	
	@Override
	public String actionName(String requirementId) {
		return NEW_STATE.forwardName();
	}
	
	@Override
	public String backwardName(String requirementId) {
		return NEW_STATE.backwardName();
	}
	
	@Override
	public void add(String requirementId, DefinitionKey key) {
		changesManageService.add(requirementId, key);
	}

	@Override
	public void action(String requirementId) {
		requirementManageService.compareAndSetStatus(requirementId,this.status(requirementId),IntegrateStatus.COMMITED.status());
	}

	@Override
	public void backward(String requirementId) {
		throw new UnsupportedOperationException(requirementId) ;
	}

	@Override
	public void rollback(String requirementId) {
		throw new UnsupportedOperationException(requirementId) ;
	}

	@Override
	public void disable(String requirementId) {
		requirementManageService.compareAndSetStatus(requirementId,this.status(requirementId),IntegrateStatus.DISABLED.status());
	}

}
