package cn.kane.dynamicview.integrate.state;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.integrate.service.IntegrateStatus;
import cn.kane.dynamicview.integrate.service.State;

public class DisabledState implements State {

	private static final IntegrateStatus DISABLE_STATE = IntegrateStatus.DISABLED ;
	
	@Override
	public String status(String requirementId) {
		return DISABLE_STATE.status();
	}

	@Override
	public String actionName(String requirementId) {
		return DISABLE_STATE.forwardName();
	}

	@Override
	public String backwardName(String requirementId) {
		return DISABLE_STATE.backwardName();
	}

	@Override
	public void add(String requirementId, DefinitionKey key) {
		throw new UnsupportedOperationException("disable cannot do any action") ;
	}

	@Override
	public void action(String requirementId) {
		throw new UnsupportedOperationException("disable cannot do any action") ;
	}

	@Override
	public void backward(String requirementId) {
		throw new UnsupportedOperationException("disable cannot do any action") ;

	}

	@Override
	public void disable(String requirementId) {
		throw new UnsupportedOperationException("disable cannot do any action") ;
	}

	@Override
	public void rollback(String requirementId) {
		throw new UnsupportedOperationException("disable cannot do any action") ;
	}

}
