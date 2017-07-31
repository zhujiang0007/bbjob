package com.rundatop.sys.controller;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rundatop.core.exception.BizException;
import com.rundatop.security.base.controller.SecurityController;
import com.rundatop.sys.dto.SysUser;
import com.rundatop.sys.model.SysFunction;
import com.rundatop.sys.service.IFunctionService;
import com.rundatop.sys.service.IMenuService;

@RestController
@RequestMapping("sys")
public class SysMenuController extends SecurityController{
	Logger logger=LoggerFactory.getLogger(getClass());
	@Resource
	private IMenuService menuService;
	@Resource
	private IFunctionService functionService;
	@RequestMapping(value="menu",method=RequestMethod.GET)
	public List<SysFunction> getMenus(@RequestParam(defaultValue="0")Integer pid) throws BizException{
		SysUser user= (SysUser) getCurrentUser();
		if(user.getUserType()==null)
			throw new BizException("用户类型异常，请联系管理员");
		if(user.getUserType()==-1){
			return functionService.getFunctionsByPid(pid);
		}
		Collection<? extends GrantedAuthority> authorties= getCurrentUserAuthorities();
		List<SysFunction> funcList=menuService.selectFuncByUserAuthority(pid,authorties);
		return funcList;
	}
}
