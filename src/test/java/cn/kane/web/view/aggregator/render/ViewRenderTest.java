package cn.kane.web.view.aggregator.render;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.pojo.model.Page;
import cn.kane.web.view.aggregator.pojo.model.ViewModel;
import cn.kane.web.view.aggregator.pojo.model.Widget;
import cn.kane.web.view.aggregator.render.view.render.TemplateRender;
import cn.kane.web.view.aggregator.service.render.ViewRender;
import cn.kane.web.view.aggregator.util.DefinitionKeyUtils;
import junit.framework.TestCase;

public class ViewRenderTest extends TestCase {

    private TemplateRender templateRender ;
    private ViewRender<Widget> widgetViewRender ;
    private ViewRender<Page> pageViewRender ;

    @SuppressWarnings({ "resource", "unchecked" })
    public void setUp() throws IOException{
        //init
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/resource/render/view-render-test.xml");
        templateRender = (TemplateRender)appContext.getBean("templateRender");
        widgetViewRender = (ViewRender<Widget>)appContext.getBean("widgetViewRender");
        pageViewRender = (ViewRender<Page>)appContext.getBean("pageViewRender");
    }
    
    public void testRender(){
        DefinitionKey key = this.buildKey("css", "cssName", "1") ;
        String result = templateRender.render(key, null) ;
        System.out.println(result);
    }
    
    public void testRenderInContext(){
        DefinitionKey key = this.buildKey("viewTemplate", "viewTemplateName", "1") ;
        Map<String,Object> context = new HashMap<String,Object>() ;
        context.put("new-value", "aaaaaaaaaaa");
        String result = templateRender.render(key, context) ;
        System.out.println(result);
    }
    
    public void testMerge(){
        DefinitionKey key = this.buildKey("viewTemplate", "viewTemplateName", "2") ;
        Map<String,Object> context = new HashMap<String,Object>() ;
        context.put("view_value", "aaaaaaaaaaa");
        DefinitionKey cssKey = this.buildKey("css", "cssName", "1") ;
        context.put("css_define", DefinitionKeyUtils.format(cssKey));
        DefinitionKey jsKey = this.buildKey("js", "jsName", "1") ;
        context.put("js_define", DefinitionKeyUtils.format(jsKey));
        String result = templateRender.render(key, context) ;
        System.out.println(result);
    }
    
    public void testWidgetViewRender(){
        DefinitionKey key = this.buildKey("widget", "widgetName", "1") ;
        ViewModel widgetView = widgetViewRender.reander(key, null) ;
        System.out.println(widgetView);
    }
    
    public void testPageViewRender(){
        DefinitionKey key = this.buildKey("page", "pageName", "1") ;
        ViewModel pageView = pageViewRender.reander(key, null) ;
        System.out.println(pageView);
    }
    
    private DefinitionKey buildKey(String type,String name,String version){
        DefinitionKey key = new DefinitionKey() ;
        key.setType(type);
        key.setName(name);
        key.setVersion(version);
        return key ;
    } 
    
}
