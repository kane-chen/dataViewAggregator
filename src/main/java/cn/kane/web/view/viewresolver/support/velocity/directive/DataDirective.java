package cn.kane.web.view.viewresolver.support.velocity.directive;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.DataReader;
import cn.kane.web.view.aggregator.util.DefinitionKeyUtils;
import cn.kane.web.view.viewresolver.support.velocity.ContainerBeanFactory;

public class DataDirective extends Directive {

	@Override
	public String getName() {
		return "data" ;
	}

	@Override
	public int getType() {
		return LINE ;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {
		//data-reader key
		String formattedDataReaderKey = (String)node.jjtGetChild(0).value(context) ;
		//put already
		if(null != context.get(formattedDataReaderKey)){
			return true ;
		}
		//getDate&put-in-context
		Object params = node.jjtGetChild(1).value(context) ;
		DefinitionKey key = DefinitionKeyUtils.parse(formattedDataReaderKey) ;
		DataReader dataReader = ContainerBeanFactory.getInstance().getDataReaderLoader().getResourceByKey(key) ;
		Object result = dataReader.getData(params) ;
		//assign
		if(null!=node.jjtGetChild(2)){
			String paramName = (String) node.jjtGetChild(2).value(context) ;
			context.put(paramName, result) ;
		}
		context.put(formattedDataReaderKey,result) ;
		return true ;
	}

}
