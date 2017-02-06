package com.rundatop.core.dao;


/**
 * 类功能说明 数据库与应用字符集转换配置
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
 * @date 2014-4-18 下午2:14:28
 * @version V1.0
 */
public class CharSetConstant {
	public static String localCharset;
	public static String dbCharset;

	public void setLocalCharset(String localCharset) {
		CharSetConstant.localCharset = localCharset;
	}

	public void setDbCharset(String dbCharset) {
		CharSetConstant.dbCharset = dbCharset;
	}

	public static Object charsetLocalAdapter(Object value) {
		if (value == null) {
			return value;
		}
		Object oret = value;
		if (((value instanceof String)) && (!localCharset.equals(dbCharset))) {
			try {
				oret = StringEx.convertCharSet((String) value, dbCharset,
						localCharset);
			} catch (Exception e) {
				throw new RuntimeException("字符集转换错误:" + e);
			}
		}
		return oret;
	}

	public static Object charsetDbAdapter(Object value) {
		if (value == null) {
			return value;
		}
		Object oret = value;
		if ((value instanceof String)) {

			if (!localCharset.equals(dbCharset)) {
				try {
					oret = StringEx.convertCharSet((String) value,
							localCharset, dbCharset);
				} catch (Exception e) {
					throw new RuntimeException("字符集转换错误:" + e);
				}
			}
		}

		return oret;
	}

	/**
	 * Clob对象转换成String
	 * 
	 * @param in
	 * @return
	 */
	public static Object clobToString(Object in) {
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
//				return charsetLocalAdapter(rtn);
//			} else {
				if (in instanceof String) {
					return charsetLocalAdapter((String) in);
				}
				return in;
//			}
		} catch (Exception e) {
			return in;
		}

	}
}
