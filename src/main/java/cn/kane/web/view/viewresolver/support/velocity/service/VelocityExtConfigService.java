package cn.kane.web.view.viewresolver.support.velocity.service;

public interface VelocityExtConfigService {

	void upateMacro(String macroName, String content,String sourceTemplate, String[] argArray);

	Object costexp(Long costMillis) ;
	
}
