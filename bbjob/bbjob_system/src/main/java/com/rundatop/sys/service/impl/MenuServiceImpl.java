package com.rundatop.sys.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.rundatop.security.base.AuthorityUtils;
import com.rundatop.sys.model.SysFunction;
import com.rundatop.sys.service.IFunctionService;
import com.rundatop.sys.service.IMenuService;

import tk.mybatis.mapper.entity.Example;
@Service
public class MenuServiceImpl implements IMenuService{
	
	@Resource
	private IFunctionService functionService;
	
	@Override
	public List<SysFunction> selectFuncByUserAuthority(String pid, Collection<? extends GrantedAuthority> authorties) {
		List<String> authortiesString=AuthorityUtils.authorties2String(authorties);
		Example example=new Example(SysFunction.class);
		Example.Criteria criteria= example.createCriteria();
		criteria.andEqualTo("pId", pid);
		if(authortiesString.size()>0)
			criteria.andIn("id", authortiesString);
		criteria.andEqualTo("isVisible", 1);
		return functionService.selectByExample(example);
	}

}
