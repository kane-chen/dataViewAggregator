package cn.kane.web.view.aggregator.render.view.render;

import java.util.List;
import java.util.Map;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;

public interface TemplateRender {

	String render(DefinitionKey templateKey, Map<String, Object> param);

	String merge(List<DefinitionKey> templateKeys, Map<String, Object> params);

}
