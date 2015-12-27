package cn.kane.dynamicview.definition.manager;

import cn.kane.dynamicview.definition.entity.TemplateDefinition;
import cn.kane.dynamicview.definition.service.TemplateDefinitionManager;


public class BaseTemplateDefinitionManager extends AbstractResourceDefinitionManager<TemplateDefinition>
		implements TemplateDefinitionManager {

	@Override
	public TemplateDefinition formatBeforeWrite(TemplateDefinition definition) {
		return definition;
	}

	@Override
	public TemplateDefinition parseAfterRead(TemplateDefinition definition) {
		return definition;
	}

	@Override
	public Class<TemplateDefinition> getTargetClass() {
		return TemplateDefinition.class;
	}

}
