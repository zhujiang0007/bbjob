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
	@RequestMapping(value="role/page",method=RequestMethod.GET)
	public PageInfo<SysRole> selectRoleListPage(SysRoleEntity sysRole,@RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int rows){
		List<SysRole> list=roleService.selectRoleByRole(sysRole, page, rows);
		return new PageInfo<SysRole>(list);
	}
	@RequestMapping(value="role/list",method=RequestMethod.GET)
	public List<SysRole> selectRoleList(SysRoleEntity sysRole){
		List<SysRole> list=roleService.selectRoleByRole(sysRole);
		return list;
	}
	@RequestMapping(value="role",method=RequestMethod.GET)
	public SysRole getUser(Integer roleId){
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
		sysRole.setEnable(1);
		roleService.save(sysRole);
		return "保存成功！";
	}
	@RequestMapping(value="role",method=RequestMethod.DELETE)
	public String deleteUser(Integer roleId){
		roleService.delete(roleId);
		return "删除成功";
	}
}
