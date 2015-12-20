package cn.kane.web.view.integrate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.kane.web.view.integrate.service.RequirementJoinService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/resource/integrate/integrate-service-test.xml")
public class RequirementJoinServiceTest {

	@Autowired
	private RequirementJoinService requirementJoinService ;
	private String operator = "kane" ;
	private String requirementId = "111" ;
	
	@Before
	public void join(){
		requirementJoinService.join(operator, requirementId) ;
	}
	
	@Test
	public void query(){
		String queryed = requirementJoinService.query(operator) ;
		Assert.assertEquals(requirementId, queryed) ;
	}
}
