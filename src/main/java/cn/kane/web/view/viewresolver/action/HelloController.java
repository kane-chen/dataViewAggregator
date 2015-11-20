package cn.kane.web.view.viewresolver.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.kane.web.view.aggregator.pojo.definition.DefinitionKey;
import cn.kane.web.view.aggregator.util.DefinitionKeyUtils;

@Controller
@RequestMapping("/hello")
public class HelloController {

	@RequestMapping(value="sayhi",method=RequestMethod.GET)
	public ModelAndView hello(){
		ModelAndView view = new ModelAndView() ;
		view.addObject("user", "kane") ;
		view.setViewName("hello");
		return view ;
	}
	
	@RequestMapping(value="dynamic",method=RequestMethod.GET)
	public ModelAndView dynamic(){
		ModelAndView view = new ModelAndView() ;
		view.addObject("user", "kane") ;
		DefinitionKey key = new DefinitionKey() ;
		key.setType("viewTemplate");
		key.setName("sayhi");
		key.setVersion("1");
		view.setViewName(DefinitionKeyUtils.format(key));
		return view ;
	}
	
	@RequestMapping(value="template_driver",method=RequestMethod.GET)
	public ModelAndView template_driver(@RequestParam("userId")String userId){
		Map<String,Object> params = new HashMap<String, Object>(1) ;
		params.put("userId", userId) ;
		ModelAndView view = new ModelAndView() ;
		view.addObject("params", params) ;
		DefinitionKey key = new DefinitionKey() ;
		key.setType("widget");
		key.setName("simple");
		key.setVersion("1");
		view.setViewName(DefinitionKeyUtils.format(key));
		return view ;
	}
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public ModelAndView page(@RequestParam("userId")String userId){
		Map<String,Object> params = new HashMap<String, Object>(1) ;
		params.put("userId", userId) ;
		ModelAndView view = new ModelAndView() ;
		view.addObject("params", params) ;
		DefinitionKey key = new DefinitionKey() ;
		key.setType("page");
		key.setName("test");
		key.setVersion("1");
		view.setViewName(DefinitionKeyUtils.format(key));
		return view ;
	}
}
