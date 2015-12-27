package cn.kane.dynamicview.integrate.service;

public interface RequirementJoinService {

	void join(String operator,String requirementId) ;
	
	String query(String operator) ;
}
