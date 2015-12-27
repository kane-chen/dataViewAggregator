package cn.kane.dynamicview.resource.entity;

import cn.kane.dynamicview.resource.service.DataReadService;

public class DataReader extends Resource {

	private static final long serialVersionUID = 4102289552284643976L;

	private DataReadService dataReadService;

	private String sourceCode;

	public DataReadService getDataReadService() {
		return dataReadService;
	}

	public void setDataReadService(DataReadService dataReadService) {
		this.dataReadService = dataReadService;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Object getData(Object param) {
		return this.dataReadService.getData(param);
	}
}
