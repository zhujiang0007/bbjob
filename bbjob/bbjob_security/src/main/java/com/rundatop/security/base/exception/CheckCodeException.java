package com.rundatop.security.base.exception;

import org.springframework.security.core.AuthenticationException;
/**
 *
 * @author zj
 *
 */
public class CheckCodeException extends AuthenticationException{

	public CheckCodeException(String msg) {
		super(msg);
	}
	private static final long serialVersionUID = -5102165270356446009L;

}
