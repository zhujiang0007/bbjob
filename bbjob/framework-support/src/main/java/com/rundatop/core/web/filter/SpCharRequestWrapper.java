package com.rundatop.core.web.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

import com.rundatop.core.utils.HtmlRegexpUtil;

public class SpCharRequestWrapper extends HttpServletRequestWrapper{

	public SpCharRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String[] getParameterValues(String name) {
		String filterHtml = this.getParameter("filterHtml");
		if("1".equals(filterHtml)){
			return super.getParameterValues(name);
		}
		
		String[] values = super.getParameterValues(name);
		
		if(null == values || values.length<=0) return null;
		
		for (int i=0;i<values.length;i++) {
			values[i] = StringUtils.trimToNull(values[i]);
			if(null != values[i]){
				values[i] = HtmlRegexpUtil.replaceTag(values[i]);
			}
		}
		return values;
	}

}
