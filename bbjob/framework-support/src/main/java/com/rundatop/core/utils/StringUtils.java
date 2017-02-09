package com.rundatop.core.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

import com.rundatop.core.exception.BizRuntimeException;
/**
 * 字符串处理工具
 * @author zj
 * @date   2017年2月8日
 *
 */
public class StringUtils {
	/**
	 * 数字补0
	 * @param num
	 * @param formatter
	 * @return
	 */
	public static String getFormatterNumber(Integer num, String formatter) {
		DecimalFormat df = new DecimalFormat(formatter);
		return df.format(num);
	}
	/**
	 * 将普通字符串中的特殊字符串替换成XML可解析的形式
	 * 
	 * @param xml
	 * @return
	 */
	public static String NORMAL2XML(String xml) {
		return xml.replace("&", "&amp;").replace("<", "&lt;")
				.replace(">", "&gt;");
	}
	/**
	 * 输入流转字符串
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream is) {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			throw new BizRuntimeException(e);
		}
		return buffer.toString();
	}

	/**
	 * 读取指定文件
	 * 
	 * @param tmpFile
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String tmpFile) {
		StringBuffer sbFile;
		FileReader in;
		try {
			in = new FileReader(tmpFile);

			char[] buffer = new char[4096];
			int len;
			sbFile = new StringBuffer();
			while ((len = in.read(buffer)) != -1) {
				String s = new String(buffer, 0, len);
				sbFile.append(s);
			}
		} catch (FileNotFoundException e) {
			throw new BizRuntimeException(e);
		} catch (IOException e) {
			throw new BizRuntimeException(e);
		}
		return sbFile.toString();
	}
	/**
	 * 取固定长度的字符串
	 * 
	 * @param resource
	 *            原串
	 * @param length
	 *            长度
	 * @return
	 */

	public static String fixedLengthStringNull(String resource, int length) {
		if (resource.length() < length)
			return resource;

		return resource.subSequence(0, length) + "";
	}

	public static String fixedLengthString(String resource, int length) {
		return fixedLengthStringNull(resource, length) + "...";
	}

	public static String replaceToNull(String resource, String[] replace) {
		if (null == replace)
			return resource;
		String temp = resource;
		for (int i = 0; i < replace.length; i++) {
			temp = temp.replaceAll(replace[i], " ");
		}
		return temp;
	}
	/**
	 * byte转换为MB
	 * @param size
	 * @return
	 */
	public static String getSoftsize(long size) {
		float f = 0f;
		String tmp = "";
		if (size > 1024 * 1024) {
			f = (float) size / 1024 / 1024;
			tmp = "MB";
		} else if (size > 1024) {
			f = (float) size / 1024;
			tmp = "KB";
		} else {
			f = (float) size;
			tmp = "B";
		}

		java.text.DecimalFormat df = new DecimalFormat(".##");
		tmp = (String) df.format(f) + tmp;
		return tmp;
	}
	/**
	 * 获取字符串中的文字和数字
	 * @param str
	 * @return
	 */
	public static String[] StringNumber(String str) {
		int length = str.length();
		char v = 0;
		StringBuffer string = new StringBuffer();
		StringBuffer number = new StringBuffer();
		for (int i = 0; i < length; i++) {
			v = str.charAt(i);
			if (v < 49 || v > 57)
				string.append(v);
			else
				number.append(v);
		}
		return new String[] { string.toString(), number.toString() };
	}
	/**
	 * 转换为utf8字符串
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					// System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	/**
	 * 格式化日期，如果不是两位，拼‘0’
	 */
	public static String getTwoDate(int rq) {
		String temp = "" + rq;
		if (rq > 0 && rq < 10)
			temp = "0" + rq;
		return temp;
	}
	/**
	 * 数组连接
	 * @param separator
	 * @param list
	 * @return
	 */
	public static String join(String separator, String[] list) {
		if (list == null)
			list = new String[0];
		StringBuffer csv = new StringBuffer();
		for (int i = 0; i < list.length; i++) {
			if (i > 0) {
				csv.append(separator);
			}
			csv.append(list[i]);
		}
		return csv.toString();
	}
	/**
	 * 集合连接
	 * @param separator
	 * @param list
	 * @return
	 */
	public static String join(String separator, Collection<String> list) {
		StringBuffer csv = new StringBuffer();
		Iterator it = list.iterator();
		for (int i = 0; it.hasNext(); i++) {
			if (i > 0) {
				csv.append(separator);
			}
			csv.append((String) it.next());
		}
		return csv.toString();
	}
	

}
