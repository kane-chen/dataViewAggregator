package cn.kane.web.view.aggregator.manager;

import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;

public class BaseStringResourceDefinitionManager extends AbstractResourceDefinitionManager<StringResourceDefinition>
		implements StringResourceDefinitionManager {

	public void init(){
	}
	
	@Override
	public StringResourceDefinition formatBeforeWrite(StringResourceDefinition definition) {
		return definition;
	}

	@Override
	public StringResourceDefinition parseAfterRead(StringResourceDefinition definition) {
		return definition;
	}

	@Override
	public Class<StringResourceDefinition> getTargetClass() {
		return StringResourceDefinition.class;
	}

}
