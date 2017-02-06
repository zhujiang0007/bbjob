package com.rundatop.core.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * 类功能说明 ThreadLocal管理器，负责管理工作线程接入后的所有线程本地变量
 * <p>
 * 类修改者
 * <p>
 * 修改日期
 * <p>
 * 修改说明
 * <p>
 * Copyright: Copyright (c) 2014
 * <p>
 * Company:广联达软件股份有限公司
 * 
 * @author pus-a
 * @date 2014-5-6 上午8:28:43
 * @version V1.0
 */
public class ThreadLocalManager {
	private static ThreadLocal<Context> context = new ThreadLocal<Context>();
	private static ThreadLocal<HttpSession> session = new ThreadLocal<HttpSession>();
	private static ThreadLocal<HttpServletRequest> servletReq = new ThreadLocal<HttpServletRequest>();
	public static HttpSessionContext sessioncontext = null;

	/**
	 * 返回当前工作线程上下文
	 * 
	 * @return
	 */
	public static Context getContext() {
		if (context.get() == null)
			context.set(new Context());
		return context.get();
	}

	public static void clear() {
		context.remove();
		session.remove();
		servletReq.remove();
	}

	/**
	 * 将当前HTTP请求的Session放入线程本地变量中
	 * 
	 * @param httpsession
	 */
	public static void setSession(HttpSession httpsession) {
		if (sessioncontext == null) {
			sessioncontext = httpsession.getSessionContext();
		}
		session.set(httpsession);
	}

	/**
	 * 获取当前HTTP的Session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		return session.get();
	}

	/**
	 * 设置当前工作线程上下文
	 * 
	 * @return
	 */
	public static void setContext(Context c) {
		context.set(c);
	}

	/**
	 * @return the servletReq
	 */
	public static HttpServletRequest getServletReq() {
		return servletReq.get();
	}

	/**
	 * @param servletReq
	 *            the servletReq to set
	 */
	public static void setServletReq(HttpServletRequest req) {
		servletReq.set(req);
	}

}
