package com.rundatop.core.dao.transaction;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.rundatop.core.context.ThreadLocalManager;
import com.rundatop.core.exception.BizRuntimeException;

/**
 * 类功能说明：框架统一数据库事务管理类，通过该类与框架事务拦截器一起协调来管理数据库事务
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
public class TransactionManagerImpl implements TransactionManager {
	private static final Logger log =LoggerFactory.getLogger(TransactionManagerImpl.class);

	private PlatformTransactionManager transactionManager;

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}


	public void commit() {
		if (isTransaction()) {
			TransactionStatus status = (TransactionStatus) ThreadLocalManager
					.getContext().context.get(transactionManager);
			try {
				transactionManager.commit(status);
				ThreadLocalManager.getContext().transcation_flag = false;
			} catch (DuplicateKeyException e) {
				log.error("",e);
				throw new BizRuntimeException("主键冲突!" + e.getMessage());
			}

		}

		
	}


	public void rollback() {
		if (isTransaction()) {
			TransactionStatus status = (TransactionStatus) ThreadLocalManager
					.getContext().context.get(transactionManager);
			try {
				if (!status.isCompleted())
					transactionManager.rollback(status);
			} finally {
				ThreadLocalManager.getContext().transcation_flag = false;
			}
		}
	}


	public void begin() {

		if (!isTransaction()) {
//			if (ThreadLocalManager.getContext().context
//					.get(TransactionAdvice.class) == null) {
//				log.error(ThreadLocalManager.getContext().context);
//				throw new BizRuntimeException("方法调用未经过事务拦截器，禁止开启数据库事务！");
//			}
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			// 开始数据库事务
			TransactionStatus status = transactionManager.getTransaction(def);
			ThreadLocalManager.getContext().transcation_flag = true;
			ThreadLocalManager.getContext().context.put(transactionManager,
					status);
		}

	}


	public boolean isTransaction() {
		return ThreadLocalManager.getContext().transcation_flag;
	}
}
