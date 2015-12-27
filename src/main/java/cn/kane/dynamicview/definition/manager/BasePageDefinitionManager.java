package cn.kane.dynamicview.definition.manager;

import org.apache.commons.beanutils.BeanUtils;

import cn.kane.dynamicview.definition.entity.PageDefinition;
import cn.kane.dynamicview.definition.service.PageDefinitionManager;

import com.alibaba.fastjson.JSON;


public class BasePageDefinitionManager extends AbstractResourceDefinitionManager<PageDefinition>
		implements PageDefinitionManager {

	@Override
	public Class<PageDefinition> getTargetClass() {
		return PageDefinition.class;
	}

	@Override
	public PageDefinition formatBeforeWrite(PageDefinition definition) {
		if (null == definition) {
			return null;
		}
		String content = this.keys2Json(definition);
		definition.setContent(content);
		return definition;
	}

	private String keys2Json(PageDefinition definition) {
		PageDefinition cloneDefinition = null;
		try {
			cloneDefinition = (PageDefinition) BeanUtils.cloneBean(definition);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("cloneBean[%s] error", definition), e);
		}
		cloneDefinition.setKey(null);
		cloneDefinition.setDescription(null);
		return JSON.toJSONString(cloneDefinition);
	}

	@Override
	public PageDefinition parseAfterRead(PageDefinition definition) {
		if (null == definition) {
			return null;
		}
		PageDefinition parsedfinition = new PageDefinition();
		parsedfinition = this.json2Keys(definition.getContent(), parsedfinition);
		parsedfinition.setKey(definition.getKey());
		parsedfinition.setDescription(definition.getDescription());
		return parsedfinition;
	}

	private PageDefinition json2Keys(String content, PageDefinition definition) {
		if (null == content) {
			return definition;
		}
		PageDefinition parsedDefinition = JSON.parseObject(content, PageDefinition.class);
		try {
			BeanUtils.copyProperties(definition, parsedDefinition);
		} catch (Exception e) {
			throw new RuntimeException(String.format("set-field by json[%s] error", content), e);
		}
		return definition;
	}

}
