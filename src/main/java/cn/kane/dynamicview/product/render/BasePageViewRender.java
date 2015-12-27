package cn.kane.dynamicview.product.render;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.product.render.data.DataAggregator;
import cn.kane.dynamicview.product.render.view.ViewMerger;
import cn.kane.dynamicview.product.service.PageViewRender;
import cn.kane.dynamicview.product.vo.ViewModel;
import cn.kane.dynamicview.resource.entity.Page;
import cn.kane.dynamicview.resource.service.PageLoader;

public class BasePageViewRender implements PageViewRender {
	@Autowired
	private PageLoader pageLoader;
	@Autowired
	private DataAggregator dataAggregator;
	@Autowired
	private ViewMerger<Page> pageViewMerger;

	@Override
	public ViewModel reander(DefinitionKey defKey, Map<String, Object> params) {
		// load
		Page page = pageLoader.getResourceByKey(defKey);
		// data-aggregate
		Map<String, Object> context = dataAggregator.aggregate(page.getDataReaderKeys(), params);
		// render
		ViewModel pageView = pageViewMerger.merge(page, context);
		return pageView;
	}

}
