package com.rundatop.sys.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rundatop.core.exception.BizException;
import com.rundatop.sys.dto.SysUserFunctionEntity;
import com.rundatop.sys.model.SysFunction;
import com.rundatop.sys.model.SysUserFunction;
import com.rundatop.sys.service.IFunctionService;
import com.rundatop.sys.service.IUserFunctionService;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("sys")
public class UserFunctionController {
	@Resource
	private IUserFunctionService userFunctionService;
	@Resource
	private IFunctionService functionService;
	@RequestMapping(value="userfunction/{userId}/list/{pid}", method=RequestMethod.GET)
	public List<SysFunction> getUserFunctions(@PathVariable("userId")Integer userId,@PathVariable("pid")Integer pid){
		Example example1=new Example(SysUserFunction.class);
		example1.createCriteria().andEqualTo("userId", userId);
		List<SysUserFunction> list=userFunctionService.selectByExample(example1);
		if(list.size()==0)
			return Collections.emptyList();
		Example example2=new Example(SysFunction.class);
		ArrayList<Integer> idList=new ArrayList<Integer>(list.size());
		for(SysUserFunction userFunction:list){
			idList.add(userFunction.getUserId());
		}
		example2.createCriteria().andIn("id", idList).andEqualTo("pId", pid);
		return functionService.selectByExample(example2);
	}
	@RequestMapping(value="userfunction/{userId}/list", method=RequestMethod.GET)
	public List<Integer> getUserFunctionIds(@PathVariable("userId")Integer userId){
		Example example1=new Example(SysUserFunction.class);
		example1.createCriteria().andEqualTo("userId", userId);
		List<SysUserFunction> list=userFunctionService.selectByExample(example1);
		if(list.size()==0)
			return Collections.emptyList();
		List<Integer> functionids=new ArrayList<Integer>();
		for(SysUserFunction userfunc:list){
			functionids.add(userfunc.getFunctionId());
		}
		return functionids;
	}
	@RequestMapping(value="userfunction", method=RequestMethod.PUT)
	public String addRole(@RequestBody SysUserFunctionEntity sysUserFunction) throws BizException{
		this.userFunctionService.saveBatch(sysUserFunction.getUserId(), sysUserFunction.getFunctionIds());
		return "保存成功!";
	}
	@RequestMapping(value="userfunction", method=RequestMethod.DELETE)
	public String deleteRole(SysUserFunctionEntity sysUserFunction) throws BizException{
		this.userFunctionService.deleteBatch(sysUserFunction.getUserId(), sysUserFunction.getFunctionIds());
		return "删除成功!";
	}
}
