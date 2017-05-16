package com.rundatop.authority.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rundatop.security.base.Constants;
@Controller
@RequestMapping("loginfailure")
public class LoginfailureController {
	@RequestMapping
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response){
		String requestURI=request.getAttribute(Constants.LOGIN_REQUEST_URI).toString();
		ModelAndView modelView=new ModelAndView();
		AuthenticationException e=(AuthenticationException) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		String errInfo= e.getMessage();
		modelView.addObject("errInfo", errInfo);
		if(requestURI.indexOf("sys")>-1){
			modelView.setViewName("sys/login");
		}
		else
		modelView.setViewName("web/login");
		return modelView;
	}
}
