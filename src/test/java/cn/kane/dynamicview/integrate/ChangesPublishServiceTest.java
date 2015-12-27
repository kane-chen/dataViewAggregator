package cn.kane.dynamicview.integrate;

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

import cn.kane.dynamicview.definition.entity.AbstractDefinition;
import cn.kane.dynamicview.definition.entity.DefinitionKey;
import cn.kane.dynamicview.definition.entity.TemplateDefinition;
import cn.kane.dynamicview.definition.service.ResourceDefinitionManager;
import cn.kane.dynamicview.integrate.entity.Requirement;
import cn.kane.dynamicview.integrate.service.ChangesManageService;
import cn.kane.dynamicview.integrate.service.IntegrateStatus;
import cn.kane.dynamicview.integrate.service.RequirementJoinService;
import cn.kane.dynamicview.integrate.service.RequirementManageService;
import cn.kane.dynamicview.integrate.service.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/integrate-publish-test.xml"
		,"classpath:/integrate-manager-test.xml"
		,"classpath:/definition-manager-test.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChangesPublishServiceTest {
	@Autowired
	private RequirementManageService requirementManageService ;
	@Autowired
	private RequirementJoinService requirementJoinService ;
	@Autowired
	private ChangesManageService changesManageService ;
	@Autowired
	private ResourceDefinitionManager<AbstractDefinition> resourceDefinitionManagerFacade ;
	@Autowired
	private State stateFacade ;
	
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
		TemplateDefinition def1 = new TemplateDefinition() ;
		def1.setKey(key1) ;
		def1.setOperator(operator);
		def1.setContent(".h1{colore:blue}");
		resourceDefinitionManagerFacade.add(def1) ;
		TemplateDefinition def2 = new TemplateDefinition() ;
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
	public void test003_commited(){
		//commit
		stateFacade.action(requirementId) ;
		//check
		Requirement commitedReq = requirementManageService.get(requirementId) ;
		Assert.assertEquals(IntegrateStatus.COMMITED.status(), commitedReq.getStatus()) ;
	}
	
	@Test
	public void test004_publish(){
		//publish
		stateFacade.action(requirementId) ;
		Requirement publishedReq = requirementManageService.get(requirementId) ;
		Assert.assertEquals(IntegrateStatus.PUBLISHED.status(), publishedReq.getStatus()) ;
		//trunk
		DefinitionKey trunkKey1 = this.clone(key1) ;
		trunkKey1.setVersion(ChangesManageService.TRUNK_VERSION) ;
		AbstractDefinition def1 = resourceDefinitionManagerFacade.get(trunkKey1) ;
		Assert.assertNotNull(def1);
		DefinitionKey trunkKey2 = this.clone(key1) ;
		trunkKey2.setVersion(ChangesManageService.TRUNK_VERSION) ;
		AbstractDefinition def2 = resourceDefinitionManagerFacade.get(key2) ;
		Assert.assertNotNull(def2);
	}
	
	@Test
	public void test005_rollback(){
		//rollback
		stateFacade.rollback(requirementId) ;
		Requirement rollbackedReq = requirementManageService.get(requirementId) ;
		Assert.assertEquals(IntegrateStatus.ROLLBACKED.status(), rollbackedReq.getStatus()) ;
		DefinitionKey trunkKey1 = this.clone(key1) ;
		trunkKey1.setVersion(ChangesManageService.TRUNK_VERSION) ;
		AbstractDefinition def1 = resourceDefinitionManagerFacade.get(trunkKey1) ;
		Assert.assertNull(def1);
	}
	
	@Test
	public void test006_disabled(){
		
	}
	
	private DefinitionKey clone(DefinitionKey key){
		DefinitionKey clone = new DefinitionKey() ;
		clone.setType(key.getType());
		clone.setName(key.getName());
		clone.setVersion(key.getVersion());
		return clone ;
	}
}