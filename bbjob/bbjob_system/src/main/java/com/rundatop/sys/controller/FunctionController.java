package com.rundatop.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rundatop.sys.dto.FunctionTreeEntity;
import com.rundatop.sys.model.SysFunction;
import com.rundatop.sys.service.IFunctionService;
import com.rundatop.sys.service.IUserFunctionService;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("sys/function")
public class FunctionController {
	@Resource
	private IFunctionService functionService;
	@Resource
	private IUserFunctionService userFunctionService;
	@RequestMapping(value="list/{pid}",method=RequestMethod.GET)
	public List<SysFunction> getFunction(@PathVariable("pid") Integer pid){
		
		Example example=new Example(SysFunction.class);
		Example.Criteria criteria=example.createCriteria();
		if(pid==null)
			criteria.andIsNull("pId");
		else
			criteria.andEqualTo("pId", pid);
		return functionService.selectByExample(example);
	}
	@RequestMapping(value="list",method=RequestMethod.GET)
	public List<SysFunction> getFunction(){
		
		Example example=new Example(SysFunction.class);
		Example.Criteria criteria=example.createCriteria();
			criteria.andIsNull("pId");
		return functionService.selectByExample(example);
	}
	@RequestMapping(value="tree/{pid}",method=RequestMethod.GET)
	public List<FunctionTreeEntity> getFunctionTree(@PathVariable("pid") Integer pid){		
		return functionService.selectFunctionTreeByPid(pid+"");
	}
}
