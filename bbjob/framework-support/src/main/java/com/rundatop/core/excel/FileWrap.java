package com.rundatop.core.excel;

import java.io.File;
/**
 * 
 *  FileWrap.java.
 * 
 * <table>
 * 	<tr>
 * 		<th>版本</th>
 * 		<th>日期</th>
 * 		<th>详细说明</th>
 * 	</tr>

 * </table>
 * 
 * @author DEV
 * @see 
 * @version 1.0
 */
public class FileWrap extends File {
	
	public FileWrap(File file, String name) {
		super(file, file.getName());
		this.file = file;
		this.name = name;
	}
	private static final long serialVersionUID = 3889712801125313241L;
	private String name;
	private File file;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
