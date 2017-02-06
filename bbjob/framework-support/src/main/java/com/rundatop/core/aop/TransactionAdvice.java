package com.rundatop.core.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rundatop.core.dao.transaction.TransactionManager;

/**
 * 类功能说明：事务切面拦截器，负责服务层的数据库事务的管理
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
public class TransactionAdvice implements MethodInterceptor {
	private static final Log log = LogFactory.getLog(TransactionAdvice.class);
	private TransactionManager transactionManager;

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	
	public Object invoke(MethodInvocation method) throws Throwable {
		Object[] reqs = method.getArguments();
		log.debug("调用方法:"+method.getThis().getClass().toString()+"()."+method.getMethod().getName());
		transactionManager.begin();
		try {
			Object o = method.proceed();		
			transactionManager.commit();
			return o;
		} catch (Exception e) {
			
			//todo
			//log.error(Utils.getFullErrorMessage(e));
			transactionManager.rollback();
			throw e;
		} finally {
			//	context.remove(transactionManager);


		}
	}
}
