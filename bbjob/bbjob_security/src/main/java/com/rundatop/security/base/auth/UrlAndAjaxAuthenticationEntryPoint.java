package com.rundatop.security.base.auth;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UrlAndAjaxAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint{
	 public UrlAndAjaxAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
		// TODO Auto-generated constructor stub
	}
	private Logger logger=org.slf4j.LoggerFactory.getLogger(UrlAndAjaxAuthenticationEntryPoint.class);
	 @Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		String url = request.getRequestURI();
		if (logger.isDebugEnabled()) {  
            logger.debug("url:" + url);  
        }
		if (url.indexOf("ajax") == -1){
			super.commence(request, response, authException);
		}else{  
            // ajax请求，返回json，替代redirect到login page  
            if (logger.isDebugEnabled()) {  
                logger.debug("ajax request or post");  
            }  
  
            ObjectMapper objectMapper = new ObjectMapper();  
            response.setHeader("Content-Type", "application/json;charset=UTF-8");  
            JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(),  
                    JsonEncoding.UTF8);  
            try {  
            	HashMap<String, String> map=new HashMap<String, String>();
    			map.put("success", "2");
                objectMapper.writeValue(jsonGenerator, map);  
            } catch (JsonProcessingException ex) {  
                throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);  
            }  
        }  
		
	}
}
