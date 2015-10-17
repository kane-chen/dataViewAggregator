package cn.kane.web.view.aggregator.render.view.merge;

import java.util.Map;

import cn.kane.web.view.aggregator.pojo.model.Resource;
import cn.kane.web.view.aggregator.pojo.model.ViewModel;

public interface ViewMerger<T extends Resource> {

	ViewModel merge(T resource, Map<String, Object> context);

}
