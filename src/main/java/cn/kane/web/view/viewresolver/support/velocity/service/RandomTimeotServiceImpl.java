package cn.kane.web.view.viewresolver.support.velocity.service;

import java.util.Map;
import java.util.Random;

public class RandomTimeotServiceImpl implements RandomTimeotService{

	private static final String BASE_KEY = "net" ;
	private static final String RANGE_KEY = "micro" ;
	private Long baseMillis = 150L ;
	private Integer rangeMillis = 50 ;
	private Random random = new Random() ;
	@Deprecated
	public Object getData(Object param) {
		long base = this.getBase(param) ;
		long range = this.getRange(param) ;
		long sleepMillis = this.getRandom(base, (int)range) ;
		try {
			Thread.sleep(sleepMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return param ;
	}
	
	private long getRandom(long base,int range){
		int added = random.nextInt(range) ;
		return base+added ;
	}
	
	@SuppressWarnings("unchecked")
	private long getBase(Object param){
		if(null == param){
			return baseMillis ;
		}
		if(param instanceof Map){
			return baseMillis ;
		}
		Map<String,Object> map = (Map<String, Object>) param ;
		Object value = map.get(BASE_KEY) ;
		long base = (Long)value ;
		return base ;
	}

	@SuppressWarnings("unchecked")
	private long getRange(Object param){
		if(null == param){
			return rangeMillis ;
		}
		if(param instanceof Map){
			return rangeMillis ;
		}
		Map<String,Object> map = (Map<String, Object>) param ;
		Object value = map.get(RANGE_KEY) ;
		long range = (Long)value ;
		return range ;
	}

	@Override
	public Object cost(RandomMillis param) {
		if(null == param){
			param = new RandomMillis() ;
		}
		if(null == param.getBase()){
			param.setBase(baseMillis);
		}
		if(null == param.getRange()){
			param.setRange(rangeMillis);
		}
		long sleepMillis = this.getRandom(param.getBase(), param.getRange()) ;
		try {
			Thread.sleep(sleepMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "SUCCESS";
	}
}
