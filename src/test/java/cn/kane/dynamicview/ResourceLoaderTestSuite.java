package cn.kane.dynamicview;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.kane.dynamicview.resource.GroovyDataReaderTest;
import cn.kane.dynamicview.resource.PageLoaderTest;
import cn.kane.dynamicview.resource.WidgetLoaderTest;

@RunWith(Suite.class)
@SuiteClasses({
	GroovyDataReaderTest.class
	,WidgetLoaderTest.class
	,PageLoaderTest.class
})
public class ResourceLoaderTestSuite {

}
