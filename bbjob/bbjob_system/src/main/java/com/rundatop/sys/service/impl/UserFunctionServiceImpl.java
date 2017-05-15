package com.rundatop.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rundatop.core.exception.BizException;
import com.rundatop.core.service.impl.BaseService;
import com.rundatop.sys.model.SysUserFunction;
import com.rundatop.sys.service.IUserFunctionService;
import com.rundatop.sys.service.IUserService;

import tk.mybatis.mapper.entity.Example;

@Service
public class UserFunctionServiceImpl extends BaseService<SysUserFunction> implements IUserFunctionService{
	@Resource
	private IUserService userService;
	@Override
	public List<Integer> selectFunctionsIdByUserId(Integer userId) {
		Example example=new Example(SysUserFunction.class);
		example.createCriteria().andEqualTo("userId",userId);
		List<SysUserFunction> userFuncList=this.selectByExample(example);
		if(userFuncList.size()==0)
			return Collections.emptyList();
		List<Integer> funcIds=new ArrayList<Integer>();
		for(SysUserFunction userFunc:userFuncList){
			funcIds.add(userFunc.getFunctionId());
		}
		return funcIds;
	}

	@Override
	public void saveBatch(Integer userId, Integer[] functionIds) throws BizException {
		if(userId==null){
			throw new BizException("userId is null");
		}
		if(functionIds==null||functionIds.length==0){
			throw new BizException("functionIds is "+functionIds);
		}
		if(userService.selectByKey(userId)==null){
			throw new BizException("id为"+userId+"的用户不存在!");
		}
		for(Integer functionId:functionIds){
			SysUserFunction userFunc=new SysUserFunction();
			userFunc.setUserId(userId);
			userFunc.setFunctionId(functionId);
			this.save(userFunc);
		}
		
	}

	@Override
	public void deleteBatch(Integer userId, Integer[] functionIds) throws BizException {
		if(userId==null){
			throw new BizException("userId is null");
		}
		if(functionIds==null||functionIds.length==0){
			throw new BizException("functionIds is "+functionIds);
		}
		if(userService.selectByKey(userId)==null){
			throw new BizException("id为"+userId+"的用户不存在!");
		}
		for(Integer functionId:functionIds){
			SysUserFunction userFunc=new SysUserFunction();
			userFunc.setUserId(userId);
			userFunc.setFunctionId(functionId);
			this.delete(userFunc);
		}
		
	}

}
