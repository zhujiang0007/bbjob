package com.rundatop.core.cache;

import java.io.Serializable;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.google.code.ssm.api.CacheKeyMethod;

public class RequestCacheKeyGenerate implements CacheKeyGenerate,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8874808472808972149L;
	private HttpServletRequest request;
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public RequestCacheKeyGenerate(HttpServletRequest request){
		this.request=request;	}
	@CacheKeyMethod
	public String createCacheKey() {
		Enumeration<String> names= request.getParameterNames();
		StringBuilder sb=new StringBuilder();
		while(names.hasMoreElements()){
			sb.append(request.getParameter(names.nextElement()));
		}
		return sb.toString();
	}
	
}
