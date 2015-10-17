package cn.kane.web.view.aggregator.render.view.merge;

import java.util.Map;

import cn.kane.web.view.aggregator.render.view.render.TemplateRender;
import cn.kane.web.view.aggregator.pojo.model.ViewModel;
import cn.kane.web.view.aggregator.pojo.model.Widget;

public class WidgetViewMerger implements ViewMerger<Widget> {

	private TemplateRender templateRender;

	@Override
	public ViewModel merge(Widget widget, Map<String, Object> context) {
		if (null == widget) {
			return null;
		}
		templateRender.render(widget.getWidgetDefinition().getDataTemplateDefinition(), context);
		String detail = templateRender.render(widget.getWidgetDefinition().getViewTemplateDefinition(), context);
		ViewModel view = new ViewModel();
		view.setCss(widget.getCss());
		view.setJs(widget.getJs());
		view.setContent(detail);
		return view;
	}

	public void setTemplateRender(TemplateRender templateRender) {
		this.templateRender = templateRender;
	}

}
