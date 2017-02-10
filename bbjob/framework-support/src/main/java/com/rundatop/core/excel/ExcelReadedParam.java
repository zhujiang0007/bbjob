package com.rundatop.core.excel;

/**
 * 
 *  ExcelReadedParam.java.
 * 
 * <table>
 * 	<tr>
 * 		<th>版本</th>
 * 		<th>日期</th>
 * 		<th>详细说明</th>
 * 	</tr>
 * 	<tr>
 * 		
 * 	</tr>
 * </table>
 * 
 * @author 
 * @see 
 * @version 1.0
 */
public class ExcelReadedParam {
	private int sheetIndex = 0;
//	private String cellPosition = null;
	private int dataStartRowIndex = -1;
//	private int dataEndRowIndex = -1;
	private boolean distinguishAccordingToType;
	public static ExcelReadedParam defaultExcelReadedParam() {
		return new ExcelReadedParam(0, -1, false);
	}
	public ExcelReadedParam() {
		super();
	}
	public ExcelReadedParam(int sheetIndex, int dataStartRowIndex, boolean distinguishAccordingToType) {
		super();
		this.sheetIndex = sheetIndex;
		this.dataStartRowIndex = dataStartRowIndex;
		this.distinguishAccordingToType = distinguishAccordingToType;
	}
	public int getSheetIndex() {
		return sheetIndex;
	}
	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}
	public int getDataStartRowIndex() {
		return dataStartRowIndex;
	}
	public void setDataStartRowIndex(int dataStartRowIndex) {
		this.dataStartRowIndex = dataStartRowIndex;
	}
	public boolean isDistinguishAccordingToType() {
		return distinguishAccordingToType;
	}
	public void setDistinguishAccordingToType(boolean distinguishAccordingToType) {
		this.distinguishAccordingToType = distinguishAccordingToType;
	}
}
