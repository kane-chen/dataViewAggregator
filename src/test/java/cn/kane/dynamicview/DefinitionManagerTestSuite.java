package cn.kane.dynamicview;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.kane.dynamicview.definition.PageDefinitionManagerTest;
import cn.kane.dynamicview.definition.TemplateDefinitionManagerTest;
import cn.kane.dynamicview.definition.WidgetDefinitionManagerTest;

@RunWith(Suite.class)
@SuiteClasses({
	TemplateDefinitionManagerTest.class
	,WidgetDefinitionManagerTest.class
	,PageDefinitionManagerTest.class
})
public class DefinitionManagerTestSuite {

}
