package com.rundatop.sys.service;

import com.rundatop.core.exception.BizException;
import com.rundatop.core.service.IService;
import com.rundatop.sys.model.SysRoleFunction;

public interface IRoleFunctionService extends IService<SysRoleFunction>{

	void saveBatch(Integer roleId, Integer[] functionIds) throws BizException;

	void deleteBatch(Integer roleId, Integer[] functionIds) throws BizException;

}
