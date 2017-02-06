package com.rundatop.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;

/**
 * 类功能说明
 * <p>
 * 类修改者
 * <p>
 * 修改日期
 * <p>
 * 修改说明
 * <p>
 * Copyright: Copyright (c) 2014
 * <p>
 * Company:广联达软件股份有限公司
 * 
 * @author pus-a
 * @date 2014-4-18 下午1:51:29
 * @version V1.0
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