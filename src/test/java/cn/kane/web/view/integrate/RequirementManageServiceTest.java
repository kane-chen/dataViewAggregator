package cn.kane.web.view.integrate;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.kane.web.view.integrate.pojo.Requirement;
import cn.kane.web.view.integrate.service.RequirementManageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/resource/integrate/integrate-service-test.xml")
public class RequirementManageServiceTest {

	@Autowired
	private RequirementManageService requirementManageService ;
	private String name4test = "testname" ;
	private String descripte4test = "testdescripte" ;
	private String operator4test = "testoperator" ;
	private static String addedId ;
	
	@Before
	public void add(){
		Requirement req = new Requirement() ;
		req.setName(name4test) ;
		req.setDescription(descripte4test) ;
		req.setOperator(operator4test) ;
		addedId = requirementManageService.add(req) ;
	}
	
	@Test
	public void query(){
		Requirement queryBean = requirementManageService.get(addedId) ;
		Assert.assertEquals(name4test, queryBean.getName()) ;
		Assert.assertEquals(operator4test, queryBean.getOperator()) ;
		Assert.assertEquals(descripte4test, queryBean.getDescription()) ;
	}
	
	@Test
	public void edit(){
		//test-param
		String newname = "newname" ;
		String operator = "newoperator" ;
		//query&update
		Requirement queryBean = requirementManageService.get(addedId) ;
		queryBean.setName(newname) ;
		queryBean.setOperator(operator) ;
		requirementManageService.edit(queryBean) ;
		//query&check
		queryBean = requirementManageService.get(addedId) ;
		Assert.assertEquals(newname, queryBean.getName()) ;
		Assert.assertEquals(operator, queryBean.getOperator()) ;
		Assert.assertEquals(descripte4test, queryBean.getDescription()) ;
	}
	
	@After
	public void remove(){
		requirementManageService.remove(addedId) ;
	}
}
