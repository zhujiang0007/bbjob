package com.rundatop.core.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rundatop.core.config.SystemConfig;



/**
 * 系统启动时加载一些公共参数
 * @date 2013-4-11 上午8:23:39
 * @version V1.0
 * 
 */
@WebListener()
public class InitParamListener implements HttpSessionListener,
		ServletContextListener, ServletContextAttributeListener {
	private ServletContext context = null;
	private WebApplicationContext webContext = null;

	
	public void sessionCreated(HttpSessionEvent se) {

	}

	
	public void sessionDestroyed(HttpSessionEvent se) {

	}

	
	public void attributeAdded(ServletContextAttributeEvent arg0) {

	}

	
	public void attributeRemoved(ServletContextAttributeEvent arg0) {

	}

	
	public void attributeReplaced(ServletContextAttributeEvent arg0) {

	}

	
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	// context初始化时激发
	
	public void contextInitialized(ServletContextEvent event) {
		webContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		this.context = event.getServletContext();
		SystemConfig.ctx=this.context.getContextPath()+"/";
	}
}
