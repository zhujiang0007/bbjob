package com.rundatop.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rundatop.core.service.impl.BaseService;
import com.rundatop.sys.model.SysUserRole;
import com.rundatop.sys.service.IUserRoleService;

import tk.mybatis.mapper.entity.Example;
@Service
public class SysUserRoleServiceImpl extends BaseService<SysUserRole> implements IUserRoleService{

	
	public List<SysUserRole> selectUserRoleBy(Integer userId) {
		Example example=new Example(SysUserRole.class);
		example.createCriteria().andEqualTo("userId", userId);
		return mapper.selectByExample(example);
	}
	public List<Integer> selectUserRoleIdsBy(Integer userId) {
		List<Integer> roleIdList=Collections.emptyList();
		List<SysUserRole> list= selectUserRoleBy(userId);		
		if(list.size()>0){
			for(SysUserRole userRole:list){
				roleIdList.add(userRole.getRoleId());
			}
		}		
		return roleIdList;
	}
}
