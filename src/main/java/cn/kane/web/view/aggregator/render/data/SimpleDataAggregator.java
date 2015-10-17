package cn.kane.web.view.aggregator.render.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.DataReader;
import cn.kane.web.view.aggregator.service.loader.DataReaderLoader;

public class SimpleDataAggregator implements DataAggregator {

	private DataReaderLoader dataReaderLoader;

	@Override
	public Map<String, Object> aggregate(List<DefinitionKey> dataReaderKeys, Map<String, Object> param) {
		dataReaderKeys = this.sortDataReaders(dataReaderKeys);
		Map<String, Object> context = new HashMap<String, Object>();
		if (null != param) {
			context.putAll(param);
		}
		for (DefinitionKey key : dataReaderKeys) {
			if (null == key) {
				continue;
			}
			DataReader dataReader = dataReaderLoader.getResourceByKey(key);
			if (null == dataReader) {
				continue;
			}
			Object result = dataReader.getData(param);
			if (null != result) {
				context.put(key.getName(), result);
			}
		}
		return context;
	}

	protected List<DefinitionKey> sortDataReaders(List<DefinitionKey> dataReaderKeys) {
		// TODO dependency list
		return dataReaderKeys;
	}

	public void setDataReaderLoader(DataReaderLoader dataReaderLoader) {
		this.dataReaderLoader = dataReaderLoader;
	}

}
