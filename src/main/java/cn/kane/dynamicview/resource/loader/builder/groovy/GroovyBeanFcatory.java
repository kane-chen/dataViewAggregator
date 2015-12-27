package cn.kane.dynamicview.resource.loader.builder.groovy;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import groovy.lang.GroovyClassLoader;
import cn.kane.dynamicview.resource.loader.builder.groovy.handler.AnnotationHandler;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class GroovyBeanFcatory {

	private static final Log LOG = LogFactory.getLog(GroovyBeanFcatory.class);

	/**
	 * groovy-class-loader
	 */
	private GroovyClassLoader groovyClassLoader;
	/**
	 * Map<className,instance>-singleton
	 */
	private Map<String, SoftReference<Object>> beanNameMapping = new ConcurrentHashMap<String, SoftReference<Object>>();
	/**
	 * Map<className,code's md5>
	 */
	private Map<String, String> nameCodeMapping = new ConcurrentHashMap<String, String>();
	
	/**
	 * Map<className,initClass-lock>
	 */
	private ConcurrentHashMap<String,Lock> nameLockMapping = new ConcurrentHashMap<String, Lock>() ; 

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
			//cached
			instance = this.getInstanceInCache(className, codeTxt, clazz) ;
			if(null!=instance){
				return instance ;
			}
			//class-parsed
			Class<T> targetClazz = (Class<T>) this.getClazz(className, codeTxt);
			//new-instance
			instance = targetClazz.newInstance();
			//annotation-handlers
			if (null != annoHandlers) {
				for (AnnotationHandler annoHandler : annoHandlers) {
					annoHandler.handle(instance);
				}

			}
		} catch (Exception e) {
			LOG.error(String.format("instance[className=%s] error", className), e);
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	private <T> T getInstanceInCache( String className,String codeTxt,Class<T> clazz){
		T instance = null;
		// code-md5
		String newCodeMd5 = Hashing.md5().hashString(codeTxt, Charsets.UTF_8).toString();
		String codeMd5 = nameCodeMapping.get(className);
		// not-change,return
		if (newCodeMd5.equals(codeMd5)) {
			SoftReference<Object> ref = beanNameMapping.get(className) ;
			if(null!=ref){
				instance = (T) ref.get() ;
			}
		}
		return instance ;
	}
	
	private Class<?> getClazz(String className, String codeTxt) {
		if (null == className || null == codeTxt) {
			return null;
		}
		Class<?> targetClazz = null;
		//mutex-lock
		Lock initLock = new ReentrantLock() ;
		Lock retLock = nameLockMapping.putIfAbsent(className,initLock ) ;
		if(retLock != null){
			initLock = retLock ;
		}
		// pasrseClass
		try {
			if(initLock.tryLock(1, TimeUnit.MILLISECONDS)){
				targetClazz = groovyClassLoader.parseClass(codeTxt);
			}
		} catch (Exception e) {
			LOG.error(String.format("class-parse[className=%s] error", className), e);
		}finally{
			try{
				initLock.unlock();
			}catch(Exception e){
			}
		}
		return targetClazz;
	}

	public void setAnnoHandlers(List<AnnotationHandler> annoHandlers) {
		this.annoHandlers = annoHandlers;
	}

}
