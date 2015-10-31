package cn.kane.web.view.viewresolver.support.velocity;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;

public class VelocityExtRefrenceConfigurer extends VelocityConfigurer {

	private StringResourceDefinitionManager stringResourceDefinitionManager ;
	
	@Override
	protected void postProcessVelocityEngine(VelocityEngine velocityEngine) {
		super.postProcessVelocityEngine(velocityEngine);
		velocityEngine.addProperty("templateManager", stringResourceDefinitionManager);
	}

	public void setStringResourceDefinitionManager(StringResourceDefinitionManager stringResourceDefinitionManager) {
		this.stringResourceDefinitionManager = stringResourceDefinitionManager;
	}
	
}
