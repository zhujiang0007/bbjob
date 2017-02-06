package com.rundatop.core.dao;

import java.sql.Clob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class StringEx {
	public static final int indexOf(StringBuffer source, int start,
			String tobelocated) {
		return source.toString().indexOf(tobelocated, start);
	}

	public static String replace(String str, String oldStr, String newStr) {
		if ((null == str) || (null == oldStr) || (newStr == null))
			return str;

		if (str.length() > 1000000) {
			try {
				throw new Exception("警告：替换的字符串超过1M,可能造成JVM抖动!");
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		int len = oldStr.length();
		String post = str;
		StringBuffer resStr = new StringBuffer("");
		int idx;
		while ((idx = post.indexOf(oldStr)) != -1) {
			String pre = post.substring(0, idx);
			post = post.substring(idx + len);

			resStr.append(pre).append(newStr);
		}
		resStr.append(post);
		return resStr.toString();
	}

	public static String[] splitString(String str, String splitter) {
		if (str == null)
			return null;
		if ((null == splitter) || (splitter.length() == 0))
			return new String[] { str };
		int p = str.indexOf(splitter);
		int prev = 0;
		if (p < 0) {
			String[] ss = new String[1];
			ss[0] = str;
			return ss;
		}

		ArrayList found = new ArrayList();
		String s = null;

		while (p >= 0) {
			if (prev == p) {
				s = "";
			} else {
				s = str.substring(prev, p);
			}
			found.add(s);

			prev = p + 1;
			p = str.indexOf(splitter, prev);
		}
		if (prev < str.length()) {
			found.add(str.substring(prev, str.length()));
		}
		return (String[]) found.toArray(new String[1]);
	}

	public int occurence(String str, String s) {
		if ((null == str) || (null == s))
			return 0;
		int p = str.indexOf(s);
		int cnt = 0;
		if (p < 0)
			return cnt;
		int len = s.length();
		cnt++;
		while (p < str.length()) {
			p += len;
			p = str.indexOf(s, p);
			if (p < 0)
				return cnt;
			cnt++;
		}
		return cnt++;
	}

	public static String sNull(Object obj) {
		if (obj != null) {
			if ((obj instanceof Clob)) {
				try {
					return ((Clob) obj).getSubString(1L,
							(int) ((Clob) obj).length());
				} catch (Exception e) {
					e.printStackTrace();
					return "";
				}

			}

			return obj.toString();
		}

		return "";
	}

	public static String sNull(Object obj, boolean isconvert) {
		if (isconvert) {
			return obj == null ? "" : obj.toString();
		}

		return obj == null ? null : obj.toString();
	}

	public static String convGbk(String arg) throws Exception {
		if (arg == null)
			return arg;
		String s = null;

		byte[] b = arg.getBytes("ISO-8859-1");
		s = new String(b, "GBK");
		return s;
	}

	public static String reverseGbk(String arg) throws Exception {
		if (arg == null)
			return null;
		String s = null;

		byte[] b = arg.getBytes("GBK");
		s = new String(b, "ISO-8859-1");
		return s;
	}

	public static String convertCharSet(String arg, String source, String target)
			throws Exception {
		if ((null == arg) || (null == source) || (null == target)) {
			return arg;
		}
		String s = null;

		byte[] b = arg.getBytes(source);
		s = new String(b, target);
		return s;
	}

	public static String replMark(String s) {
		if (null == s)
			return null;
		return s.replace('<', '[').replace('>', ']');
	}

	public static String getParm(Hashtable inputParms, String key) {
		if ((null == key) || (null == inputParms))
			return null;
		Object o1 = inputParms.get(key);
		if ((o1 instanceof String)) {
			return (String) inputParms.get(key);
		}
		if ((o1 instanceof String[])) {
			String[] s1 = (String[]) o1;
			if (s1 != null)
				return s1[0];
		}
		return null;
	}

}