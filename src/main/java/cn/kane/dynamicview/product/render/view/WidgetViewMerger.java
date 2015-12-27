package cn.kane.dynamicview.product.render.view;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.dynamicview.product.vo.ViewModel;
import cn.kane.dynamicview.resource.entity.Widget;

public class WidgetViewMerger implements ViewMerger<Widget> {
	@Autowired
	private TemplateRender templateRender;

	@Override
	public ViewModel merge(Widget widget, Map<String, Object> context) {
		if (null == widget) {
			return null;
		}
		if(null == context){
			context = new HashMap<String, Object>(1) ;
		}
		//context-parameter
		templateRender.render(widget.getWidgetDefinition().getDataTemplateDefinition(), context);
		//template-render
		String detail = templateRender.render(widget.getWidgetDefinition().getViewTemplateDefinition(), context);
		//wrapper
		ViewModel view = new ViewModel();
		view.setCss(widget.getCss());
		view.setJs(widget.getJs());
		view.setContent(detail);
		return view;
	}

}
