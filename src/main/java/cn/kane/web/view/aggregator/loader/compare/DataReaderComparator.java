package cn.kane.web.view.aggregator.loader.compare;

import org.apache.commons.lang3.builder.EqualsBuilder;

import cn.kane.web.view.aggregator.pojo.model.DataReader;

public class DataReaderComparator implements ResourceComparator<DataReader> {

    @Override
    public boolean isEquals(DataReader res1, DataReader res2) {
        return EqualsBuilder.reflectionEquals(res1, res2, "dataReadService") ;
    }

}
