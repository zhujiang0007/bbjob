package com.rundatop.sys.service;

import java.util.List;

import com.rundatop.core.service.IService;
import com.rundatop.sys.dto.FunctionTreeEntity;
import com.rundatop.sys.model.SysFunction;
public interface IFunctionService extends IService<SysFunction>{

	List<SysFunction> getFunctionsByPid(Integer pid);

	List<FunctionTreeEntity> selectFunctionTreeByPid(Integer pid);

	

}
