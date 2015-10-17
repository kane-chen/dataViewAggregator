package cn.kane.web.view.aggregator.manager;

import org.apache.commons.beanutils.BeanUtils;

import cn.kane.web.view.aggregator.pojo.definition.WidgetDefinition;
import cn.kane.web.view.aggregator.service.manager.WidgetDefinitionManager;

import com.alibaba.fastjson.JSON;

public class OTSWidgetDefinitionManager extends AbstractOTSResourceDefinitionManager<WidgetDefinition>
		implements WidgetDefinitionManager {

	@Override
	public Class<WidgetDefinition> getTargetClass() {
		return WidgetDefinition.class;
	}

	@Override
	public WidgetDefinition formatBeforeWrite(WidgetDefinition definition) {
		if (null == definition) {
			return null;
		}
		String content = this.keys2Json(definition);
		definition.setContent(content);
		return definition;
	}

	private String keys2Json(WidgetDefinition definition) {
		WidgetDefinition cloneDefinition = null;
		try {
			cloneDefinition = (WidgetDefinition) BeanUtils.cloneBean(definition);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("cloneBean[%s] error", definition), e);
		}
		cloneDefinition.setKey(null);
		cloneDefinition.setDescription(null);
		return JSON.toJSONString(cloneDefinition);
	}

	@Override
	public WidgetDefinition parseAfterRead(WidgetDefinition definition) {
		if (null == definition) {
			return null;
		}
		WidgetDefinition parsedDefinition = new WidgetDefinition();
		parsedDefinition = this.json2Keys(definition.getContent(), parsedDefinition);
		parsedDefinition.setKey(definition.getKey());
		parsedDefinition.setDescription(definition.getDescription());
		return parsedDefinition;
	}

	private WidgetDefinition json2Keys(String content, WidgetDefinition definition) {
		if (null == content) {
			return definition;
		}
		WidgetDefinition parsedDefinition = JSON.parseObject(content, WidgetDefinition.class);
		try {
			BeanUtils.copyProperties(definition, parsedDefinition);
		} catch (Exception e) {
			throw new RuntimeException(String.format("set-field by json[%s] error", content), e);
		}
		return definition;
	}

}
