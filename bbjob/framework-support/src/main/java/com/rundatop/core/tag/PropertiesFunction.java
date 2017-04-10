package com.rundatop.core.tag;

import com.rundatop.core.spring.ApplicationContext;
import com.rundatop.core.spring.config.ExtendedPropertyPlaceholderConfigurer;

public class PropertiesFunction {
	public static String getProperty(String key){	
		ExtendedPropertyPlaceholderConfigurer propertyConfigurer=(ExtendedPropertyPlaceholderConfigurer) ApplicationContext.getBean("propertyConfigurer");
		return propertyConfigurer.getProperty(key).toString();
	}
}
