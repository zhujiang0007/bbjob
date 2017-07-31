package com.rundatop.sys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rundatop.sys.dto.SysUser;
import com.rundatop.sys.mapper.SysRoleFunctionMapper;
import com.rundatop.sys.mapper.SysUserFunctionMapper;
import com.rundatop.sys.mapper.SysUserRoleMapper;
import com.rundatop.sys.mapper.UserMapper;
import com.rundatop.sys.model.SysRoleFunction;
import com.rundatop.sys.model.SysUserFunction;
import com.rundatop.sys.model.SysUserRole;
import com.rundatop.sys.model.User;

import tk.mybatis.mapper.entity.Example;
@Service("userLoginService")
public class LoginService implements UserDetailsService {
	@Resource
	private UserMapper userMapper;
	@Resource
	private SysUserRoleMapper sysUserRoleMapper;
	@Resource
	private SysUserFunctionMapper sysUserFunctionMapper;
	@Resource
	private SysRoleFunctionMapper sysRoleFunctionMapper;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Example example=new Example(User.class);
		example.createCriteria().andEqualTo("username",username);
		List<User> list= userMapper.selectByExample(example);
		if(list.size()>0){
			List<SimpleGrantedAuthority> gtantedList=getAllFunctionsBy(list.get(0).getId());
			return new SysUser(list.get(0), gtantedList);
		}
		throw new UsernameNotFoundException(username);
	}
	public List<SimpleGrantedAuthority> getAllFunctionsBy(Integer userId){
		List<SimpleGrantedAuthority> gtantedList=new ArrayList<SimpleGrantedAuthority>();
		List<SimpleGrantedAuthority> userFunList=getUserFunctions(userId);
		List<SimpleGrantedAuthority> userRoleFunList=getUserRoleFunctions(userId);
		gtantedList.addAll(userFunList);
		gtantedList.addAll(userRoleFunList);
		HashSet<SimpleGrantedAuthority> set=new HashSet<SimpleGrantedAuthority>(gtantedList);
		gtantedList.clear();
		gtantedList.addAll(set);
		return gtantedList;
	}
	private List<SimpleGrantedAuthority> getUserFunctions(Integer userId){
		List<SimpleGrantedAuthority> gtantedList=new ArrayList<SimpleGrantedAuthority>();
		Example example=new Example(SysUserFunction.class);
		Example.Criteria c=example.createCriteria();
		c.andEqualTo("userId", userId);
		List<SysUserFunction> funList=sysUserFunctionMapper.selectByExample(example);
		if(funList.size()>0){
			for(SysUserFunction fun:funList){
				SimpleGrantedAuthority a=new SimpleGrantedAuthority("ROLE_"+fun.getFunctionId()+"");
				gtantedList.add(a);
			}
		}
		return gtantedList;
	}
	private List<SimpleGrantedAuthority> getUserRoleFunctions(Integer userId){
		List<SimpleGrantedAuthority> gtantedList=new ArrayList<SimpleGrantedAuthority>();
		Example example=new Example(SysUserRole.class);
		Example.Criteria c=example.createCriteria();
		c.andEqualTo("userId", userId);
		List<SysUserRole> userRoleList=this.sysUserRoleMapper.selectByExample(example);
		if(userRoleList.size()>0){
			List<Integer> roleIds=new ArrayList<Integer>(userRoleList.size());
			for(SysUserRole userRole :userRoleList){
				roleIds.add(userRole.getRoleId());
			}
			Example example1=new Example(SysRoleFunction.class);
			Example.Criteria c1=example1.createCriteria();
			c1.andIn("roleId", roleIds);
			List<SysRoleFunction> roleFunList=this.sysRoleFunctionMapper.selectByExample(example1);
			if(roleFunList.size()>0){
				for(SysRoleFunction rolefun:roleFunList){
					SimpleGrantedAuthority a=new SimpleGrantedAuthority("ROLE_"+rolefun.getFunctionId()+"");
					gtantedList.add(a);
				}
				
			}
		}
		return gtantedList;
	}

}
