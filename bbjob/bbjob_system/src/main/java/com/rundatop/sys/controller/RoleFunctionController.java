package com.rundatop.sys.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rundatop.core.exception.BizException;
import com.rundatop.sys.dto.SysRoleFunctionEntity;
import com.rundatop.sys.model.SysFunction;
import com.rundatop.sys.model.SysRoleFunction;
import com.rundatop.sys.service.IFunctionService;
import com.rundatop.sys.service.IRoleFunctionService;

import tk.mybatis.mapper.entity.Example;
@RestController
@RequestMapping("sys")
public class RoleFunctionController {
	@Resource
	private IRoleFunctionService roleFunctionService;
	@Resource
	private IFunctionService functionService;
	@RequestMapping(value="rolefunction/list/{roleId}", method=RequestMethod.GET)
	public List<Integer> getRoleFunctions(@PathVariable("roleId")String roleId){
		Example example1=new Example(SysRoleFunction.class);
		example1.createCriteria().andEqualTo("roleId", roleId);
		List<SysRoleFunction> list=roleFunctionService.selectByExample(example1);
		if(list.size()==0)
			return Collections.emptyList();
		ArrayList<Integer> idList=new ArrayList<Integer>(list.size());
		for(SysRoleFunction roleFunction:list){
			idList.add(roleFunction.getFunctionId());
		}
		
		return idList;
	}
	@RequestMapping(value="rolefunction", method=RequestMethod.PUT)
	public String addRole(@RequestBody SysRoleFunctionEntity sysRoleFunction) throws BizException{
		this.roleFunctionService.saveBatch(sysRoleFunction.getRoleId(),sysRoleFunction.getFunctionIds());
		return "保存成功!";
	}
	@RequestMapping(value="rolefunction", method=RequestMethod.DELETE)
	public String deleteRole(SysRoleFunctionEntity SysRoleFunction) throws BizException{
		this.roleFunctionService.deleteBatch(SysRoleFunction.getRoleId(),SysRoleFunction.getFunctionIds());
		return "删除成功!";
	}
}
