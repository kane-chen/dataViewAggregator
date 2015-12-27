package cn.kane.dynamicview.definition.manager;

import org.apache.commons.beanutils.BeanUtils;






import cn.kane.dynamicview.definition.entity.WidgetDefinition;
import cn.kane.dynamicview.definition.service.WidgetDefinitionManager;

import com.alibaba.fastjson.JSON;

public class BaseWidgetDefinitionManager extends AbstractResourceDefinitionManager<WidgetDefinition> implements WidgetDefinitionManager {

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
