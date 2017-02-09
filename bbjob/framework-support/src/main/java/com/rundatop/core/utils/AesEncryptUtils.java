package com.rundatop.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import com.rundatop.core.exception.BizRuntimeException;

public class AesEncryptUtils {
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
}
