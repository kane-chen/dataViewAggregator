package cn.kane.web.view.aggregator.loader.compare;

import cn.kane.web.view.aggregator.pojo.model.Resource;

public interface ResourceComparator<T extends Resource> {

	boolean isEquals(T res1, T res2);

}
