package cn.kane.web.view.aggregator.loader.compare;

import org.apache.commons.lang3.builder.EqualsBuilder;

import cn.kane.web.view.aggregator.pojo.model.Page;

public class PageComparator implements ResourceComparator<Page> {

    @Override
    public boolean isEquals(Page res1, Page res2) {
        return EqualsBuilder.reflectionEquals(res1, res2);
    }
    

}
