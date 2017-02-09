package com.rundatop.core.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rundatop.core.utils.ExceptionUtils;



/**
 * 业务运行时异常类
 * @author zj
 * @date   2017年2月8日
 *
 */
public class BizRuntimeException extends RuntimeException {
	private static final Log log = LogFactory.getLog(BizRuntimeException.class);
	public String errorCode;

	public BizRuntimeException() {
		super();
	}

	public BizRuntimeException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		log.error(ExceptionUtils.getFullErrorMessage(cause));
	}

	public BizRuntimeException(String message, Throwable cause) {
		super(message, cause);
		log.error(ExceptionUtils.getFullErrorMessage(cause));
	}

	public BizRuntimeException(String message) {
		super(message);
	}

	public BizRuntimeException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BizRuntimeException(Throwable cause) {
		super(cause);
		log.error(ExceptionUtils.getFullErrorMessage(cause));
	}
}
