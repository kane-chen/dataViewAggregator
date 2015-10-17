package cn.kane.web.view.aggregator.util.groovy.annotation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextBeanAnnoHandler extends AbstractFieldAnnotationHandler<ContextBean>
		implements ApplicationContextAware {

	private ApplicationContext appContext;

	@Override
	protected Object getValueByAnnotation(ContextBean anno) {
		if (null == anno || null == appContext) {
			return null;
		}
		String beanName = anno.beanName();
		Object value = appContext.getBean(beanName);
		return value;
	}

	@Override
	protected Class<ContextBean> getSpecialClass() {
		return ContextBean.class;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

}
