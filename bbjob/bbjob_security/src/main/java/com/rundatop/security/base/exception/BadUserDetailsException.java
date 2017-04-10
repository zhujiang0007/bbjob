package com.rundatop.security.base.exception;

import org.springframework.security.core.AuthenticationException;
/**
 *
 * @author zj
 *
 */
public class BadUserDetailsException extends AuthenticationException{

	public BadUserDetailsException(String msg) {
		super(msg);
	}
	private static final long serialVersionUID = -5102165270356446009L;

}
