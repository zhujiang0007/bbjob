package com.rundatop.security.base.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.rundatop.core.json.JsonParser;



public class BaseRequestBodyAuthenticationFilter extends AbstractAuthenticationProcessingFilter{
	private Logger logger=LoggerFactory.getLogger(BaseRequestBodyAuthenticationFilter.class);
	public BaseRequestBodyAuthenticationFilter() {
		super("/ajaxLogin");
		// TODO Auto-generated constructor stub
	}
	private String accountKey="account";
	private String passwordKey="password";
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		Map<String,String> map=obtionRequestBody2Map(request);
		String account=map.get(accountKey);
		String password=map.get(passwordKey);
		UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(account,password);
		setDetails(request,authToken);
		return this.getAuthenticationManager().authenticate(authToken);
	}
	 protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
	        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	    }
	public Map<String,String> obtionRequestBody2Map(HttpServletRequest request){
		HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
		try {
			InputStream in= inputMessage.getBody();	
			JsonParser parser=new JsonParser();
			return parser.getMapFromInputStream(in);
			
		} catch (IOException e) {
			logger.error("",e);
		}
		
	
		return null;
	}
}
