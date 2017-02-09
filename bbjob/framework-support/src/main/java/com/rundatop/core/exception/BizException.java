package com.rundatop.core.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rundatop.core.utils.ExceptionUtils;



/**
 * 业务异常类
 * @author zj
 * @date   2017年2月8日
 *
 */
public class BizException extends Exception {
	private static final Log log = LogFactory.getLog(BizException.class);
	public String errorCode;

	public BizException() {
		super();
	}

	public BizException(String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		log.error(ExceptionUtils.getFullErrorMessage(cause));
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
		log.error(ExceptionUtils.getFullErrorMessage(cause));
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BizException(Throwable cause) {
		super(cause);
		log.error(ExceptionUtils.getFullErrorMessage(cause));
	}
}
