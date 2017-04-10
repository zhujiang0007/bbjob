package com.rundatop.sys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	@RequestMapping(value="userrole/{userId}", method=RequestMethod.GET)
	public List<SysRole> getUserRoles(@PathVariable("userId")String userId){
		Example example1=new Example(SysUserRole.class);
		example1.createCriteria().andEqualTo("userId", userId);
		List<SysUserRole> list=userRoleService.selectByExample(example1);
		Example example2=new Example(SysRole.class);
		ArrayList<Integer> idList=new ArrayList<Integer>(list.size());
		for(SysUserRole userRole:list){
			idList.add(userRole.getUserId());
		}
		example2.createCriteria().andIn("id", idList);
		return roleService.selectByExample(example2);
	}
	@RequestMapping(value="userrole", method=RequestMethod.PUT)
	public String addRole(@RequestBody SysUserRole sysUserRole){
		this.userRoleService.save(sysUserRole);
		return "保存成功!";
	}
	@RequestMapping(value="userrole", method=RequestMethod.DELETE)
	public String deleteRole(@RequestBody SysUserRole sysUserRole){
		this.userRoleService.delete(sysUserRole);
		return "删除成功!";
	}
}
