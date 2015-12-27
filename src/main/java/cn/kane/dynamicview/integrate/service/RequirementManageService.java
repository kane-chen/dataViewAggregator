package cn.kane.dynamicview.integrate.service;

import java.util.Date;
import java.util.List;

import cn.kane.dynamicview.integrate.entity.Requirement;

public interface RequirementManageService {

	String add(Requirement requirement) ;
	
	void edit(Requirement requirement) ;
	
	void remove(String id) ;
	
	Requirement get(String id) ;

	void compareAndSetStatus(String id,String status,String newStatus) ;
	
	List<Requirement> list(String name,Date fromDate,Date toDate) ;
}
