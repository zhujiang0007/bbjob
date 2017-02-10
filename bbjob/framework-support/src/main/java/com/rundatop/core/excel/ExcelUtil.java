package com.rundatop.core.excel;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.rundatop.core.exception.BizRuntimeException;
import com.rundatop.core.utils.DateUtils;


/**
 *  ExcelUtil.java.
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
 
 * @see 
 * @version 1.0
 */
public class ExcelUtil {
	private final static String FOL_XSL = ".xls";
	private final static String FOL_XSLX = ".xlsx";

	public static List<Map<String, Object>> read(File file, File tpl) throws Exception {
		return read(file, tpl, null);
	}
	public static List<Map<String, Object>> read(File file, File tpl, ExcelReadedParam excelReadedParam) throws Exception {
		if (null == excelReadedParam) {
			excelReadedParam = ExcelReadedParam.defaultExcelReadedParam();
		}
		Workbook book = getWorkbook(file);
		Sheet sheet = book.getSheetAt(excelReadedParam.getSheetIndex());

		Workbook bookTpl = getWorkbook(tpl);
		Sheet sheetTpl = bookTpl.getSheetAt(excelReadedParam.getSheetIndex());
		return readSheet(sheet, sheetTpl, excelReadedParam);
	}
	public static List<Map<String, Object>> read(MultipartFile fileObject, File tpl) throws Exception {
		return read(fileObject, tpl, null);
	}
	public static List<Map<String, Object>> read(MultipartFile fileObject, File tpl, ExcelReadedParam excelReadedParam) throws Exception {
		if (null == excelReadedParam) {
			excelReadedParam = ExcelReadedParam.defaultExcelReadedParam();
		}
		Workbook book = getWorkbook(fileObject);
		Sheet sheet = book.getSheetAt(excelReadedParam.getSheetIndex());

		Workbook bookTpl = getWorkbook(tpl);
		Sheet sheetTpl = bookTpl.getSheetAt(excelReadedParam.getSheetIndex());
		return readSheet(sheet, sheetTpl, excelReadedParam);
	}


	public static Workbook getWorkbook(File file) throws Exception {
		InputStream is = null;
		if (file instanceof FileWrap) {
			is = new FileInputStream(((FileWrap)file).getFile());
		} else {
			is = new FileInputStream(file);
		}
		try {
			if (file.getName().endsWith(FOL_XSL)) {
				return new HSSFWorkbook(is);
			}
			if (file.getName().endsWith(FOL_XSLX)) {
				return new XSSFWorkbook(is);
			}
			throw new BizRuntimeException("文件格式错误，请导入excel文件");
		} catch (Exception e) {
			throw e;
		} finally {
			is.close();
		}
	}
	public static Workbook getWorkbook(MultipartFile fileObject) throws Exception {
		InputStream is = null;
		try {
			is = fileObject.getInputStream();
			if (fileObject.getOriginalFilename().endsWith(FOL_XSL)) {
				return new HSSFWorkbook(is);
			}
			if (fileObject.getOriginalFilename().endsWith(FOL_XSLX)) {
				return new XSSFWorkbook(is);
			}
			throw new BizRuntimeException("请上传excel格式的文件");
		} catch (Exception e) {
			throw e;
		} finally {
			is.close();
		}
	}
	public static boolean validateTitleRow(Sheet sheet, Sheet sheetTpl) throws Exception {
		int titleRowsCount = sheetTpl.getLastRowNum();
		if (0 == titleRowsCount) {
			throw new Exception("Language.EXCEPTION_NOTITLEROW");
		}
		int titleColsCount = -1;
		Row row = null;
		Row rowTpl = null;
		Cell cell = null;
		Cell cellTpl = null;
		for (int i = 1; i < titleRowsCount + 1; i++) {
			rowTpl = sheetTpl.getRow(i);
			titleColsCount = rowTpl.getLastCellNum();
			row = sheet.getRow(i-1);
			for (int j = 0; j < titleColsCount; j++) {
				cellTpl = rowTpl.getCell(j);
				cell = row.getCell(j);
				if (null == cellTpl.getStringCellValue() || "".equals(cellTpl.getStringCellValue())) {
					continue;
				}
				if (cell.getStringCellValue().equals(cellTpl.getStringCellValue())) {
					continue;
				}
				throw new Exception("(" + i + ":" + j + ")");
			}
		}
		return true;
	}
	public static List<Map<String, Object>> readSheet(Sheet sheet, Sheet sheetTpl, ExcelReadedParam excelReadedParam) throws Exception {
		if (!validateTitleRow(sheet, sheetTpl)) {
			return null;
		}
		Row filedRow = sheetTpl.getRow(0);
		int dataStartRowIndex = excelReadedParam.getDataStartRowIndex();
		if (-1 == dataStartRowIndex) {//根据模板计算数据行开始行号
			dataStartRowIndex = sheetTpl.getLastRowNum();
		}
		int rowsCount = sheet.getLastRowNum() + 1;
		if (dataStartRowIndex == rowsCount) {
			throw new Exception("Language.EXCEPTION_NODATA");
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(rowsCount - dataStartRowIndex);
		for (int i = dataStartRowIndex; i < rowsCount; i++) {
			list.add(readRow(sheet.getRow(i), filedRow, excelReadedParam.isDistinguishAccordingToType()));
		}
		return list;
	}
	public static Map<String, Object> readRow(Row row, Row rowTpl, boolean distinguishAccordingToType) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < rowTpl.getLastCellNum(); i++) {
			map.put(readCell(rowTpl.getCell(i), false).toString(), readCell(row.getCell(i), distinguishAccordingToType));
		}
		return map;
	}

	public static Object readCell(Cell cell, boolean distinguishAccordingToType) {
		if (null == cell) {
			return null;
		}
		if (distinguishAccordingToType) {
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				return subZeroAndDot(cell.getNumericCellValue()+"");
			}
			return cell.getStringCellValue();
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.getStringCellValue();
	}

	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}

	
	//未完
	public static List<Object[]> readSheetIgnoreKey(Sheet sheet, int startRowIndex, boolean distinguishAccordingToType) {
		return null;
	}
	//未完
	public static Object[] readRowIgnoreKey(Row row, boolean distinguishAccordingToType) {
		return null;
	}



	public final static String DATE_OUTPUT_PATTERNS = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";  

	public static String getCellValue(Cell cell) {  
		if(null == cell ) return "";
		String ret;  
		switch (cell.getCellType()) {  
		case Cell.CELL_TYPE_BLANK:  
			ret = "";  
			break;  
		case Cell.CELL_TYPE_BOOLEAN:  
			ret = String.valueOf(cell.getBooleanCellValue());  
			break;  
		case Cell.CELL_TYPE_ERROR:  
			ret = null;  
			break;  
		case Cell.CELL_TYPE_NUMERIC:  
			if (DateUtil.isCellDateFormatted(cell)) {   
				Date theDate = cell.getDateCellValue();  
				ret = DateUtils.stringOfDateTime(theDate);  
			} else {   
				ret = NumberToTextConverter.toText(cell.getNumericCellValue());  
			}  
			break;  
		case Cell.CELL_TYPE_STRING:  
			ret = cell.getRichStringCellValue().getString();  
			break;  
		default:  
			ret = null;  
		}  

		return ret; //有必要自行trim  
	}  

}
