package com.rundatop.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.rundatop.core.service.impl.BaseService;
import com.rundatop.sys.dto.SysRoleEntity;
import com.rundatop.sys.model.SysRole;
import com.rundatop.sys.service.IRoleService;
import com.rundatop.sys.service.IUserRoleService;

import tk.mybatis.mapper.entity.Example;
@Service
public class SysRoleServiceImpl extends BaseService<SysRole> implements IRoleService{
	@Resource
	private IUserRoleService userRoleService;

	
	public List<SysRole> selectRoleByRole(SysRoleEntity sysRole, int page, int rows) {
		Example example=new Example(SysRole.class);
		Example.Criteria criteria=example.createCriteria();
		if(sysRole!=null&&StringUtils.isNotBlank(sysRole.getRoleName())){
			criteria.andEqualTo("roleName", sysRole.getRoleName());
		}
		if(sysRole!=null&&sysRole.getUserId()!=null){
			List<Integer> roleIds=userRoleService.selectUserRoleIdsBy(sysRole.getUserId());
			criteria.andNotIn("id", roleIds);
		}
		List<SysRole> resultList=mapper.selectByExample(example);
		return resultList;
	}

}
