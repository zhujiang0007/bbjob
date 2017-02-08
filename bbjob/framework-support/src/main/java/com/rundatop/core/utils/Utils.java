package com.rundatop.core.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rundatop.core.exception.BizRuntimeException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 类功能说明：通用工具箱
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
public class Utils {
	private static final Logger log = LoggerFactory.getLogger(Utils.class);

	public static final String XML = "XML";
	public static final String JSON = "JSON";
	private static final String DES = "DES";
	public static final long HOUR = 3600000L;
	public static final long DAY = 86400000L;
	public static final String dateFormat = "yyyy.MM.dd HH:mm:ss";

	/**
	 * 避免Double精度丢失的乘法
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double mul(double... v) {
		BigDecimal b = new BigDecimal(Double.toString(1));
		for (double temp : v) {
			b = b.multiply(new BigDecimal(Double.toString(temp)));
		}
		return b.doubleValue();
	}

	/**
	 * 避免double精度丢失的加法运算
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double sum(double... v) {
		BigDecimal b = new BigDecimal(Double.toString(0));
		for (double temp : v) {
			b = b.add(new BigDecimal(Double.toString(temp)));
		}
		return b.doubleValue();
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

	/**
	 * 获取异常的全部信息
	 * 
	 * @param e
	 *            异常对象
	 * @return
	 */
	public static String getFullErrorMessage(Throwable e) {
		StringBuffer buffer = new StringBuffer();
		StackTraceElement[] stacktrace = e.getStackTrace();
		buffer.append("Caused by: " + e + "\n");
		for (StackTraceElement tmp : stacktrace) {
			buffer.append("\tat " + tmp.toString() + "\n");
		}
		return buffer.toString();
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
	 * 功能描述: <br>
	 * 对称加密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	/**
	 * 功能描述: <br>
	 * 对称解密
	 * 
	 * @param src
	 *            数据源
	 * @param key
	 *            密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	private static final String algorithmStr = "AES/ECB/PKCS5Padding";

	private static KeyGenerator keyGen;

	private static Cipher cipher;

	private static boolean isInited = false;

	// 初始化
	private static void init() {

		// 初始化keyGen
		try {
			keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(128);
			// 初始化cipher
			cipher = Cipher.getInstance(algorithmStr);
		} catch (Exception e) {
			throw new BizRuntimeException(e);
		}

		isInited = true;
	}

	private static byte[] GenKey() {
		if (!isInited)// 如果没有初始化过,则初始化
		{
			init();
		}
		return keyGen.generateKey().getEncoded();
	}

	// 加密
	public static String aesEncrypt(String sSrc, String sKey) {
		byte[] encryptedText = null;

		if (!isInited)// 为初始化
		{
			init();
		}
		try {
			Key key = new SecretKeySpec(sKey.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			encryptedText = cipher.doFinal(sSrc.getBytes());
		} catch (Exception e) {
			throw new BizRuntimeException(e);
		}

		String result = null;
		try {
			result = new String(encryptedText, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new BizRuntimeException(e);
		}
		return result;
	}

	// 解密
	public static String aesDecrypt(String sSrc, String sKey) throws Exception {
		byte[] originBytes = null;
		if (!isInited) {
			init();
		}
		Key key = new SecretKeySpec(sKey.getBytes(), "AES");
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 解密
			originBytes = cipher.doFinal(sSrc.getBytes());
		} catch (Exception e) {
			throw new BizRuntimeException(e);
		}
		String result = null;
		try {
			result = new String(originBytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new BizRuntimeException(e);
		}
		return result;
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

	public static String gzip(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return primStr;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					throw new BizRuntimeException(e);
				}
			}
		}
		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
	}

	/**
	 * 
	 * 使用gzip进行解压缩
	 * 
	 * @param compressedStr
	 * @return
	 */
	public static String gunzip(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = new sun.misc.BASE64Decoder()
					.decodeBuffer(compressedStr);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			throw new BizRuntimeException(e);
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
					throw new BizRuntimeException(e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new BizRuntimeException(e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw new BizRuntimeException(e);
				}
			}
		}
		return decompressed;
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
	 * 日期转换
	 * 
	 * @param yyyymmddhhmmss
	 *            时间字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date parseDate(String yyyymmddhhmmss, String format) {
		if (null == format) {
			throw new IllegalArgumentException("错误的日期格式");
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			ParsePosition pos = new ParsePosition(0);
			return formatter.parse(yyyymmddhhmmss, pos);
		} catch (Exception e) {
			throw new IllegalArgumentException("错误的日期:" + yyyymmddhhmmss + " "
					+ e);
		}
	}

	/**
	 * 日期转换成 Timestamp
	 * 
	 * @param yyyymmddhhmmss
	 * @return
	 */
	public static Timestamp parseTimestamp(String yyyymmddhhmmss) {
		try {
			return new Timestamp(parseDate(yyyymmddhhmmss).getTime());
		} catch (NullPointerException e) {
		}
		throw new IllegalArgumentException("日期为空或格式不正确");
	}

	/**
	 * 字符串转换日期 日期格式为yyyy.MM.dd HH:mm:ss
	 * 
	 * @param yyyymmddhhmmss
	 * @return
	 */
	public static Date parseDate(String yyyymmddhhmmss) {
		if ((yyyymmddhhmmss == null) || (yyyymmddhhmmss.length() == 0)) {
			return null;
		}

		if ((yyyymmddhhmmss.indexOf("-") < 0)
				&& (yyyymmddhhmmss.indexOf(".") < 0)
				&& (yyyymmddhhmmss.indexOf("/") < 0)) {
			throw new IllegalArgumentException("错误的日期格式，应该有\"-\"或\".\"作为分隔符");
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		try {
			yyyymmddhhmmss = yyyymmddhhmmss.replace('-', '.');

			yyyymmddhhmmss = yyyymmddhhmmss.replace('/', '.');

			if (yyyymmddhhmmss.indexOf(":") < 0) {
				yyyymmddhhmmss = yyyymmddhhmmss + " 00:00:00";
			}
			ParsePosition pos = new ParsePosition(0);
			Date tempDat = formatter.parse(yyyymmddhhmmss, pos);
			if (tempDat == null)
				throw new IllegalArgumentException("错误的日期");
			return tempDat;
		} catch (Exception e) {
			throw new IllegalArgumentException("错误的日期:" + yyyymmddhhmmss + " "
					+ e);
		}
	}

	/**
	 * Date转换成格式为yyyy.MM.dd HH:mm:ss日期字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		try {
			return formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("日期转换成字符串失败:" + e);
		}
	}

	/**
	 * 日期转换
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return
	 */
	public static String dateToString(Timestamp date, String format) {
		return dateToString((Date) date, format);
	}

	public static String dateToString(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("日期转换成字符串失败:" + e);
		}
	}

	/**
	 * 取服务器时间
	 * 
	 * @return
	 */
	public static Timestamp nowTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Date转换成Timestamp
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	// public static void uploadFile(String path, String filename, InputStream
	// is) {
	// uploadFile(Config.ftp_url, Config.ftp_port, Config.ftp_username,
	// Config.ftp_password, path, filename, is);
	// }
	//
	// public static void uploadFile(String path, String filename, File file) {
	// uploadFile(Config.ftp_url, Config.ftp_port, Config.ftp_username,
	// Config.ftp_password, path, filename, file);
	// }
	/**
	 * 文件流 读入文件
	 * 
	 * @param ins
	 *            输入流
	 * @param file
	 *            文件
	 */
	public static void inputstreamtofile(InputStream ins, File file) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			throw new BizRuntimeException(e);
		} finally {

			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
				}
			}
			if (ins != null)
				try {
					ins.close();
				} catch (IOException e) {
				}

		}
	}

	/**
	 * 数字补0
	 * 
	 * @param num
	 *            数字
	 * @param formatter
	 *            补0的格式
	 * @return
	 */
	public static String getFormatterNumber(Integer num, String formatter) {
		DecimalFormat df = new DecimalFormat(formatter);
		return df.format(num);
	}

	

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	public static StringBuffer readLogInfo(String filePath) {
		StringBuffer sb = new StringBuffer("");
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			URL url = new URL(filePath);
			InputStream ins = url.openStream();
			inputStreamReader = new InputStreamReader(ins, "UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String lineString = bufferedReader.readLine();
			while (lineString != null) {
				sb.append(lineString);
				lineString = bufferedReader.readLine();
				sb.append("<br/>");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb;
	}
	
	
	
	
	
}
