package cn.kane.dynamicview.product.render.view;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.product.vo.ViewModel;
import cn.kane.dynamicview.resource.entity.Page;
import cn.kane.dynamicview.resource.entity.Widget;
import cn.kane.dynamicview.resource.service.WidgetLoader;
import cn.kane.dynamicview.util.DefinitionKeyUtils;

public class PageViewMerger implements ViewMerger<Page> {
	@Autowired
	private WidgetLoader widgetLoader;
	@Autowired
	private ViewMerger<Widget> widgetViewMerger;
	@Autowired
	private TemplateRender templateRender;

	@Override
	public ViewModel merge(Page page, Map<String, Object> context) {
		if (null == page) {
			return null;
		}
		// widget-merged(template)
		Map<String, ViewModel> widgetViews = new HashMap<String, ViewModel>(page.getWidgetKeys().size());
		for (DefinitionKey key : page.getWidgetKeys()) {
			Widget widget = widgetLoader.getResourceByKey(key);
			ViewModel widgetView = widgetViewMerger.merge(widget, context);
			widgetViews.put(DefinitionKeyUtils.format(key), widgetView);
		}
		// multi-merge(css&js)
		StringBuilder cssMergedBuffer = new StringBuilder();
		StringBuilder jsMergedBuffer = new StringBuilder();
		for (ViewModel view : widgetViews.values()) {
			cssMergedBuffer.append(view.getCss());
			jsMergedBuffer.append(view.getJs());
		}
		String mergedCss = cssMergedBuffer.toString();
		String mergedJs = jsMergedBuffer.toString();
		// detail
		Map<String, Object> details = new HashMap<String, Object>(widgetViews.size());
		for (String key : widgetViews.keySet()) {
			details.put(key, widgetViews.get(key).getContent());
		}
		String mergedDetail = templateRender.render(page.getPageDefinition().getLayoutDefinition(), details);
		// result
		ViewModel pageView = new ViewModel();
		pageView.setJs(mergedJs);
		pageView.setCss(mergedCss);
		pageView.setContent(mergedDetail);
		return pageView;
	}

}
