package com.rundatop.core.common;


public class InterfaceResult extends Result {
	public final static String STAE_CODE_NOPERMISSION = "302"; // 表示无权限
	public final static String TYPE_PAGE = "pagelist"; // 分页
	public final static String TYPE_LIST = "list"; // 不分页
	public final static String TYPE_MESSAGE = "message"; // 提示
	public final static String TYPE_MAP = "map"; // 
	public final static String TYPE_RECORD="record";
	public final static String TYPE_ERROR="error";
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	} 
	
	public InterfaceResult(String code,String message,String type){
		setStatusCode(code);
		setMessage(message);
		this.type = type;
	}
	public InterfaceResult(String code,String message){
	super(code, message);
	}
}
