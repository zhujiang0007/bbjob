package com.rundatop.core.web.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.rundatop.core.spring.ApplicationContext;

public class DelegatingServcletProxy extends GenericServlet{
	@Autowired
	ApplicationContext applicationContext;
	private String targetBean;
	private Servlet proxy;
	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		proxy.service(req, res);
		
	}
	@Override
	public void init(ServletConfig config) throws ServletException {
		if(StringUtils.isEmpty(targetBean))
			targetBean=config.getServletName();
		initServlet();
	}
	private void initServlet() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		this.proxy=(Servlet) applicationContext.getBean(targetBean);
	}

}
