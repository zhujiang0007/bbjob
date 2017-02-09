
package com.rundatop.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

import com.rundatop.core.exception.BizRuntimeException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
	/**
	 * 功能描述: <br>
	 * Base64编码
	 * 
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 */
	public static void encodeBase64(InputStream arg0, OutputStream arg1)
			throws IOException {
		new BASE64Encoder().encode(arg0, arg1);
	}

	/**
	 * 功能描述: <br>
	 * Base64解码
	 * 
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public static byte[] decodeBase64(String value) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buff = decoder.decodeBuffer(value);
		return buff;
	}

	/**
	 * 功能描述: <br>
	 * Base64解码
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] decodeBase64(InputStream input) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buff = decoder.decodeBuffer(input);
		return buff;
	}
	/**
	 * 功能描述: <br>
	 * Base64编码
	 * 
	 * @param data
	 * @return
	 */
	public static String encodeBase64(byte[] data) {
		return new BASE64Encoder().encode(data);
	}
	
	
	/**
	 * 计算数据字符串对应的MD5编码
	 */
	public static String getMD5(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
	
	/**
	 * 获取单个文件的MD5值！
	 * 
	 * @param file
	 * @return
	 */
	public static String getMD5(File file) {
		if (!file.isFile()) {
			return null;
		}

		FileInputStream in = null;
		StringBuffer result = new StringBuffer();
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line = null;
			while ((line = buf.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			throw new BizRuntimeException(e);
		}
		return getMD5(result.toString());

	}

	
}
