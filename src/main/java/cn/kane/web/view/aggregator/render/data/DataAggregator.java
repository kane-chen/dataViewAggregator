package cn.kane.web.view.aggregator.render.data;

import java.util.List;
import java.util.Map;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;

public interface DataAggregator {

	Map<String, Object> aggregate(List<DefinitionKey> dataReaderKeys, Map<String, Object> param);

}
