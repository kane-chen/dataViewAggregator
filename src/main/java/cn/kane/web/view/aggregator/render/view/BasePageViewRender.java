package cn.kane.web.view.aggregator.render.view;

import java.util.Map;

import cn.kane.web.view.aggregator.render.data.DataAggregator;
import cn.kane.web.view.aggregator.render.view.merge.ViewMerger;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.Page;
import cn.kane.web.view.aggregator.pojo.model.ViewModel;
import cn.kane.web.view.aggregator.service.loader.PageLoader;
import cn.kane.web.view.aggregator.service.render.PageViewRender;

public class BasePageViewRender implements PageViewRender {

	private PageLoader pageLoader;
	private DataAggregator dataAggregator;
	private ViewMerger<Page> pageViewMerger;

	@Override
	public ViewModel reander(DefinitionKey defKey, Map<String, Object> params) {
		Page page = pageLoader.getResourceByKey(defKey);
		// data-aggregate
		Map<String, Object> context = dataAggregator.aggregate(page.getDataReaderKeys(), params);
		ViewModel pageView = pageViewMerger.merge(page, context);
		return pageView;
	}

	public void setPageLoader(PageLoader pageLoader) {
		this.pageLoader = pageLoader;
	}

	public void setDataAggregator(DataAggregator dataAggregator) {
		this.dataAggregator = dataAggregator;
	}

	public void setPageViewMerger(ViewMerger<Page> pageViewMerger) {
		this.pageViewMerger = pageViewMerger;
	}

}
