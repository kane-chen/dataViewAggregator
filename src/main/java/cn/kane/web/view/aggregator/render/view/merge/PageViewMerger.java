package cn.kane.web.view.aggregator.render.view.merge;

import java.util.HashMap;
import java.util.Map;

import cn.kane.web.view.aggregator.render.view.render.TemplateRender;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.Page;
import cn.kane.web.view.aggregator.pojo.model.ViewModel;
import cn.kane.web.view.aggregator.pojo.model.Widget;
import cn.kane.web.view.aggregator.service.loader.WidgetLoader;

public class PageViewMerger implements ViewMerger<Page> {

	private WidgetLoader widgetLoader;
	private ViewMerger<Widget> widgetViewMerger;
	private TemplateRender templateRender;

	@Override
	public ViewModel merge(Page page, Map<String, Object> context) {
		if (null == page) {
			return null;
		}
		// widget-merged
		Map<String, ViewModel> widgetViews = new HashMap<String, ViewModel>(page.getWidgetKeys().size());
		for (DefinitionKey key : page.getWidgetKeys()) {
			Widget widget = widgetLoader.getResourceByKey(key);
			ViewModel widgetView = widgetViewMerger.merge(widget, context);
			widgetViews.put(key.getName(), widgetView);
		}
		// multi-merge
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

	public void setWidgetLoader(WidgetLoader widgetLoader) {
		this.widgetLoader = widgetLoader;
	}

	public void setWidgetViewMerger(ViewMerger<Widget> widgetViewMerger) {
		this.widgetViewMerger = widgetViewMerger;
	}

	public void setTemplateRender(TemplateRender templateRender) {
		this.templateRender = templateRender;
	}

}
