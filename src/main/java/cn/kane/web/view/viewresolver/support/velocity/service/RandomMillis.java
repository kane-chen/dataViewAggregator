package cn.kane.web.view.viewresolver.support.velocity.service;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class RandomMillis implements Serializable{
	
	private static final long serialVersionUID = -2055019851996852749L;
	private Long base ;
	private Integer range ;

	public Long getBase() {
		return base;
	}

	public void setBase(Long base) {
		this.base = base;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this) ;
	}
}
