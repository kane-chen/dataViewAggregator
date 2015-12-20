package cn.kane.web.view.integrate;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.kane.web.view.aggregator.pojo.definition.AbstractDefinition;
import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.definition.StringResourceDefinition;
import cn.kane.web.view.aggregator.service.manager.ResourceDefinitionManager;
import cn.kane.web.view.integrate.pojo.Requirement;
import cn.kane.web.view.integrate.service.ChangesManageService;
import cn.kane.web.view.integrate.service.ChangesPublishService;
import cn.kane.web.view.integrate.service.ChangesPushService;
import cn.kane.web.view.integrate.service.RequirementJoinService;
import cn.kane.web.view.integrate.service.RequirementManageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/resource/integrate/publish-service-test.xml"
		,"classpath:/resource/integrate/integrate-service-aop-test.xml"
		,"classpath:/resource/integrate/definition-manager-test.xml"
		,"classpath:/resource/integrate/definition-manager-ol-test.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChangesPublishServiceTest {
	/* daily */
	@Autowired
	private RequirementManageService requirementManageService ;
	@Autowired
	private RequirementJoinService requirementJoinService ;
	@Autowired
	private ResourceDefinitionManager<AbstractDefinition> resourceDefinitionManagerFacade ;
	@Autowired
	private ChangesManageService changesManageService ;
	/* publish */
	@Autowired
	private ChangesPushService changesPushService ; 
	@Autowired
	private ChangesPublishService changesPublishService ;
	/* ol-mamanger */
	@Autowired
	private RequirementManageService olRequirementManageService ;
	@Autowired
	private ChangesManageService olChangesManageService ;
	@Autowired
	private ResourceDefinitionManager<AbstractDefinition> olResourceDefinitionManagerFacade ;
	
	private static String requirementId;
	private static final String operator = "kane";
	private static DefinitionKey key1 ;
	private static DefinitionKey key2 ;
	
	@BeforeClass
	public static void prepare(){
		key1 = new DefinitionKey() ;
		key1.setType("css");
		key1.setName("simplecss");
		key2 = new DefinitionKey() ;
		key2.setType("viewTemplate");
		key2.setName("template1");
	}
	
	@Test
	public void test001_requirement(){
		Requirement req = new Requirement() ;
		req.setName("requirement4push");
		req.setOperator(operator);
		requirementId = requirementManageService.add(req) ;
		Assert.assertNotNull(requirementId);
		requirementJoinService.join(operator, requirementId) ;
		Assert.assertEquals(requirementId, requirementJoinService.query(operator)) ;
	}
	
	@Test
	public void test002_changes(){
		StringResourceDefinition def1 = new StringResourceDefinition() ;
		def1.setKey(key1) ;
		def1.setOperator(operator);
		def1.setContent(".h1{colore:blue}");
		resourceDefinitionManagerFacade.add(def1) ;
		StringResourceDefinition def2 = new StringResourceDefinition() ;
		def2.setKey(key2) ;
		def2.setOperator(operator);
		def2.setContent("<h1>hello</h1>");
		resourceDefinitionManagerFacade.add(def2) ;
		//changes-assert
		List<DefinitionKey> keys = changesManageService.list(requirementId) ;
		Assert.assertTrue(keys.contains(key1));
		Assert.assertTrue(keys.contains(key2));
	}
	
	@Test
	public void test003_push(){
		changesPushService.push(requirementId) ;
		//requirement
		Requirement olReq = olRequirementManageService.get(requirementId) ;
		Assert.assertEquals("pushed", olReq.getStatus()) ;
		//changes
		List<DefinitionKey> olKeys = olChangesManageService.list(requirementId) ;
		Assert.assertTrue(olKeys.contains(key1));
		Assert.assertTrue(olKeys.contains(key2));
		//synchronize
		AbstractDefinition def1 = olResourceDefinitionManagerFacade.get(key1) ;
		Assert.assertNotNull(def1);
		AbstractDefinition def2 = olResourceDefinitionManagerFacade.get(key2) ;
		Assert.assertNotNull(def2);
	}
	
	@Test
	public void test004_publish(){
		changesPublishService.publish(requirementId) ;
		//requirement
		Requirement olReq = olRequirementManageService.get(requirementId) ;
		Assert.assertEquals("published", olReq.getStatus()) ;
		//trunk
		key1.setVersion("trunk") ;
		AbstractDefinition def1 = olResourceDefinitionManagerFacade.get(key1) ;
		Assert.assertNotNull(def1);
		key2.setVersion("trunk") ;
		AbstractDefinition def2 = olResourceDefinitionManagerFacade.get(key2) ;
		Assert.assertNotNull(def2);
	}
}
