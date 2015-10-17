package cn.kane.web.view.aggregator.util.groovy;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import groovy.lang.GroovyClassLoader;
import cn.kane.web.view.aggregator.util.groovy.annotation.AnnotationHandler;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class GroovyBeanFcatory {

	private static final Log LOG = LogFactory.getLog(GroovyBeanFcatory.class);

	/**
	 * groovy-class-loader
	 */
	private GroovyClassLoader groovyClassLoader;
	/**
	 * Map<className,Class>
	 */
	private Map<String, SoftReference<Class<?>>> classNameMapping = new ConcurrentHashMap<String, SoftReference<Class<?>>>();
	/**
	 * Map<className,code's md5>
	 */
	private Map<String, String> classNameCodeMapping = new ConcurrentHashMap<String, String>();

	/**
	 * annotation-handler[Config]
	 */
	private List<AnnotationHandler> annoHandlers;

	public GroovyBeanFcatory() {
		this.groovyClassLoader = new GroovyClassLoader(this.getClass().getClassLoader());
	}

	public GroovyBeanFcatory(ClassLoader parent) {
		this.groovyClassLoader = new GroovyClassLoader(parent);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBeanIntance(String className, String codeTxt, Class<T> clazz) {
		T instance = null;
		try {
			Class<T> targetClazz = (Class<T>) this.getClazz(className, codeTxt);
			instance = targetClazz.newInstance();
			if (null != annoHandlers) {
				for (AnnotationHandler annoHandler : annoHandlers) {
					annoHandler.handle(instance);
				}

			}
		} catch (Exception e) {
			LOG.error(String.format("instance[className=%s] error", className), e);
			e.printStackTrace();
		}
		return instance;
	}

	private Class<?> getClazz(String className, String codeTxt) {
		if (null == className || null == codeTxt) {
			return null;
		}
		// code-md5
		String newCodeMd5 = Hashing.md5().hashString(codeTxt, Charsets.UTF_8).toString();
		String codeMd5 = classNameCodeMapping.get(className);
		// not-change,return
		Class<?> targetClazz = null;
		if (newCodeMd5.equals(codeMd5)) {
			SoftReference<Class<?>> softRef = classNameMapping.get(className);
			if (null != softRef) {
				targetClazz = softRef.get();
			}
		}
		if (null != targetClazz) {
			return targetClazz;
		}
		// rePasrseClass
		try {
			targetClazz = groovyClassLoader.parseClass(codeTxt);
			// cache mapping
			if (null != targetClazz) {
				classNameCodeMapping.put(className, newCodeMd5);
				SoftReference<Class<?>> softRef = new SoftReference<Class<?>>(targetClazz);
				classNameMapping.put(className, softRef);
			}
		} catch (Exception e) {
			LOG.error(String.format("class-parse[className=%s] error", className), e);
		}
		return targetClazz;
	}

	public void setAnnoHandlers(List<AnnotationHandler> annoHandlers) {
		this.annoHandlers = annoHandlers;
	}

}
