package cn.kane.dynamicview.product.render.view.velocity.directive;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.product.render.view.velocity.tools.ContainerBeanFactory;
import cn.kane.dynamicview.resource.entity.DataReader;
import cn.kane.dynamicview.util.DefinitionKeyUtils;

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
		Object result = null;
		//get-data
		if(null != context.get(formattedDataReaderKey)){//put already
			result = context.get(formattedDataReaderKey) ;
		}else{//getDate&put-in-context
			Object params = node.jjtGetChild(1).value(context) ;
			DefinitionKey key = DefinitionKeyUtils.parse(formattedDataReaderKey) ;
			DataReader dataReader = ContainerBeanFactory.getInstance().getDataReaderLoader().getResourceByKey(key) ;
			result = dataReader.getData(params) ;
		}
		//assign
		if(null!=node.jjtGetChild(2)){
			String paramName = (String) node.jjtGetChild(2).value(context) ;
			context.put(paramName, result) ;
		}
		context.put(formattedDataReaderKey,result) ;
		return true ;
	}

}
