package cn.kane.web.view.viewresolver.support.velocity;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.kane.web.view.aggregator.service.loader.DataReaderLoader;
import cn.kane.web.view.aggregator.service.loader.PageLoader;
import cn.kane.web.view.aggregator.service.loader.WidgetLoader;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;

public class ContainerBeanFactory implements ApplicationContextAware{

	private StringResourceDefinitionManager stringResourceDefinitionManager;
	private DataReaderLoader dataReaderLoader ;
	private WidgetLoader widgetLoader ;
	private PageLoader pageLoader ;
	
	private static volatile AtomicBoolean isInstanced = new AtomicBoolean(false) ;
	private static ContainerBeanFactory instance ;

	public ContainerBeanFactory(){
		if(isInstanced.compareAndSet(false, true)){
			instance = this ;
		}else{
			throw new UnsupportedOperationException("cannot instance,please use getInstance");
		}
	}
	
	public static ContainerBeanFactory getInstance(){
//		if(!isInstanced.get()){
//			throw new UnsupportedOperationException("not instance now,wait instanced");
//		}
		return instance ;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		instance.stringResourceDefinitionManager = applicationContext.getBean("stringResourceDefinitionManager", StringResourceDefinitionManager.class) ;
		instance.dataReaderLoader = applicationContext.getBean("dataReaderLoader", DataReaderLoader.class);
		instance.widgetLoader = applicationContext.getBean("widgetLoader", WidgetLoader.class);
		instance.pageLoader = applicationContext.getBean("pageLoader", PageLoader.class);
	}

	public StringResourceDefinitionManager getStringResourceDefinitionManager() {
		return stringResourceDefinitionManager;
	}

	public DataReaderLoader getDataReaderLoader() {
		return dataReaderLoader;
	}

	public WidgetLoader getWidgetLoader() {
		return widgetLoader;
	}

	public PageLoader getPageLoader() {
		return pageLoader;
	}

}
