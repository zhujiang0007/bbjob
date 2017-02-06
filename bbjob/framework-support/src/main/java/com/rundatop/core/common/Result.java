package com.rundatop.core.common;

public class Result {
	public final static String STAE_CODE_SUCCESS = "200"; // 表示操作成功
	public final static String STAE_CODE_ERROR = "300"; // 表示操作失败
	public final static String STAE_CODE_OUTTIME = "301"; // 表示超时
	private String statusCode; 
	private String message; // 提示框中的提示信息
	private Object params;
	private String param;//String参数
	
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Result(){
		this.statusCode = STAE_CODE_SUCCESS;
	}
	
	public Result(String code){
		this.statusCode = code;
	}
	
	public Result(String code,String msg){
		this.statusCode = code;
		this.message = msg;
	}
	
	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
