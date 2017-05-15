package com.rundatop.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rundatop.core.exception.BizException;
import com.rundatop.core.service.impl.BaseService;
import com.rundatop.sys.model.SysRoleFunction;
import com.rundatop.sys.model.SysUserFunction;
import com.rundatop.sys.service.IRoleFunctionService;
import com.rundatop.sys.service.IRoleService;
@Service
public class RoleFunctionServiceImpl extends BaseService<SysRoleFunction> implements IRoleFunctionService{
	@Resource
	private IRoleService roleService;
	@Override
	public void saveBatch(Integer roleId, Integer[] functionIds) throws BizException {
		if(roleId==null){
			throw new BizException("roleId is null");
		}
		if(functionIds==null||functionIds.length==0){
			throw new BizException("functionIds is "+functionIds);
		}
		if(roleService.selectByKey(roleId)==null){
			throw new BizException("id为"+roleId+"的角色不存在!");
		}
		for(Integer functionId:functionIds){
			SysRoleFunction roleFunc=new SysRoleFunction();
			roleFunc.setRoleId(roleId);
			roleFunc.setFunctionId(functionId);
			this.save(roleFunc);
		}
	}

	@Override
	public void deleteBatch(Integer roleId, Integer[] functionIds) throws BizException {
		if(roleId==null){
			throw new BizException("roleId is null");
		}
		if(functionIds==null||functionIds.length==0){
			throw new BizException("functionIds is "+functionIds);
		}
		if(roleService.selectByKey(roleId)==null){
			throw new BizException("id为"+roleId+"的角色不存在!");
		}
		for(Integer functionId:functionIds){
			SysRoleFunction roleFunc=new SysRoleFunction();
			roleFunc.setRoleId(roleId);
			roleFunc.setFunctionId(functionId);
			this.delete(roleFunc);
		}
		
	}

}
