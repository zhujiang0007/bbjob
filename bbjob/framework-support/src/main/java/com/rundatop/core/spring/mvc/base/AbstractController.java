package com.rundatop.core.spring.mvc.base;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractController  {
	

	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(AbstractController.class);
	}
	
	

	public void setDownloadResponseHeader(HttpServletResponse response,
			String filename) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		try {
			filename = new String(filename.getBytes("GBK"), "ISO8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			getLogger().error("setDownloadResponseHeader",e1);
		}

		response.setHeader("Content-disposition", "attachment; filename="
				+ filename);
	}
	

	
	public String getContextRealPath(HttpSession session){
		return session.getServletContext().getRealPath("/");
	}
	
	public String getContextRealPath(HttpServletRequest request){
		return request.getSession().getServletContext().getRealPath("/");
	}
}
