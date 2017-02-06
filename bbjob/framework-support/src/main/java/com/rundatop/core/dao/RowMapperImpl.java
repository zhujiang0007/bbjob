package com.rundatop.core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rundatop.core.exception.BizRuntimeException;

public class RowMapperImpl implements org.springframework.jdbc.core.RowMapper {
	private static final Log log = LogFactory.getLog(RowMapperImpl.class);

	private  RowMapper rm;

	public RowMapperImpl( RowMapper rm) {
		this.rm = rm;
	}

	public Object mapRow(final ResultSet rs, int i) throws SQLException {
		return rm.mapRow(new RsRow() {
			public Object getValue(final String key) {

				try {
					return rs.getObject(key);
				} catch (SQLException e) {
					log.error(e);
					throw new BizRuntimeException(e);
				}
			}
		}, i);
	}

}
