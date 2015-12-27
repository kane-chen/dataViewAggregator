package cn.kane.dynamicview.product.render.view.velocity.tools;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.kane.dynamicview.definition.service.TemplateDefinitionManager;
import cn.kane.dynamicview.resource.service.DataReaderLoader;
import cn.kane.dynamicview.resource.service.PageLoader;
import cn.kane.dynamicview.resource.service.WidgetLoader;

public class ContainerBeanFactory implements ApplicationContextAware{

	@Autowired
	private TemplateDefinitionManager templateDefinitionManager;
	@Autowired
	private DataReaderLoader dataReaderLoader ;
	@Autowired
	private WidgetLoader widgetLoader ;
	@Autowired
	private PageLoader pageLoader ;
	
	private static volatile AtomicBoolean isInstanced = new AtomicBoolean(false) ;
	private static ContainerBeanFactory instance ;

	private ContainerBeanFactory(){
		if(isInstanced.compareAndSet(false, true)){
			//nothing
		}else{
			throw new UnsupportedOperationException("cannot instance,please use getInstance");
		}
	}
	
	public static ContainerBeanFactory getInstance(){
		if(!isInstanced.get()){
			instance = new ContainerBeanFactory() ;
		}
		return instance ;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		instance.templateDefinitionManager = applicationContext.getBean("templateDefinitionManager", TemplateDefinitionManager.class) ;
		instance.dataReaderLoader = applicationContext.getBean("dataReaderLoader", DataReaderLoader.class);
		instance.widgetLoader = applicationContext.getBean("widgetLoader", WidgetLoader.class);
		instance.pageLoader = applicationContext.getBean("pageLoader", PageLoader.class);
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

	public TemplateDefinitionManager getTemplateDefinitionManager() {
		return templateDefinitionManager;
	}

}
