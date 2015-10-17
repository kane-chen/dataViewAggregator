package cn.kane.web.view.aggregator.render.view;

import java.util.Map;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.ViewModel;
import cn.kane.web.view.aggregator.pojo.model.Widget;
import cn.kane.web.view.aggregator.render.data.DataAggregator;
import cn.kane.web.view.aggregator.render.view.merge.ViewMerger;
import cn.kane.web.view.aggregator.service.loader.WidgetLoader;
import cn.kane.web.view.aggregator.service.render.WidgetViewRender;

public class BaseWidgetViewRender implements WidgetViewRender {

	private WidgetLoader widgetLoader;
	private DataAggregator dataAggregator;
	private ViewMerger<Widget> widgetViewMerger;

	@Override
	public ViewModel reander(DefinitionKey defKey, Map<String, Object> params) {
		Widget widget = widgetLoader.getResourceByKey(defKey);
		// data-aggregate
		Map<String, Object> context = dataAggregator.aggregate(widget.getDataReaderKeys(), params);
		ViewModel pageView = widgetViewMerger.merge(widget, context);
		return pageView;
	}

	public void setWidgetLoader(WidgetLoader widgetLoader) {
		this.widgetLoader = widgetLoader;
	}

	public void setDataAggregator(DataAggregator dataAggregator) {
		this.dataAggregator = dataAggregator;
	}

	public void setWidgetViewMerger(ViewMerger<Widget> widgetViewMerger) {
		this.widgetViewMerger = widgetViewMerger;
	}

}
