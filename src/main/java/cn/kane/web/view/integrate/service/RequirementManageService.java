package cn.kane.web.view.integrate.service;

import java.util.Date;
import java.util.List;

import cn.kane.web.view.integrate.pojo.Requirement;

public interface RequirementManageService {

	String add(Requirement requirement) ;
	
	void edit(Requirement requirement) ;
	
	Requirement get(String id) ;
	
	void remove(String id) ;
	
	List<Requirement> list(String name,Date fromDate,Date toDate) ;
}
