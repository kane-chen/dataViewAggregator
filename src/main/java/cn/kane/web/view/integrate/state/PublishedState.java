package cn.kane.web.view.integrate.state;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.integrate.service.ChangesManageService;
import cn.kane.web.view.integrate.service.RequirementManageService;

public class PublishedState implements State {

	private static final IntegrateStatus PUBLISH_STATE = IntegrateStatus.PUBLISHED ;
	@Autowired
	private RequirementManageService requirementManageService;
	@Autowired
	private ChangesManageService changesManageService ;
	@Override
	public String status(String requirementId) {
		return PUBLISH_STATE.status();
	}

	@Override
	public String actionName(String requirementId) {
		return PUBLISH_STATE.forwardName();
	}

	@Override
	public String backwardName(String requirementId) {
		return PUBLISH_STATE.backwardName();
	}

	@Override
	public void add(String requirementId, DefinitionKey key) {
		throw new UnsupportedOperationException(requirementId) ;
	}

	@Override
	public void action(String requirementId) {
		throw new UnsupportedOperationException(requirementId) ;
	}

	@Override
	public void backward(String requirementId) {
		requirementManageService.compareAndSetStatus(requirementId,this.status(requirementId),IntegrateStatus.COMMITED.status());
	}

	@Override
	public void disable(String requirementId) {
		requirementManageService.compareAndSetStatus(requirementId,this.status(requirementId),IntegrateStatus.DISABLED.status());
	}

	@Override
	public void rollback(String requirementId) {
		//resume trunk
		List<DefinitionKey> backupKeys = changesManageService.listBackup(requirementId) ;
		changesManageService.writeTrunk(backupKeys);
		requirementManageService.compareAndSetStatus(requirementId,this.status(requirementId),IntegrateStatus.ROLLBACKED.status());
	}

}
