package com.rundatop.core.context;

/**
 * 类功能说明 请求服务VO基类
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
 * @date 2014-5-6 上午9:11:18
 * @version V1.0
 */
public class ServiceBaseVO {
	/**
	 * 返回码
	 */
	public String rtnCode;
	/**
	 * 返回信息
	 */
	public String rtnMsg;
	/**
	 * 数据源对应KEY，用于服务切换数据源
	 */
	public String $$DS_KEY;
}
