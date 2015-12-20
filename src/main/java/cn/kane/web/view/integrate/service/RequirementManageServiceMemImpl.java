package cn.kane.web.view.integrate.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import cn.kane.web.view.integrate.pojo.Requirement;

public class RequirementManageServiceMemImpl implements RequirementManageService {

	private Map<String,Requirement> store = new ConcurrentHashMap<String,Requirement>() ;
	private AtomicLong counter = new AtomicLong(1) ;
	@Override
	public String add(Requirement requirement) {
		if(null == requirement){
			return null;
		}
		Date now = new Date() ;
		if(null == requirement.getId()){
			requirement.setId(String.valueOf(counter.incrementAndGet())) ;
			requirement.setStatus("new") ;
			requirement.setCtime(now) ;
		}
		requirement.setMtime(now) ;
		store.put(requirement.getId(), requirement) ;
		return requirement.getId() ;
	}

	@Override
	public void edit(Requirement requirement) {
		if(!store.containsKey(requirement.getId())){
			throw new IllegalArgumentException(String.format("not found Requirement[id=%s]",requirement.getId()));
		}
		store.put(requirement.getId(), requirement) ;
	}

	@Override
	public Requirement get(String id) {
		return store.get(id);
	}

	@Override
	public void remove(String id) {
		if(!store.containsKey(id)){
			throw new IllegalArgumentException(String.format("not found Requirement[id=%s]",id));
		}
		store.remove(id) ;
	}
	
	@Override
	public List<Requirement> list(String name, Date fromDate, Date toDate) {
		throw new UnsupportedOperationException("not support list now");
	}


}
