package com.rundatop.core.common;

import java.io.Serializable;
import java.util.Collection;

public class PageData<E> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 791059706769444428L;

	private long total;
	
	private Collection<E> rows;
	
	private Collection<E> footer;

	public Collection<E> getFooter() {
		return footer;
	}

	public void setFooter(Collection<E> footer) {
		this.footer = footer;
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
}
