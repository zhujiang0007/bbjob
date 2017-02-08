package com.rundatop.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;


/**
 * spring上下文容器
 * @author zj
 * @date   2017年2月8日
 *
 */
public class ApplicationContext implements ApplicationContextAware {

	private static org.springframework.context.ApplicationContext context;

	

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	public static String[] getBeanNamesForType(Class<?> clazz) {
		return context.getBeanNamesForType(clazz);
	}

	public void setApplicationContext(
			org.springframework.context.ApplicationContext context)
			throws BeansException {
		this.context=context;
		
	}
}