package cn.kane.web.view.aggregator.dubbo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericService;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.service.manager.StringResourceDefinitionManager;
import cn.kane.web.view.viewresolver.support.velocity.service.VelocityExtConfigService;

public class StringResourceManagerTest extends TestCase {

	private StringResourceDefinitionManager stringResourceManager;
	private DefinitionKey key;
	private VelocityExtConfigService velocityExtConfigService;

	@SuppressWarnings("resource")
	public void setUp() throws IOException {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"/resource/dubbo/dubbo-client-test.xml");
		stringResourceManager = (StringResourceDefinitionManager) appContext
				.getBean("stringResourceDefinitionManager");
		velocityExtConfigService = (VelocityExtConfigService) appContext
				.getBean("velocityExtConfigService");
		key = this.buildKey("viewTemplate", "simple", "1");
	}

	public void testUpdateMacro() throws IOException {
		String content = this.getCodeInFile("/render/macroNew.vm");
		velocityExtConfigService.upateMacro("sayhi", content,
				"/render/macro.vm", new String[] { "sayhi", "name" });
	}

	public void testAsyncCall() throws InterruptedException,
			ExecutionException, TimeoutException {
		Future<Object> future = RpcContext.getContext().asyncCall(
				new Callable<Object>() {
					public Object call() throws Exception {
						return velocityExtConfigService.costexp(800L);
					}
				});
		System.out.println(future.get(1500, TimeUnit.MILLISECONDS));
	}

	public void testConfigAsyncCall() {
		ApplicationConfig application = new ApplicationConfig();
		application.setName("dubbo-client");
		ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
		;// 该实例很重量，里面封装了所有与注册中心及服务提供方连接，请缓存
		reference.setApplication(application);
		// reference.setInterface(cn.kane.web.view.viewresolver.support.velocity.service.VelocityExtConfigService.class);
		reference
				.setInterface("cn.kane.web.view.viewresolver.support.velocity.service.VelocityExtConfigService"); // 弱类型接口名
		List<RegistryConfig> regs = new ArrayList<RegistryConfig>(1);
		RegistryConfig reg = new RegistryConfig(
				"zookeeper://127.0.0.1:2181?backup=127.0.0.1:2181,127.0.0.1:2181");
		regs.add(reg);
		reference.setRegistries(regs);
		// reference.setUrl("dubbo://127.0.0.1:8085/cn.kane.web.view.viewresolver.support.velocity.service.VelocityExtConfigService");
		reference.setVersion("1.0.0");
		reference.setGeneric(true);
		reference.setTimeout(1000);
		GenericService genericService = reference.get();
		// 基本类型以及Date,List,Map等不需要转换，直接调用 ;用Map表示POJO参数，如果返回值为POJO也将自动转成Map
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", "xxx");
		key.put("type", "yyy");
		key.put("version", "zzz");
		// 如果返回POJO将自动转成Map
		// Object result = genericService.$invoke("costexp", new
		// String[]{"cn.kane.web.view.aggregator.pojo.definition.DefinitionKey"},
		// new Object[]{key});
		Object result = genericService.$invoke("costexp",
				new String[] { "java.lang.Long" },
				new Object[] { new Long(500) });
		Assert.assertNotNull(result);
	}

	public void testUpdated() throws IOException {
		String newContent = this.getCodeInFile("/templates/simpleNew.vt");
		StringResourceDefinition dataTemplate = stringResourceManager.get(key);
		dataTemplate.setContent(newContent);
		boolean result = stringResourceManager.edit(dataTemplate);
		Assert.assertTrue(result);
	}

	public void testGet() {
		StringResourceDefinition dataTemplate = stringResourceManager.get(key);
		Assert.assertEquals(key, dataTemplate.getKey());
	}

	public void testUpdateDataService() throws IOException {
		DefinitionKey dataReaderKey = this.buildKey("dataReadService","future", "1");
		String content = this.getCodeInFile("/templates/scripts/futureDataService.dat");
		StringResourceDefinition dataReader = stringResourceManager.get(dataReaderKey);
		dataReader.setContent(content);
		boolean result = stringResourceManager.edit(dataReader);
		Assert.assertTrue(result);
	}
	
	public void testDataTemplate() throws IOException {
		DefinitionKey dataReaderKey = this.buildKey("dataTemplate","simple", "1");
		String content = this.getCodeInFile("/templates/simple.dt");
		StringResourceDefinition dataTemplate = stringResourceManager.get(dataReaderKey);
		dataTemplate.setContent(content);
		boolean result = stringResourceManager.edit(dataTemplate);
		Assert.assertTrue(result);
	}
	
	public void testViewTemplate() throws IOException {
		DefinitionKey dataReaderKey = this.buildKey("viewTemplate","simple", "1");
		String content = this.getCodeInFile("/templates/simple.vt");
		StringResourceDefinition dataTemplate = stringResourceManager.get(dataReaderKey);
		dataTemplate.setContent(content);
		boolean result = stringResourceManager.edit(dataTemplate);
		Assert.assertTrue(result);
	}

	private DefinitionKey buildKey(String type, String name, String version) {
		DefinitionKey key = new DefinitionKey();
		key.setType(type);
		key.setName(name);
		key.setVersion(version);
		return key;
	}

	private String getCodeInFile(String file) throws IOException {
		String result = null;
		InputStream in = this.getClass().getResourceAsStream(file);
		byte[] bytes = new byte[in.available()];
		in.read(bytes);
		result = new String(bytes);
		return result;
	}
}
