package com.rundatop.core.dao;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.rundatop.core.dao.transaction.TransactionManager;
import com.rundatop.core.exception.BizRuntimeException;

/**
 * 持久层通用操作类
 * 
 * @author pushi
 * 
 */

@Repository("jdbchelper")
public class DaoHelper {
	private Logger log=LoggerFactory.getLogger(DaoHelper.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Resource(name = "mainTransactionManager")
	private TransactionManager transactionManager;

	/**
	 * 将Clob类型的数据转成字符串，如果不是clob类型则返回原对象
	 * 
	 * @param in
	 * @return
	 */
	public Object clobToString(Object in) {
		if (in == null) {
			return null;
		} 
		try {
//			if ("oracle.sql.CLOB".equals(in.getClass().getName())) {
//				String rtn = "";
//				oracle.sql.CLOB clob = (oracle.sql.CLOB) in;
//				InputStream input = clob.getAsciiStream();
//				int len = (int) clob.length();
//				byte[] by = new byte[len];
//				int i;
//				while (-1 != (i = input.read(by, 0, by.length))) {
//					input.read(by, 0, i);
//				}
//				rtn = new String(by);
//				rtn = clob.getSubString((long) 1, (int) clob.length());
//
//				return charsetDB2Local(rtn);
//			} else if ("weblogic.jdbc.wrapper.Clob_oracle_sql_CLOB".equals(in
//					.getClass().getName())) {
//				String rtn = "";
//				Method method = in.getClass().getMethod("getVendorObj",
//						new Class[] {});
//				oracle.sql.CLOB clob = (oracle.sql.CLOB) method.invoke(in);
//				InputStream input = clob.getAsciiStream();
//				int len = (int) clob.length();
//				byte[] by = new byte[len];
//				int i;
//				while (-1 != (i = input.read(by, 0, by.length))) {
//					input.read(by, 0, i);
//				}
//				rtn = new String(by);
//				rtn = clob.getSubString((long) 1, (int) clob.length());
//
//				return charsetDB2Local(rtn);
//
//			} else {
				if (in instanceof String) {
					return charsetDB2Local((String) in);
				}
				return in;
//			}
		} catch (Exception e) {
			return in;
		}

	}

	public List query(String sql) {
		if (log.isDebugEnabled()) {
			log.debug("执行 sql:" + sql);
		}
		return this.query(sql, new Object[] {});
	}

	public List query(String sql, RowMapper rowMapper) {
		if (log.isDebugEnabled()) {
			log.debug("执行 sql:" + sql);
		}
		return jdbcTemplate.query(charsetLocal2DB(sql), new RowMapperImpl(
				rowMapper));

	}

	public List query(String sql, Object[] params) {
		if (log.isDebugEnabled()) {
			ArrayList logparams = new ArrayList();
			for (int i = 0; i < params.length; i++) {
				logparams.add(params[i]);
			}
			log.debug("执行 sql:" + sql + ",参数为：" + logparams);
		} 
		return jdbcTemplate.query(sql, params,
				new org.springframework.jdbc.core.RowMapper() {

					public Object mapRow(ResultSet rs, int index)
							throws SQLException {
						HashMap row = new HashMap();
						for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) { 
							row.put(rs.getMetaData().getColumnName(i),
									clobToString(rs.getObject(i)));
						}
						return row;
					}
				});

	}

	public List query(String sql, Object[] params, RowMapper rowMapper) {

		for (int i = 0; i < params.length; i++) {

			if (params[i] instanceof String) {
				params[i] = charsetLocal2DB((String) params[i]);
			}
		}
		if (log.isDebugEnabled()) {
			ArrayList logparams = new ArrayList();
			for (int i = 0; i < params.length; i++) {
				logparams.add(params[i]);
			}
			log.debug("执行 sql:" + sql + ",参数为：" + logparams);
		}
		return jdbcTemplate.query(charsetLocal2DB(sql), params,
				new RowMapperImpl(rowMapper));
	}

	public List query(String sql, int pageSize, int pageNumber) {
		// TODO Auto-generated method stub
		return this.query(formatSql(sql, pageSize, pageNumber), new Object[] {
				pageNumber * pageSize, (pageNumber - 1) * pageSize });
	}

	public List query(String sql, Object[] params, int pageSize, int pageNumber) {
		// TODO Auto-generated method stub
		params = ArrayUtils.add(params, pageNumber * pageSize);
		params = ArrayUtils.add(params, (pageNumber - 1) * pageSize);
		return this.query(formatSql(sql, pageSize, pageNumber), params);
	}

	public List query(String sql, RowMapper rowMapper, int pageSize,
			int pageNumber) {
		// TODO Auto-generated method stub
		Object[] params = new Object[] {};
		params = ArrayUtils.add(params, pageNumber * pageSize);
		params = ArrayUtils.add(params, (pageNumber - 1) * pageSize);
		return this.query(formatSql(sql, pageSize, pageNumber), params,
				rowMapper);
	}

	public List query(String sql, Object[] params, RowMapper rowMapper,
			int pageSize, int pageNumber) {
		params = ArrayUtils.add(params, pageNumber * pageSize);
		params = ArrayUtils.add(params, (pageNumber - 1) * pageSize);
		return this.query(formatSql(sql, pageSize, pageNumber), params,
				rowMapper);
	}

	/**
	 * 格式化sql 添加分页语句 时间：2013-4-25 上午11:15:50 作者：zhangyzg
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private String formatSql(String sql, int pageSize, int pageNumber) {
		StringBuffer str = new StringBuffer();
		str.append("SELECT * FROM (SELECT e$$.*, ROWNUM rn FROM (");
		str.append(sql);
		str.append(") e$$ where ROWNUM <= ?" + ") where rn > ?");
		return str.toString();
	}

	public int update(String sql) {
		beginTransaction();
		if (log.isDebugEnabled()) {
			log.debug("执行 sql:" + sql);
		}
		return jdbcTemplate.update(charsetLocal2DB(sql));
	}

	public int update(String tableName, String columnsStr, Object[] values) {
		beginTransaction();
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof String) {
				values[i] = charsetLocal2DB((String) values[i]);
			}
		}
		String[] columns = columnsStr.split(",");
		if (columns.length == 0) {
			throw new BizRuntimeException(
					"列数量为0，请检查代码！！，列应为\"column1,column2...\"的字符串");
		}
		if (columns.length != values.length) {
			throw new BizRuntimeException("传入列数和值的个数不符！columns:"
					+ columns.length + ",values:" + values.length);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("insert into " + tableName + "(" + columnsStr + ")values (");
		for (int i = 0; i < columns.length; i++) {
			sql.append("?");
			if (i != columns.length - 1) {
				sql.append(",");
			}
		}
		sql.append(")");
		return jdbcTemplate.update(charsetLocal2DB(sql.toString()), values);
	}

	public int update(String tableName, String columnsStr, String valuesStr) {
		valuesStr = charsetLocal2DB(valuesStr);
		String[] columns = columnsStr.split(",");
		String[] values = valuesStr.split(",");
		if (columns.length == 0) {
			throw new BizRuntimeException(
					"列数量为0，请检查代码！！，列应为\"column1,column2...\"的字符串");
		}
		if (columns.length != values.length) {
			throw new BizRuntimeException("传入列数和值的个数不符！columns:"
					+ columns.length + ",values:" + values.length);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("insert into " + tableName + "(" + columnsStr + ")values (");
		for (int i = 0; i < columns.length; i++) {
			sql.append("?");
			if (i != columns.length - 1) {
				sql.append(",");
			}
		}
		sql.append(")");
		return jdbcTemplate.update(charsetLocal2DB(sql.toString()), values);
	}

	public int update(String sql, Object[] params) {
		beginTransaction();
		for (int i = 0; i < params.length; i++) {
			if (params[i] instanceof String) {
				params[i] = charsetLocal2DB((String) params[i]);
			}
		}
		if (log.isDebugEnabled()) {
			ArrayList logparams = new ArrayList();
			for (int i = 0; i < params.length; i++) {
				logparams.add(params[i]);
			}
			log.debug("执行 sql:" + sql + ",参数为：" + logparams);
		}
		return jdbcTemplate.update(charsetLocal2DB(sql), params);
	}

	public void beginTransaction() {
		if (!this.transactionManager.isTransaction()) {
			this.transactionManager.begin();
		}
	}

	public String charsetDB2Local(String v) {
		// try {
		// return new String(v.getBytes(config.getDbCharset()), config
		// .getLocalCharset());
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// throw new RuntimeException(e);
		// }
		return (String) CharSetConstant.charsetLocalAdapter(v);

	}

	public String charsetLocal2DB(String v) {
		// try {
		// return new String(v.getBytes(config.getLocalCharset()), config
		// .getDbCharset());
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// throw new RuntimeException(e);
		// }
		return (String) CharSetConstant.charsetDbAdapter(v);
	}

	public int[] batchUpdate(final String sql,
			final BatchPreparedStatementSetter batchps) {
		beginTransaction();
		return jdbcTemplate
				.batchUpdate(
						sql,
						new org.springframework.jdbc.core.BatchPreparedStatementSetter() {

							

							public int getBatchSize() {
								return batchps.getBatchSize();
							}


							public void setValues(
									final java.sql.PreparedStatement ps, int index)
									throws SQLException {
								batchps.setValues(new PreparedStatement() {
									
									public void setValue(int i, Object v) {
										try {
											fillParam(ps, i, v);
										} catch (SQLException e) {
											log.error("",e);
											throw new BizRuntimeException(e);
										}
										
									}
								}, index);
								
							}

							
						});
	}

	private void fillParam(java.sql.PreparedStatement ps, int index, Object obj)
			throws SQLException {
		if (obj instanceof BigDecimal) {
			ps.setBigDecimal(index, (BigDecimal) obj);
		} else if (obj instanceof Boolean) {
			ps.setBoolean(index, (Boolean) obj);
		} else if (obj instanceof Byte) {
			ps.setByte(index, (Byte) obj);
		} else if (obj instanceof Date) {
			ps.setDate(index, (Date) obj);
		} else if (obj instanceof Double) {
			ps.setDouble(index, (Double) obj);
		} else if (obj instanceof Float) {
			ps.setFloat(index, (Float) obj);
		} else if (obj instanceof Integer) {
			ps.setInt(index, (Integer) obj);
		} else if (obj instanceof Long) {
			ps.setLong(index, (Long) obj);
		} else if (obj instanceof Short) {
			ps.setShort(index, (Short) obj);
		} else if (obj instanceof String) {
			ps.setString(index, (String) CharSetConstant.charsetDbAdapter(obj));
		} else if (obj instanceof Time) {
			ps.setTime(index, (Time) obj);
		} else if (obj instanceof Timestamp) {
			ps.setTimestamp(index, (Timestamp) obj);
		} else if (obj instanceof InputStream) {
			ps.setBinaryStream(index, (InputStream) obj);
		} else if (obj == null) {
			ps.setNull(index, java.sql.Types.NULL);
		}
	}

	public int getTotal(String sql, Object[] params) {
		String total = "SELECT count(1) as total from (" + sql + ")   as t";
		HashMap hash = (HashMap) this.query(total, params).get(0);
		return Integer.parseInt(hash.get("total").toString());
	}

	protected void commit() {
		if (this.transactionManager.isTransaction()) {
			this.transactionManager.commit();
		}
	}

	public TransactionManager getTransactionManager() {

		return this.transactionManager;
	}

	public int insert(String sql, Object[] params) {

		return this.update(sql, params);
	}

	public int delete(String sql, Object[] params) {

		return this.update(sql, params);
	}

	public int[] batchInsert(String sql, BatchPreparedStatementSetter batchps) {
		// TODO Auto-generated method stub
		return this.batchUpdate(sql, batchps);
	}

	public int[] batchDelete(String sql, BatchPreparedStatementSetter batchps) {
		// TODO Auto-generated method stub
		return this.batchUpdate(sql, batchps);
	}

	public int delete(String sql) {
		// TODO Auto-generated method stub
		return this.update(sql);
	}

	public int insert(String sql) {
		// TODO Auto-generated method stub
		return this.update(sql);
	}

	public void execute(String sql) {
		beginTransaction();
		jdbcTemplate.execute(sql);
	}
}
