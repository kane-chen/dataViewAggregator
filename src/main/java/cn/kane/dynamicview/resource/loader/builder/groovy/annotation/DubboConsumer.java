package cn.kane.dynamicview.resource.loader.builder.groovy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * refer to com.alibaba.dubbo.config.ReferenceConfig
 * @author kane.ch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface DubboConsumer {

	String interfaceName() default "";
	
	Class<?> interfaceClass() default void.class;
	
	String client() default "";
	
	/**
	 * direct-url
	 * such as: dubbo://127.0.0.1:8085/cn.kane.web.view.viewresolver.support.velocity.service.VelocityExtConfigService
	 * @return
	 */
	String url() default "";
	
	/**
	 * register
	 * such as : zookeeper://127.0.0.1:2181?backup=127.0.0.1:2181,127.0.0.1:2181
	 */
	String registerAddress() default "";
	
	/**
	 * choose: dubbo,hessian..
	 * @return
	 */
	String protocol() default "";
	
	String version() ;
	
	String group() default "";
	
	boolean generic() default false ;
	
	/**
	 * 远程调用超时时间(毫秒)
	 * @return
	 */
	int timeout() default 0 ;
	
	/**
	 * 重试次数
	 * @return
	 */
	int retries() default 0 ;
	
	/**
	 * 调用并发数
	 * @return
	 */
	int actives() default 0 ;
	
	/**
	 * 0-共享连接，n-独享的连接数
	 * @return
	 */
	int connects() default 0 ;
	
}
