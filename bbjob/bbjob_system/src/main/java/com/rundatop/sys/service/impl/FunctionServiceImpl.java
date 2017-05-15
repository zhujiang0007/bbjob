package com.rundatop.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rundatop.core.service.impl.BaseService;
import com.rundatop.security.base.service.AuthorityFunctionService;
import com.rundatop.sys.dto.FunctionTreeEntity;
import com.rundatop.sys.model.SysFunction;
import com.rundatop.sys.service.IFunctionService;

import tk.mybatis.mapper.entity.Example;
@Service("functionServiceImpl")
public class FunctionServiceImpl extends BaseService<SysFunction> implements IFunctionService,AuthorityFunctionService{

	
	public String getFuncUrlById(String id) {
		SysFunction function= this.selectByKey(Integer.parseInt(id));
		return function.getUrl();
	}

	@Override
	public List<SysFunction> getFunctionsByPid(String pid) {
		Example example=new Example(SysFunction.class);
		example.createCriteria().andEqualTo("pId",pid)
		.andEqualTo("isVisible", 1);
		return this.selectByExample(example);
	}

	@Override
	public List<FunctionTreeEntity> selectFunctionTreeByPid(String pid) {
		List<FunctionTreeEntity> resultList=new ArrayList<FunctionTreeEntity>();
		List<SysFunction> list=getFunctionsByPid(pid);
		for(SysFunction function:list){
			FunctionTreeEntity entity=new FunctionTreeEntity();
			entity.setId(function.getId());
			entity.setIsMenu(function.getIsMenu());
			entity.setName(function.getName());
			entity.setRemkark(function.getRemark());
			if("1".equals(function.getIsMenu())){
				entity.getNodes().addAll(selectFunctionTreeByPid(function.getId()+""));
			}
			resultList.add(entity);
		}
		return resultList;
	}

}
