package com.rundatop.security.base.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	private ActionHandler[] handler;
 public ActionHandler[] getHandler() {
		return handler;
	}
	public void setHandler(ActionHandler[] handler) {
		this.handler = handler;
	}
@Override
public void onAuthenticationSuccess(HttpServletRequest request,
		HttpServletResponse response, Authentication authentication)
		throws ServletException, IOException {
	 if(handler!=null&&handler.length>0){
		 for(ActionHandler ac:handler){
			 ac.handle(request, authentication);
		 }
	 } 
	 super.onAuthenticationSuccess(request, response, authentication);
}
}
