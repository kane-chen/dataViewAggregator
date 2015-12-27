package cn.kane.dynamicview.resource.loader.builder.groovy.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.kane.dynamicview.resource.loader.builder.groovy.annotation.DubboConsumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;

public class DubboConsumerAnnoHandler extends
		AbstractFieldAnnotationHandler<DubboConsumer> {

	private static final String DUBBO_CLIENT_NAME = "dubboClient";
	private ApplicationConfig application;

	public void init() {
		application = new ApplicationConfig(DUBBO_CLIENT_NAME);
	}

	@Override
	protected Object getValueByAnnotation(DubboConsumer anno) {
		ReferenceConfig<?> reference = null ;
		//init expensive-cost
		if(anno.generic()){
			reference = new ReferenceConfig<GenericService>();
		}else{
			reference = new ReferenceConfig<Object>();
		}
		reference.setApplication(application);
		if (null != anno.interfaceClass() && anno.interfaceClass() != void.class) {
			reference.setInterface(anno.interfaceClass());
		}
		if (StringUtils.isNoneBlank(anno.interfaceName())) {
			reference.setInterface(anno.interfaceName()); // 弱类型接口名
		}
		if (StringUtils.isNoneBlank(anno.url())) {//direct-connect
			reference.setUrl(anno.url());
		} else {//registry
			List<RegistryConfig> registrys = new ArrayList<RegistryConfig>(1);
			RegistryConfig registry = new RegistryConfig(anno.registerAddress());
			registrys.add(registry);
			reference.setRegistries(registrys);
		}
		if(StringUtils.isNoneBlank(anno.client())){
			reference.setClient(anno.client());
		}
		if(StringUtils.isNoneBlank(anno.protocol())){
			reference.setProtocol(anno.protocol());
		}
		if(StringUtils.isNoneBlank(anno.version())){
			reference.setVersion(anno.version());
		}
		if(StringUtils.isNoneBlank(anno.group())){
			reference.setGroup(anno.group());
		}
		reference.setGeneric(anno.generic());
		if (anno.timeout() > 0) {
			reference.setTimeout(anno.timeout());
		}
		return reference.get();
	}

	@Override
	protected Class<DubboConsumer> getSpecialClass() {
		return DubboConsumer.class;
	}

}
