package com.rundatop.core.web.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
/**
 * 注解注册org.springframework.web.servlet.DispatcherServlet
 * @author Administrator
 *
 */
@WebServlet(urlPatterns={"*.action","*.htm"},
			loadOnStartup=1,
initParams={@WebInitParam(name = "contextConfigLocation", value = "classpath*:/META-INF/spring/*-springmvc-config.xml")})
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
