package com.rundatop.security.base.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CheckCodeUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code="";
	private String session_code="";
	private String ip;
	public String getSession_code() {
		return session_code;
	}
	public void setSession_code(String session_code) {
		this.session_code = session_code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public CheckCodeUsernamePasswordAuthenticationToken(String principal, String credentials,String session_code,String code,String ip){
		super(principal,credentials);
		this.code=code;
		this.session_code=session_code;
	}
	public boolean VerifyCheckCode(){
		return StringUtils.equals(session_code, code.toUpperCase());
	}

}
