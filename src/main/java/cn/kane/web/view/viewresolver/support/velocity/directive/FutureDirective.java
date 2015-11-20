package cn.kane.web.view.viewresolver.support.velocity.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

public class FutureDirective extends Directive {
	
	private static final Log LOG = LogFactory.getLog(FutureDirective.class) ;
	public final static String LEFT_TIME_KEY = "leftMillis" ;
		
	@Override
	public String getName() {
		return "future" ;
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
		//no-future
		Object value = context.get(formattedDataReaderKey) ;
		if(null == value){
			return false ;
		}
		//response
		if(! (value instanceof Future)){//future-get-already
			return true ;
		}else{//future-get
			//left-timeout-millis
			Long leftMillis = this.getTotalTimeoutMillis(context) ;
			long timeoutMillis = this.getTimeoutMillis(leftMillis, formattedDataReaderKey) ;
			long startMillis = System.currentTimeMillis() ;
			try{
				//future-get
				Future<?> future = (Future<?>)value ;
				Object respValue = future.get(timeoutMillis, TimeUnit.MILLISECONDS) ;
				//assign
				if(null!=node.jjtGetChild(1)){
					String paramName = (String) node.jjtGetChild(1).value(context) ;
					context.put(paramName, respValue);
				}
				//put in context
				context.localPut(formattedDataReaderKey, respValue) ;
			}catch(Exception e){
				LOG.warn(String.format("future[%s]-get ERROR", formattedDataReaderKey),e);
			}finally{
				long costMillis = System.currentTimeMillis() - startMillis ;
				LOG.debug(String.format("%s cost %s", formattedDataReaderKey,costMillis));
				context.put(LEFT_TIME_KEY, leftMillis-costMillis);
			}
			return true ;
		}
	}

	private long getTotalTimeoutMillis(InternalContextAdapter context){
		Object leftTimeout = context.get(LEFT_TIME_KEY) ;
		long leftMillis = 800 ;
		if(null != leftTimeout && leftTimeout instanceof Long){
			leftMillis = (Long)leftTimeout ;
		}else{
			context.put(LEFT_TIME_KEY, leftMillis ) ;
		}
		return leftMillis ;
	}
	
	private long getTimeoutMillis(long totalTimeoutMillis,String formatedDataReaderKey){
		//customer-timeout(TODO)
		long maxSingelTimeoutMillis = 200 ;
		if(totalTimeoutMillis > maxSingelTimeoutMillis){
			return maxSingelTimeoutMillis ;
		}
		return totalTimeoutMillis ;
	}
	
}
