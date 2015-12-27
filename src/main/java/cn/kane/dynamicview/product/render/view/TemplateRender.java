package cn.kane.dynamicview.product.render.view;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import cn.kane.dynamicview.definition.entity.DefinitionKey;

import com.google.common.base.Charsets;

public interface TemplateRender {

	Charset TEMPLATE_CHARSET = Charsets.UTF_8 ;
	
	String render(DefinitionKey templateKey, Map<String, Object> param);

	String merge(List<DefinitionKey> templateKeys, Map<String, Object> params);

}
