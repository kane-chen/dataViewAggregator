package cn.kane.web.view.viewresolver.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
}
