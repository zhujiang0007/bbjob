package com.rundatop.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns="*.action")
public class SpCharFilter implements Filter{
	
	
	public void destroy() {
		
	}

	@SuppressWarnings("unchecked")     
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		SpCharRequestWrapper req = new SpCharRequestWrapper((HttpServletRequest) request);
		chain.doFilter(req, response); 
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
