package cn.kane.web.view.integrate.state;

public enum IntegrateStatus {

	NEW("NEW","COMMIT",null),
	COMMITED("COMMITED","PUBLISH","new"),
	PUBLISHED("PUBLISHED",null,null),
	ROLLBACKED("ROLLBACKED",null,null),
	DISABLED("DISABLED",null,null)
	;
	
	private String status ;
	private String forwardName ;
	private String backwardName ;
	private IntegrateStatus(String status,String forwardName,String backwardName){
		this.status = status ;
	}

	public String status(){
		return this.status ;
	}
	
	public String forwardName(){
		return this.forwardName ;
	}
	
	public String backwardName(){
		return this.backwardName ;
	}
}
