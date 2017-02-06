package com.rundatop.core.context;

import java.util.HashMap;

/**
 * 类功能说明：工作线程上下文对象，保存了当前工作线程的事务状态标记与需要调用分布式系统的事务服务请求等与工作线程相关的状态。
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
public class Context {
	// 工作线程事务开启标记
	public boolean transcation_flag = false;
	 
	// 工作线程上下文容器
	public HashMap context = new HashMap();
	public String clientType = null;

}
