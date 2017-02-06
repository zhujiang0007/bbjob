package com.rundatop.core.cache;

/**
 * <ul>
 * <li>例子---siteapp_server:siteapp:id/{version}</li>
 * <li>cacheKey中的版本部分</li>
 * <li>用于批量删除</li>
 * <li></li>
 * </ul>
 * 
 * @author js
 *
 */
public abstract class CacheKeyVersionProvider {

	public static long version = System.currentTimeMillis();

	/**
	 * 生成cacheKey中的版本部分
	 * 
	 * @return
	 */
	public abstract String cacheKeyVersion();

	/**
	 * <ul>
	 * <li>升级版本号(cacheKey中的版本部分改变)</li>
	 * <li>使缓存中的数据,批量失效</li>
	 * </ul>
	 * 
	 * @return
	 */
	public abstract String refreshVersion();

}
