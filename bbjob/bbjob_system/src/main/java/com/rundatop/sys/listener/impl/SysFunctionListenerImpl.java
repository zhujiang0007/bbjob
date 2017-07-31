package com.rundatop.sys.listener.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.rundatop.sys.listener.FunctionListener;
import com.rundatop.sys.mapper.SysRoleFunctionMapper;
import com.rundatop.sys.mapper.SysUserFunctionMapper;
import com.rundatop.sys.model.SysFunction;
import com.rundatop.sys.model.SysRoleFunction;
import com.rundatop.sys.model.SysUserFunction;
@Component
public class SysFunctionListenerImpl implements FunctionListener{
	@Resource
	private SysRoleFunctionMapper roleFunctionMapper;
	@Resource
	private SysUserFunctionMapper userFunctionMapper;
	@Override
	public void beforeAdd(SysFunction entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterAdd(SysFunction entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeEdit(SysFunction entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterEdit(SysFunction entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeDelete(SysFunction entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterDelete(SysFunction entity) {
		Integer functionId=entity.getId();
		SysRoleFunction roleFunction=new SysRoleFunction();
		roleFunction.setFunctionId(functionId);
		roleFunctionMapper.delete(roleFunction);
		SysUserFunction sysFunction=new SysUserFunction();
		sysFunction.setFunctionId(functionId);
		userFunctionMapper.delete(sysFunction);
	}

}
