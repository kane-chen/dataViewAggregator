package cn.kane.dynamicview.product.render.view;

import java.util.Map;

import cn.kane.dynamicview.product.vo.ViewModel;
import cn.kane.dynamicview.resource.entity.Resource;

public interface ViewMerger<T extends Resource> {

	ViewModel merge(T resource, Map<String, Object> context);

}
