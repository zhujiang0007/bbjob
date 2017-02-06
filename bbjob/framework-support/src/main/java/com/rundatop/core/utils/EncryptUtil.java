/*
 * http://www.zljysoft.com
 * File Name：EncryptUtil.java
 * Comments: 支持SHA/MD5消息摘要的工具类
 * Create Date: 2013-4-10 上午10:04:41
 * Modified By: 
 * Modified Date: 
 * Why & What is modified: 
 * version: V1.0 
 */
package com.rundatop.core.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 支持SHA/MD5消息摘要的工具类
 * @date 2013-4-10 上午10:04:41 
 * @version V1.0
 * 
 */
public class EncryptUtil {
	/**
	 * 返回MD5码
	 * @param parStr 要编码的字符串
	 * @return 返回值
	 */
	public static String getMD5Code(String parStr) {
		return DigestUtils.md5Hex(parStr);
	}
	
	/**
	 * 返回字节数组MD5码
	 * 例如:可用于返回文件的校验码
	 * @param parByteArray 字节数组
	 * @return 返回值
	 */
	public static String getMD5Code(byte[] parByteArray) {
		return DigestUtils.md5Hex(parByteArray);
	}
	
	/**
	 * 返回SHA码
	 * @param parStr 要编码的字符串
	 * @return 返回值
	 */
	public static String getSHACode(String parStr) {
		return DigestUtils.shaHex(parStr);
	}
	
	/**
	 * 返回字节数组SHA码
	 * 例如:可用于返回文件的校验码
	 * @param parByteArray 字节数组
	 * @return 返回值
	 */
	public static String getSHACode(byte[] parByteArray) {
		return DigestUtils.shaHex(parByteArray);
	}
	
}
