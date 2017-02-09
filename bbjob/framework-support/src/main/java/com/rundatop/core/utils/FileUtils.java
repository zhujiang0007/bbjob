package com.rundatop.core.utils;

import java.io.File;

public class FileUtils {
	// 将指定目录下的文件名字变为大写或小写：u--变为大写，l-变为小写
		public static void changePathName(String path, String up) {
			// System.out.println("->->->changepathname Begin...");
			File d = new File(path); // 取得当前文件夹下所有文件和目录的列表
			File lists[] = d.listFiles();
			String pathss = new String("");
			// 对当前目录下面所有文件进行检索
			for (int i = 0; i < lists.length; i++) {
				if (lists[i].isFile()) {
					String filename = lists[i].getName();
					if (up.equals("u"))
						filename = upCase(filename);
					else
						filename = lowerCase(filename);

					String toName = new String(path + filename);
					File tempf = new File(toName);
					lists[i].renameTo(tempf);
					// System.out.println("new fullfilename is:" + toName);
				} else {
					pathss = path;
					// 进入下一级目录
					pathss = pathss + lists[i].getName() + "\\";
					// 递归遍历所有目录
					changePathName(pathss, up);
				}
			}
			// System.out.println("->->->changepathname End...");
		}

		public static String lowerCase(String filename) {
			// System.out.println("=>to lowerCase Begin...");
			String tempstr = new String("");
			char tempch = ' ';
			for (int i = 0; i < filename.length(); i++) {
				tempch = filename.charAt(i);
				if (64 < filename.charAt(i) && filename.charAt(i) < 91)// 是大写字母
					tempch += 32;
				tempstr += tempch;
			}
			// System.out.println("new filename is:" + tempstr);
			// System.out.println("=>tolowerCase End...");
			return tempstr;
		}

		public static String upCase(String filename) {
			// System.out.println("=>to upCase Begin...");
			String tempstr = new String("");
			char tempch = ' ';
			for (int i = 0; i < filename.length(); i++) {
				tempch = filename.charAt(i);
				if (97 < filename.charAt(i) && filename.charAt(i) < 122)// 是大写字母
					tempch -= 32;
				tempstr += tempch;
			}
			return tempstr;
		}
}
