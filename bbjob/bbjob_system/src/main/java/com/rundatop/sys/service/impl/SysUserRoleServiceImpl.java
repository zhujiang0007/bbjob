package com.rundatop.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rundatop.core.exception.BizException;
import com.rundatop.core.service.impl.BaseService;
import com.rundatop.sys.model.SysUserRole;
import com.rundatop.sys.service.IUserRoleService;
import com.rundatop.sys.service.IUserService;

import tk.mybatis.mapper.entity.Example;
@Service
public class SysUserRoleServiceImpl extends BaseService<SysUserRole> implements IUserRoleService{
	@Resource
	private IUserService userService;
	public List<SysUserRole> selectUserRoleBy(Integer userId) {
		Example example=new Example(SysUserRole.class);
		example.createCriteria().andEqualTo("userId", userId);
		return mapper.selectByExample(example);
	}
	public List<Integer> selectUserRoleIdsBy(Integer userId) {
		List<Integer> roleIdList=new ArrayList<Integer>();
		List<SysUserRole> list= selectUserRoleBy(userId);		
		if(list.size()>0){
			for(SysUserRole userRole:list){
				roleIdList.add(userRole.getRoleId());
			}
		}		
		return roleIdList;
	}
	@Override
	public void saveBatch(Integer userId, Integer[] roleIds) throws BizException {
		if(userId==null){
			throw new BizException("userId is null");
		}
		if(roleIds==null||roleIds.length==0){
			throw new BizException("roleIds is "+roleIds);
		}
		if(userService.selectByKey(userId)==null){
			throw new BizException("id为"+userId+"的用户不存在!");
		}
			
		for(Integer roleId:roleIds){
			SysUserRole role=new SysUserRole();
			role.setRoleId(roleId);
			role.setUserId(userId);
			this.save(role);
		}
		
	}
	@Override
	public void deleteBatch(Integer userId, Integer[] roleIds) throws BizException {
		if(userId==null){
			throw new BizException("userId is null");
		}
		if(roleIds==null||roleIds.length==0){
			throw new BizException("roleIds is "+roleIds);
		}
		if(userService.selectByKey(userId)==null){
			throw new BizException("id为"+userId+"的用户不存在!");
		}
			
		for(Integer roleId:roleIds){
			SysUserRole role=new SysUserRole();
			role.setRoleId(roleId);
			role.setUserId(userId);
			this.delete(role);
		}
		
	}
}
