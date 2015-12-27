package cn.kane.dynamicview;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	DefinitionManagerTestSuite.class
	,ResourceLoaderTestSuite.class
	,ProductRenderTestSuite.class
	,IntegrateTestSuite.class
})
public class AllTestSuite {

}
