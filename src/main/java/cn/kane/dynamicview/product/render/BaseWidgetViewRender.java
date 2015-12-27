package cn.kane.dynamicview.product.render;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.product.render.data.DataAggregator;
import cn.kane.dynamicview.product.render.view.ViewMerger;
import cn.kane.dynamicview.product.service.WidgetViewRender;
import cn.kane.dynamicview.product.vo.ViewModel;
import cn.kane.dynamicview.resource.entity.Widget;
import cn.kane.dynamicview.resource.service.WidgetLoader;

public class BaseWidgetViewRender implements WidgetViewRender {
	@Autowired
	private WidgetLoader widgetLoader;
	@Autowired
	private DataAggregator dataAggregator;
	@Autowired
	private ViewMerger<Widget> widgetViewMerger;

	@Override
	public ViewModel reander(DefinitionKey defKey, Map<String, Object> params) {
		// load
		Widget widget = widgetLoader.getResourceByKey(defKey);
		// data-aggregate
		Map<String, Object> context = dataAggregator.aggregate(widget.getDataReaderKeys(), params);
		// render
		ViewModel pageView = widgetViewMerger.merge(widget, context);
		return pageView;
	}

}
