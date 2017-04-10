package com.rundatop.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.rundatop.sys.dto.SysRoleEntity;
import com.rundatop.sys.model.SysRole;
import com.rundatop.sys.service.IRoleService;

@RequestMapping("sys")
@RestController
public class RoleController {
	@Resource
	private IRoleService roleService;
	@RequestMapping(value="roles",method=RequestMethod.POST)
	public PageInfo<SysRole> selectRoleList(@RequestBody(required=false) SysRoleEntity sysRole,@RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int rows){
		List<SysRole> list=roleService.selectRoleByRole(sysRole, page, rows);
		return new PageInfo<SysRole>(list);
	}
	
	@RequestMapping(value="role/{roleId}",method=RequestMethod.GET)
	public SysRole getUser(@PathVariable("roleId") Integer roleId){
		SysRole sysRole= roleService.selectByKey(roleId);
		return sysRole;
	}
	@RequestMapping(value="role",method=RequestMethod.PATCH)
	public String editUser(@RequestBody SysRole sysRole){
		roleService.updateNotNull(sysRole);
		return "修改成功！";
	}
	@RequestMapping(value="role",method=RequestMethod.PUT)
	public String saveUser(@RequestBody SysRole sysRole){
		roleService.save(sysRole);
		return "保存成功！";
	}
	@RequestMapping(value="role/{roleId}",method=RequestMethod.DELETE)
	public String deleteUser(@PathVariable("roleId") Integer roleId){
		roleService.delete(roleId);
		return "删除成功";
	}
}
