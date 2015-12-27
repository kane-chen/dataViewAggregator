package cn.kane.dynamicview;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cn.kane.dynamicview.product.PageRenderTest;
import cn.kane.dynamicview.product.TemplateRenderTest;
import cn.kane.dynamicview.product.WidgetRenderTest;

@RunWith(Suite.class)
@SuiteClasses({
	TemplateRenderTest.class
	,WidgetRenderTest.class
	,PageRenderTest.class
})
public class ProductRenderTestSuite {

}
