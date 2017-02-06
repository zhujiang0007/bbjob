package com.rundatop.core.dao.transaction;

public interface TransactionManager {

	public void commit();

	public void rollback();

	public void begin();

	public boolean isTransaction();

}
