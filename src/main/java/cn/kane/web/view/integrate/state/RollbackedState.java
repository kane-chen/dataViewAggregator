package cn.kane.web.view.integrate.state;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;

public class RollbackedState implements State {

	private static final IntegrateStatus ROLLBACK_STATE = IntegrateStatus.ROLLBACKED ;
	
	@Override
	public String status(String requirementId) {
		return ROLLBACK_STATE.status();
	}

	@Override
	public String actionName(String requirementId) {
		return ROLLBACK_STATE.forwardName();
	}

	@Override
	public String backwardName(String requirementId) {
		return ROLLBACK_STATE.backwardName();
	}

	@Override
	public void add(String requirementId, DefinitionKey key) {
		throw new UnsupportedOperationException("rollback cannot do any action") ;
	}

	@Override
	public void action(String requirementId) {
		throw new UnsupportedOperationException("rollback cannot do any action") ;
	}

	@Override
	public void backward(String requirementId) {
		throw new UnsupportedOperationException("rollback cannot do any action") ;

	}

	@Override
	public void disable(String requirementId) {
		throw new UnsupportedOperationException("rollback cannot do any action") ;
	}

	@Override
	public void rollback(String requirementId) {
		throw new UnsupportedOperationException("rollback cannot do any action") ;
	}

}
