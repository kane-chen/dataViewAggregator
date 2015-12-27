package cn.kane.dynamicview.resource.loader.compare;

import cn.kane.dynamicview.resource.entity.Resource;

public interface ResourceComparator<T extends Resource> {

	boolean isEquals(T res1, T res2);

}
