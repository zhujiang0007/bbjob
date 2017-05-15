package com.rundatop.sys.dto;

public class SysUserFunctionEntity {
	
	private Integer userId;
	private Integer[] functionIds;
	
	public Integer[] getFunctionIds() {
		return functionIds;
	}
	public void setFunctionIds(Integer[] functionIds) {
		this.functionIds = functionIds;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
