package com.rundatop.security.base.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

public interface ActionHandler {
	public void handle(HttpServletRequest request,Authentication authentication);
}
