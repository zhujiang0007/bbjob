package com.rundatop.security.base.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CheckCodeAuthenticationToken extends UsernamePasswordAuthenticationToken{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code="";
	private String ip;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public CheckCodeAuthenticationToken(String principal, String credentials,String code,String ip){
		super(principal,credentials);
		this.code=code;
	}

}
