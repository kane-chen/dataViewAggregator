package cn.kane.web.view.integrate.service;

public interface ChangesPublishService {

	void publish(String requirementId) ;
	
	void rollback(String requirementId) ;
	
}
