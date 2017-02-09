package com.rundatop.core.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;

public class OsUtils {

	/**
	 * 取得当前操作系统名称
	 * 
	 * @return
	 */
	public static String getOsName() {
		String osname = "";
		osname = System.getProperty("os.name");
		return osname;
	}
	// 获得本机ip
		public static String getip() {
			String lname = null;
			String lip = null;
			try {
				InetAddress add = InetAddress.getLocalHost();
				lip = add.getHostAddress();
				lname = add.getHostName();
				// System.out.println(add.getHostName()+": "+add.getHostAddress());
				return lip;
			} catch (Exception e) {
				// System.out.print(e.getMessage());
				return lip;
			}
		}

		public static String getMacAddressIP(String remotePcIP) {
			String str = "";
			String macAddress = "";
			try {
				Process pp = Runtime.getRuntime().exec("nbtstat -A " + remotePcIP);
				InputStreamReader ir = new InputStreamReader(pp.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);
				for (int i = 1; i < 100; i++) {
					str = input.readLine();
					if (str != null) {
						if (str.indexOf("MAC Address") > 1) {
							macAddress = str.substring(
									str.indexOf("MAC Address") + 14, str.length());
							break;
						}
					}
				}
			} catch (IOException ex) {
			}
			return macAddress;
		}

		/**
		 * 通过机器名获取网卡地址
		 * @param remotePcIP
		 * @return
		 */
		public static String getMacAddressName(String remotePcIP) {
			String str = "";
			String macAddress = "";
			try {
				Process pp = Runtime.getRuntime().exec("nbtstat -a " + remotePcIP);
				InputStreamReader ir = new InputStreamReader(pp.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);
				for (int i = 1; i < 100; i++) {
					str = input.readLine();
					if (str != null) {
						if (str.indexOf("MAC Address") > 1) {
							macAddress = str.substring(
									str.indexOf("MAC Address") + 14, str.length());
							break;
						}
					}
				}
			} catch (IOException ex) {
			}
			return macAddress;
		}

}
