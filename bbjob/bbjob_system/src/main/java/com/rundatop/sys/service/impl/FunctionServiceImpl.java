package com.rundatop.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rundatop.core.listener.IListener;
import com.rundatop.core.service.impl.BaseService;
import com.rundatop.core.spring.annotation.Listener;
import com.rundatop.security.base.service.AuthorityFunctionService;
import com.rundatop.sys.dto.FunctionTreeEntity;
import com.rundatop.sys.listener.FunctionListener;
import com.rundatop.sys.model.SysFunction;
import com.rundatop.sys.service.IFunctionService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service("functionServiceImpl")
public class FunctionServiceImpl extends BaseService<SysFunction> implements IFunctionService,AuthorityFunctionService{
	@Listener
	private List<FunctionListener> listeners;
	
	public String getFuncUrlById(String id) {
		SysFunction function= this.selectByKey(Integer.parseInt(id));
		return function.getUrl();
	}

	public List<SysFunction> getFunctionsByPid(Integer pid) {	
		return getFunctionsByPid(pid,1);
	}
	public List<SysFunction> getFunctionsByPid(Integer pid,Integer isVisable) {
		Example example=new Example(SysFunction.class);
		Criteria c=example.createCriteria();
		c.andEqualTo("pId",pid);
		if(isVisable!=null)
		c.andEqualTo("isVisible", isVisable);
		return this.selectByExample(example);
	}

	@Override
	public List<FunctionTreeEntity> selectFunctionTreeByPid(Integer pid) {
		List<FunctionTreeEntity> resultList=new ArrayList<FunctionTreeEntity>();
		List<SysFunction> list=getFunctionsByPid(pid);
		for(SysFunction function:list){
			FunctionTreeEntity entity=new FunctionTreeEntity();
			entity.setId(function.getId());
			entity.setIsMenu(function.getIsMenu());
			entity.setName(function.getName());
			entity.setRemkark(function.getRemark());
			if("1".equals(function.getIsMenu())){
				entity.getNodes().addAll(selectFunctionTreeByPid(function.getId()));
			}
			resultList.add(entity);
		}
		return resultList;
	}
	@Override
	public int save(SysFunction entity) {
		entity.setPermissionCode(entity.getPermissionCode());
		Integer sortNo=this.getMaxSortNo(entity.getpId());
		entity.setSortNo(sortNo==null?1:sortNo);
		return super.save(entity);
	}
	public int getMaxSortNo(Integer pId){
		Example example=new Example(SysFunction.class);
		example.createCriteria().andEqualTo("pId",pId);
		example.setOrderByClause("sort_no desc");
		List<SysFunction> functions= this.mapper.selectByExample(example);
		if(functions.size()==0)
			return 1;
		return functions.get(0).getSortNo();
	}
	@Override
	public int delete(Object key) {
		SysFunction function=this.selectByKey(key);
		List<SysFunction> list=this.getFunctionsByPid(function.getId(),null);
		for(SysFunction func:list){
			this.delete(func.getId());
		}
		if(listeners!=null){
			for(IListener listener:listeners){
				listener.beforeDelete(function);
			}
		}
		return super.delete(key);
	}

}
