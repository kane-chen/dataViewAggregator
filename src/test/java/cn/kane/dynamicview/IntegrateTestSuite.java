package cn.kane.dynamicview;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.kane.dynamicview.integrate.ChangesManageServiceTest;
import cn.kane.dynamicview.integrate.ChangesPublishServiceTest;
import cn.kane.dynamicview.integrate.RequirementJoinServiceTest;
import cn.kane.dynamicview.integrate.RequirementManageServiceTest;
import cn.kane.dynamicview.integrate.VersionControlInterceptorTest;

@RunWith(Suite.class)
@SuiteClasses({
	RequirementManageServiceTest.class
	,RequirementJoinServiceTest.class
	,ChangesManageServiceTest.class
	,VersionControlInterceptorTest.class
	,ChangesPublishServiceTest.class
})
public class IntegrateTestSuite {

}
