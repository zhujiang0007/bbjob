package com.rundatop.core.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.rundatop.core.spring.ApplicationContext;

/**
 * 类功能说明：服务日志拦截器，负责服务层的输入输出报文日志
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
 * @date 2014-4-18 下午1:28:34
 * @version V1.0
 */
public class ServiceLogAdvice implements MethodInterceptor {
	private static final Logger log=LoggerFactory.getLogger(ServiceLogAdvice.class);

	public Object invoke(final MethodInvocation method) throws Throwable {
		Object target = method.getThis();
		Object[] params = method.getArguments();
		long begin = 0;
		long end = 0;
		if (log.isInfoEnabled()) {
			begin = System.currentTimeMillis();
		}
		Object ret = null;
		try {
			ret = method.proceed();
			return ret;
		} catch (Throwable e) {
			afterReturning("调用失败!", method.getMethod(), params, target, -1);
			throw e;
		} finally {
//			if (log.isInfoEnabled()) {
//				if ((target instanceof FileBatchUploadService || target instanceof FileUploadService)
//						&& method.getMethod().getName().equals("process")) {
//
//				} else {
					end = System.currentTimeMillis();
					long cost = end - begin;
					afterReturning(ret, method.getMethod(), params, target,
							cost);
//				}
			}
//	}

	}

	public void afterReturning(Object ret, Method mehtod, Object[] params,
			Object obj, long cost) {
//		if (log.isDebugEnabled()) {
//			try {
//				String SID = null;
//				org.springframework.stereotype.Service s = obj.getClass()
//						.getAnnotation(
//								org.springframework.stereotype.Service.class);
//				if (s != null) {
//					SID = s.value();
//				} else {
//					String[] names = ApplicationContext.getBeanNamesForType(obj
//							.getClass());
//					for (String name : names) {
//						if (obj.toString().equals(
//								ApplicationContext.getBean(name).toString())) {
//							SID = name;
//							break;
//						} else {
//							log.warn("未找到类型为:" + obj.getClass().getName()
//									+ "，对应的服务ID!");
//							SID = "未知ID";
//						}
//					}
//				}
//				Class clazz = obj.getClass();
//				StringBuffer logStr = new StringBuffer();
//				logStr.append(" \n 调用服务:" + SID + "， 类：" + clazz.getName()
//						+ "的实例方法：" + mehtod.getName() + "");
//				if (params.length > 0) {
//					List list = Arrays.asList(params);
//					if (list.size() == 1 && list.get(0) instanceof CommContext) {
//						logStr.append(" \n 输入参数为：" + list.get(0));
//					} else if (list.size() == 1
//							&& list.get(0) instanceof ServiceBaseVO) {
//						logStr.append(" \n 输入参数为："
//								+ JsonParser.pojotoJson((ServiceBaseVO) list
//										.get(0)));
//
//					} else {
//						logStr.append(" \n 输入参数为："
//								+ JsonParser.pojotoJson(list));
//					}
//
//				} else {
//					logStr.append(" \n 该方法无输入参数。" + " \n 总耗时：" + cost + "毫秒");
//				}
//
//				if (ret instanceof CommContext) {
//
//					logStr.append(" \n 返回参数为：" + ret + " \n 总耗时：" + cost + "毫秒");
//				} else if (ret instanceof ServiceBaseVO) {
//					logStr.append(" \n 返回参数为：" + JsonParser.pojotoJson(ret)
//							+ " \n 总耗时：" + cost + "毫秒");
//				} else {
//					HashMap tmp = new HashMap();
//					tmp.put("ret", ret);
//					logStr.append(" \n 返回参数为：" + JsonParser.pojotoJson(tmp)
//							+ " \n 总耗时：" + cost + "毫秒");
//				}
//				if (logStr.length() > 0) {
//					log.debug(logStr.toString());
//				}
//			} catch (Throwable e) {
//				log.warn(" \n 调用  类：" + obj.getClass().getName() + "的实例方法："
//						+ mehtod.getName() + "记录日志失败，错误原因："
//						+ Utils.getFullErrorMessage(e));
//			}

//		}
	}
}