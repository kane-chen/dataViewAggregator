package cn.kane.web.view.aggregator.loader.compare;

import org.apache.commons.lang3.builder.EqualsBuilder;

import cn.kane.web.view.aggregator.pojo.model.Widget;

public class WidgetComparator implements ResourceComparator<Widget> {

	@Override
	public boolean isEquals(Widget res1, Widget res2) {
		return EqualsBuilder.reflectionEquals(res1, res2);
	}
}
