package com.rundatop.sys.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rundatop.core.exception.BizException;
import com.rundatop.sys.dto.SysUserRoleEntity;
import com.rundatop.sys.model.SysRole;
import com.rundatop.sys.model.SysUserRole;
import com.rundatop.sys.service.IRoleService;
import com.rundatop.sys.service.IUserRoleService;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("sys")
public class UserRoleController {
	@Resource
	private IUserRoleService userRoleService;
	@Resource
	private IRoleService roleService;
	@RequestMapping(value="userrole", method=RequestMethod.GET)
	public List<SysRole> getUserRoles(String userId){
		Example example1=new Example(SysUserRole.class);
		example1.createCriteria().andEqualTo("userId", userId);
		List<SysUserRole> list=userRoleService.selectByExample(example1);
		if(list.size()==0)
			return Collections.emptyList();
		Example example2=new Example(SysRole.class);
		ArrayList<Integer> idList=new ArrayList<Integer>(list.size());
		for(SysUserRole userRole:list){
			idList.add(userRole.getRoleId());
		}
		example2.createCriteria().andIn("id", idList);
		return roleService.selectByExample(example2);
	}
	@RequestMapping(value="userrole", method=RequestMethod.PUT)
	public String addRole(@RequestBody SysUserRoleEntity userRoleEntity) throws BizException{
		this.userRoleService.saveBatch(userRoleEntity.getUserId(),userRoleEntity.getRoleIds());
		return "保存成功!";
	}
	@RequestMapping(value="userrole", method=RequestMethod.DELETE)
	public String deleteRole(SysUserRoleEntity userRoleEntity) throws BizException{
		this.userRoleService.deleteBatch(userRoleEntity.getUserId(),userRoleEntity.getRoleIds());
		return "删除成功!";
	}
}
