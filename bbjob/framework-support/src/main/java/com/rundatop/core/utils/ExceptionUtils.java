package com.rundatop.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常处理类
 * @author zj
 * @date   2017年2月9日
 *
 */
public class ExceptionUtils {
	private static final Logger log = LoggerFactory.getLogger(ExceptionUtils.class);

	
	/**
	 * 获取异常的全部信息
	 * 
	 * @param e
	 *            异常对象
	 * @return
	 */
	public static String getFullErrorMessage(Throwable e) {
		StringBuffer buffer = new StringBuffer();
		StackTraceElement[] stacktrace = e.getStackTrace();
		buffer.append("Caused by: " + e + "\n");
		for (StackTraceElement tmp : stacktrace) {
			buffer.append("\tat " + tmp.toString() + "\n");
		}
		return buffer.toString();
	}

	

	

	
		
	

	

	

	

	

	

	

	

	


	
	

	

	

	
	
	
	
	
	
}
