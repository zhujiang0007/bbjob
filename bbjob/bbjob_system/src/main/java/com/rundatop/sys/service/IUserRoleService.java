package com.rundatop.sys.service;

import java.util.List;

import com.rundatop.core.exception.BizException;
import com.rundatop.core.service.IService;
import com.rundatop.sys.dto.SysUserRoleEntity;
import com.rundatop.sys.model.SysUserRole;

public interface IUserRoleService extends IService<SysUserRole>{

	public List<SysUserRole> selectUserRoleBy(Integer userId);
	public List<Integer> selectUserRoleIdsBy(Integer userId);
	public void saveBatch(Integer userId, Integer[] roleIds) throws BizException;
	public void deleteBatch(Integer userId, Integer[] roleIds) throws BizException;
}
