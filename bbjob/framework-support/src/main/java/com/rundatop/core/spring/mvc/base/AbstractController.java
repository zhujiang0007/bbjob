package com.rundatop.core.spring.mvc.base;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rundatop.core.common.Page;


public abstract class AbstractController  {
	protected static final String RESULT = "result";
	
	protected static final String ENTITY = "entity";
	
	protected static final String SESSION_USER = "sessionUser";

	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(AbstractController.class);
	}
	
	protected Page getPageBean(HttpServletRequest request){
		Page p = new Page();
		try {
			Integer page = Integer.parseInt(request.getParameter("page").toString().toString());
			Integer rows = Integer.parseInt(request.getParameter("rows").toString().toString());
			if(page<=0) page=1;
			if(rows<=0) rows=10;
			p.setPageNum(page);
			p.setNumPerPage(rows);
		} catch (Exception e) {
			p.setPageNum(1);
			p.setNumPerPage(10);
		}
		return p;
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
