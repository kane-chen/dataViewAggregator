package cn.kane.dynamicview.resource.loader.compare;

import org.apache.commons.lang3.builder.EqualsBuilder;

import cn.kane.dynamicview.resource.entity.Widget;

public class WidgetComparator implements ResourceComparator<Widget> {

	@Override
	public boolean isEquals(Widget res1, Widget res2) {
		return EqualsBuilder.reflectionEquals(res1, res2);
	}
}
