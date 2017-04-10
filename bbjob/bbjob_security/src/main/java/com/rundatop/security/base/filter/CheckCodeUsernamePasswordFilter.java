package com.rundatop.security.base.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.code.kaptcha.Constants;
import com.rundatop.core.utils.NetworkUtil;
import com.rundatop.security.base.auth.CheckCodeAuthenticationToken;
import com.rundatop.security.base.exception.CheckCodeException;

public class CheckCodeUsernamePasswordFilter extends UsernamePasswordAuthenticationFilter{
	public final static String CODE="verifyCode";
@Override
public Authentication attemptAuthentication(HttpServletRequest request,
		HttpServletResponse response) throws AuthenticationException {
	String username=obtainUsername(request);
	String password=obtainPassword(request);
	String code=request.getParameter(CheckCodeUsernamePasswordFilter.CODE);
	String requestURI=request.getRequestURI();
	request.setAttribute(com.rundatop.security.base.Constants.LOGIN_REQUEST_URI, requestURI);
	String session_code=(String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
	if(!StringUtils.equals(code, session_code)){
		throw new CheckCodeException("验证码不正确!");
	}
	
	String curIp="";
	try {
		curIp = NetworkUtil.getIpAddress(request);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	CheckCodeAuthenticationToken authRequest=new CheckCodeAuthenticationToken(username,password,code,curIp);
	setDetails(request, authRequest);
	return this.getAuthenticationManager().authenticate(authRequest);
}
}
