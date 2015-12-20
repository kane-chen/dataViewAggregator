package cn.kane.web.view.integrate.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

public class RequirementJoinServiceMemImpl implements RequirementJoinService{

	private Map<String,String> requirementMembers = new ConcurrentHashMap<String, String>() ;
	
	@Override
	public void join(String operator, String requirementId) {
		if(StringUtils.isBlank(operator) || null == requirementId){
			throw new IllegalArgumentException(String.format("illegal-argument[operatoe=%s,requirementId=%s]", operator,requirementId)) ;
		}
		requirementMembers.put(operator, requirementId) ;
	}

	@Override
	public String query(String operator) {
		return requirementMembers.get(operator);
	}

}
