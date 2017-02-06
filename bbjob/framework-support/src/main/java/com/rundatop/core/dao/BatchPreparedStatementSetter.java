package com.rundatop.core.dao;
/**
 * 类功能说明： 批量执行SQL赋参数接口
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
public interface BatchPreparedStatementSetter {
	public abstract void setValues(PreparedStatement ps, int index);

	public abstract int getBatchSize();
}
