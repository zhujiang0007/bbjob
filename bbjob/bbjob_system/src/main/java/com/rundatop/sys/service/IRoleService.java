package com.rundatop.sys.service;

import java.util.List;

import com.rundatop.core.service.IService;
import com.rundatop.sys.dto.SysRoleEntity;
import com.rundatop.sys.model.SysRole;

public interface IRoleService  extends IService<SysRole>{
	public List<SysRole> selectRoleByRole(SysRoleEntity sysRole, int page,int rows);

	public List<SysRole> selectRoleByRole(SysRoleEntity sysRole);
}
