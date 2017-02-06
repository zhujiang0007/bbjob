package com.rundatop.core.spring.mvc.base;

import javax.servlet.http.HttpServletRequest;

/********
 * 接口权限管理类
 * 
 * @author yxw
 * @date 2015年8月27日16:08:37
 * @version v1.0
 * 
 * */

public abstract class AbstractInterfaceController extends AbstractController  {
	
	/**
	 * method:是否有权限
	 * 判断是否有权限
	 * */
	public boolean ishasAuthority(HttpServletRequest request){
		//获取请求的路径
		System.out.println("getServletPath:"+request.getServletPath());
		//TODO 做权限验证
		String cateCode = request.getParameter("cateCode");
		if(null==cateCode||cateCode==""){
			return false;
		}
		return true;
	}
}
