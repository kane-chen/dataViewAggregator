package cn.kane.web.view.integrate;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.integrate.service.ChangesManageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/resource/integrate/integrate-service-test.xml"
		,"classpath:/resource/integrate/definition-manager-test.xml"
})
public class ChangesManageServiceTest {

	@Autowired
	private ChangesManageService changesManageService ;
	private static String requirementId = "1111" ;
	private static DefinitionKey key ;
	
	@BeforeClass
	public static void setup(){
		key = new DefinitionKey() ;
		key.setType("type") ;
		key.setName("name") ;
		key.setVersion(requirementId) ;
	}
	
	@Before
	public void add(){
		changesManageService.add(requirementId, key) ;
	}
	
	@Test
	public void query(){
		List<DefinitionKey> changes = changesManageService.list(requirementId) ;
		Assert.assertTrue(changes.contains(key));
	}
	
}
