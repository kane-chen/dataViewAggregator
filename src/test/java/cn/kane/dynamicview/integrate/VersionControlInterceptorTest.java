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
import cn.kane.dynamicview.integrate.service.RequirementJoinService;
import cn.kane.dynamicview.integrate.service.RequirementManageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/integrate-manager-test.xml"
		,"classpath:/definition-manager-test.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VersionControlInterceptorTest {
	
	@Autowired
	private RequirementManageService requirementManageService ;
	@Autowired
	private RequirementJoinService requirementJoinService ;
	@Autowired
	private ResourceDefinitionManager<AbstractDefinition> resourceDefinitionManagerFacade ;
	@Autowired
	private ChangesManageService changesManageService ;
	private static String requirementId  ;
	private static String operator = "kane" ;  
	private static DefinitionKey key ;
	
	@BeforeClass
	public static void setup(){
		key = new DefinitionKey() ;
		key.setType("string") ;
		key.setName("name") ;
		key.setVersion(requirementId) ;
	}
	
	@Test
	public void test001_addRequiremnt(){
		Requirement req = new Requirement() ;
		req.setName("requirementName");
		req.setDescription("description");
		req.setOperator(operator) ;
		requirementId = requirementManageService.add(req) ;
	}
	
	@Test
	public void test002_join(){
		requirementJoinService.join(operator, requirementId) ;
	}
	
	@Test
	public void test003_editDefinition(){
		TemplateDefinition def = new TemplateDefinition() ;
		def.setKey(key) ;
		def.setOperator(operator) ;
		def.setDescription("def-description") ;
		def.setContent("hello") ;
		resourceDefinitionManagerFacade.add(def) ;
	}
	
	@Test
	public void test004_query(){
		List<DefinitionKey> changes = changesManageService.list(requirementId) ;
		Assert.assertTrue(changes.contains(key));
	}
	
}
