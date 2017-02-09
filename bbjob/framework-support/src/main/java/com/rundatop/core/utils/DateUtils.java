package com.rundatop.core.utils;

import java.sql.Timestamp;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

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
	 * 当前日期字符串
	 */

	public static String stringOfCnDateTime() {
		return stringOfCnDateTime(new java.util.Date());
	}

	public static String stringOfCnDateTime(java.util.Date date) {
		Format formatter = new SimpleDateFormat("yyyy年M月d日 H时m分s秒");
		return formatter.format(date);
	}

	/**
	 * 当前日期字符串
	 */
	public static String stringOfCnDate() {
		return stringOfCnDate(new java.util.Date());
	}

	public static String stringOfCnDate(java.util.Date date) {
		Format formatter = new SimpleDateFormat("yyyy年M月d日");
		return formatter.format(date);
	}

	/**
	 * 当前日期字符串yyyy-MM-dd
	 */
	public static String stringOfDate() {
		return stringOfDate(new java.util.Date());
	}

	public static String stringOfDate(java.util.Date date) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	/**
	 * 当前日期字符串yyyy-M-d
	 */
	public static String stringOfDate2() {
		return stringOfDate2(new Date());
	}

	public static String stringOfDate2(Date date) {
		Format formatter = new SimpleDateFormat("yyyy-M-d");
		return formatter.format(date);
	}
	public static String stringOfDateTime(){
		return stringOfDateTime(new Date());
	}
	public static String stringOfDateTime(Date date){
		return dateToString(date,"yyyy-MM-dd hh:mm:ss");
	}
}
