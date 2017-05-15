package com.rundatop.security.base.handler;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.rundatop.security.base.service.AuthorityFunctionService;

public class RoleSwitchLoginSuccessHandler extends SuccessHandler implements InitializingBean{
	private AuthorityFunctionService service;
	public AuthorityFunctionService getService() {
		return service;
	}

	public void setService(AuthorityFunctionService service) {
		this.service = service;
	}
	private String[] roleList;
	private HashMap<String,String> urlMap=new HashMap<String,String>();
	private String copyDefaultTargetUrl;
	public String[] getRoleList() {
		return roleList;
	}

	public void setRoleList(String[] roleList) {
		this.roleList = roleList;
	}

	
	public void afterPropertiesSet() throws Exception {
		copyDefaultTargetUrl=this.getDefaultTargetUrl();
		for(String auth:roleList){
			String url= service.getFuncUrlById(auth);
			urlMap.put("ROLE_"+auth, url);
		}
		
	}
	 @Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		 boolean flag=false;
		for(String targetAuth:roleList){		
			for(GrantedAuthority auth:authentication.getAuthorities()){
				if(flag)
					break;
				if(("ROLE_"+targetAuth).equals(auth.getAuthority())){
					flag=true;
					this.setDefaultTargetUrl(urlMap.get("ROLE_"+targetAuth));
					break;
				}
				
			}
		}
		if(!flag){
			setDefaultTargetUrl(copyDefaultTargetUrl);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
