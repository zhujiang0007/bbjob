package com.rundatop.core.common;

import java.util.Collection;

public class PageDataResult<E> {
	
	public final static String STAE_CODE_SUCCESS = "200"; // 表示操作成功
	public final static String STAE_CODE_ERROR = "300"; // 表示操作失败
	public final static String STAE_CODE_OUTTIME = "301"; // 表示超时
	private String statusCode; 
	private String message; // 提示框中的提示信息
    private long total;
    
    private Collection<E> rows;
	
	private Collection<E> footer;
	
	public PageDataResult(String code,String msg){
		this.statusCode = code;
		this.message = msg;
	}
	
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Collection<E> getRows() {
		return rows;
	}

	public void setRows(Collection<E> rows) {
		this.rows = rows;
	}

	public Collection<E> getFooter() {
		return footer;
	}

	public void setFooter(Collection<E> footer) {
		this.footer = footer;
	}

	
	
	
	
}
