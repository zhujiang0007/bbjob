package com.rundatop.sys.service;

import java.util.List;

import com.rundatop.core.exception.BizException;
import com.rundatop.core.service.IService;
import com.rundatop.sys.model.SysUserFunction;

public interface IUserFunctionService extends IService<SysUserFunction>{

	List<Integer> selectFunctionsIdByUserId(Integer userId);

	void saveBatch(Integer userId, Integer[] functionIds) throws BizException ;

	void deleteBatch(Integer userId, Integer[] functionIds) throws BizException ;

}
