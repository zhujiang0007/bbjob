package com.rundatop.sys.service.impl;

import org.springframework.stereotype.Service;

import com.rundatop.core.service.impl.BaseService;
import com.rundatop.security.base.service.AuthorityFunctionService;
import com.rundatop.sys.model.SysFunction;
import com.rundatop.sys.service.IFunctionService;
@Service("functionServiceImpl")
public class FunctionServiceImpl extends BaseService<SysFunction> implements IFunctionService,AuthorityFunctionService{

	
	public String getFuncUrlById(String id) {
		SysFunction function= this.selectByKey(Integer.parseInt(id));
		return function.getUrl();
	}

}
