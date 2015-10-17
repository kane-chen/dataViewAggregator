package cn.kane.web.view.aggregator.manager;

import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;

public class OTSStringResourceDefinitionManager extends AbstractOTSResourceDefinitionManager<StringResourceDefinition>
		implements StringResourceDefinitionManager {

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
