package com.rundatop.sys.dto;

public class SysRoleFunctionEntity {
	
	private Integer roleId;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	private Integer[] functionIds;
	
	public Integer[] getFunctionIds() {
		return functionIds;
	}
	public void setFunctionIds(Integer[] functionIds) {
		this.functionIds = functionIds;
	}
	
	
}
