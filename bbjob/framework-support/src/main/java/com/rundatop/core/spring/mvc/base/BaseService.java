package com.rundatop.core.spring.mvc.base;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public abstract class BaseService {

	protected  String getUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid.toUpperCase();
	}
	
	protected  String getExtName(String fileName){
		if(StringUtils.isNotBlank(fileName)){
			return fileName.substring(fileName.lastIndexOf("."));
		}
		return null;
	}
}
