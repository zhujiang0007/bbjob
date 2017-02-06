package com.rundatop.core.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rundatop.core.utils.Utils;



/**
 * 类功能说明:业务非检查异常类
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
 * @date 2014-4-18 下午1:28:34
 * @version V1.0
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
		log.error(Utils.getFullErrorMessage(cause));
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
		log.error(Utils.getFullErrorMessage(cause));
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
		log.error(Utils.getFullErrorMessage(cause));
	}
}