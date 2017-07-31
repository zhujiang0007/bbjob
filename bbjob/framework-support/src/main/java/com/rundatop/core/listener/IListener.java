package com.rundatop.core.listener;

public abstract interface IListener<T> {
  public void beforeAdd(T entity);
  public void afterAdd(T entity);
  public void beforeEdit(T entity);
  public void afterEdit(T entity);
  public void beforeDelete(T entity);
  public void afterDelete(T entity);
}
