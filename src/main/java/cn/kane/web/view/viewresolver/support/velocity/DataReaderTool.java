package cn.kane.web.view.viewresolver.support.velocity;

import java.util.Map;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.DataReader;
import cn.kane.web.view.aggregator.util.DefinitionKeyUtils;

public class DataReaderTool {

	public static Object getData(String formatedKey,Map<String,Object> params){
		DefinitionKey key = DefinitionKeyUtils.parse(formatedKey) ;
		if(null == key || !"dataReadService".equals(key.getType())){
			return null ;
		}
		DataReader dataReader = ContainerBeanFactory.getInstance().getDataReaderLoader().getResourceByKey(key) ;
		if(null==dataReader){
			return null ;
		}
		return dataReader.getData(params) ;
	}
	
}
