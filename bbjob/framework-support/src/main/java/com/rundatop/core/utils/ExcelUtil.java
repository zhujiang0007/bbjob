package com.rundatop.core.utils;


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

	/**
	 * keep rowAccessWindowSize rows in memory, exceeding rows will be flushed to disk
	 * @param file
	 * @param rowAccessWindowSize
	 * @return
	 * @throws Exception
	public static Workbook getWorkbook(File file, int rowAccessWindowSize) throws Exception {
		if (file.getName().endsWith(".xsl")) {//xls暂时没有找到读row的限制
			return new HSSFWorkbook(new FileInputStream(file));
		}
		if (file.getName().endsWith(".xslx")) {
			return new SXSSFWorkbook(new XSSFWorkbook(new FileInputStream(file)), rowAccessWindowSize).getXSSFWorkbook();
		}
		throw new Exception("无效的Excel文件");
	}
	 */
	//未完
	public static List<Object[]> readSheetIgnoreKey(Sheet sheet, int startRowIndex, boolean distinguishAccordingToType) {
		return null;
	}
	//未完
	public static Object[] readRowIgnoreKey(Row row, boolean distinguishAccordingToType) {
		return null;
	}









	/*	public static int readBook(File file, int sheetIndex) {
		FileInputStream fs = new FileInputStream(file);
		SXSSFWorkbook xslx = new SXSSFWorkbook(new XSSFWorkbook(fs), 100);

		XSSFSheet sh = xslx.getXSSFWorkbook().getSheetAt(0);
		int count = sh.getLastRowNum() + 1;
		int colWidth = 0;
		Cell cell = null;
		Row row = null;
		for (int i = 0; i < count; i++) {
			row = sh.getRow(i);
			if (null == row) {
				return;
			}
			colWidth = sh.getColumnWidth(i);
			colWidth = row.getLastCellNum();
			for (int j = 0; j < colWidth; j++) {
				cell = row.getCell(j);
				System.out.println(cell.getStringCellValue());
			}
		}
		fs.close();
	}
	public static int writeXSLX(File file) {
        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        for(int rownum = 0; rownum < 1000; rownum++){
            Row row = sh.createRow(rownum);
            for(int cellnum = 0; cellnum < 10; cellnum++){
                Cell cell = row.createCell(cellnum);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(address);
            }

        }

//        // Rows with rownum < 900 are flushed and not accessible
//        for(int rownum = 0; rownum < 900; rownum++){
////          Assert.assertNull(sh.getRow(rownum));
//        	sh.getRow(rownum);
//        }
//
//        // ther last 100 rows are still in memory
//        for(int rownum = 900; rownum < 1000; rownum++){
////            Assert.assertNotNull(sh.getRow(rownum));
//        	sh.getRow(rownum);
//        }
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();

        // dispose of temporary files backing this workbook on disk
        wb.dispose();
    }*/

	/*private static void function1() throws Exception {

		Workbook[] wbs = new Workbook[] { new HSSFWorkbook(),
				new XSSFWorkbook() };
		for (int i = 0; i < wbs.length; i++) {
			Workbook wb = wbs[i];
			CreationHelper createHelper = wb.getCreationHelper();

			// create a new sheet
			Sheet s = wb.createSheet();
			// declare a row object reference
			Row r = null;
			// declare a cell object reference
			Cell c = null;
			// create 2 cell styles
			CellStyle cs = wb.createCellStyle();
			CellStyle cs2 = wb.createCellStyle();
			DataFormat df = wb.createDataFormat();

			// create 2 fonts objects
			Font f = wb.createFont();
			Font f2 = wb.createFont();

			// Set font 1 to 12 point type, blue and bold
			f.setFontHeightInPoints((short) 12);
			f.setColor(IndexedColors.RED.getIndex());
			f.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// Set font 2 to 10 point type, red and bold
			f2.setFontHeightInPoints((short) 10);
			f2.setColor(IndexedColors.RED.getIndex());
			f2.setBoldweight(Font.BOLDWEIGHT_BOLD);

			// Set cell style and formatting
			cs.setFont(f);
			cs.setDataFormat(df.getFormat("#,##0.0"));

			// Set the other cell style and formatting
			cs2.setBorderBottom(cs2.BORDER_THIN);
			cs2.setDataFormat(df.getFormat("text"));
			cs2.setFont(f2);

			// Define a few rows
			for (int rownum = 0; rownum < 30; rownum++) {
				r = s.createRow(rownum);
				for (int cellnum = 0; cellnum < 10; cellnum += 2) {
					c = r.createCell(cellnum);
					Cell c2 = r.createCell(cellnum + 1);

					c.setCellValue((double) rownum + (cellnum / 10));
					c2.setCellValue(createHelper.createRichTextString("Hello! "
							+ cellnum));
				}
			}

			// Save
			String filename = "d:/temp/workbook.xls";
			if (wb instanceof XSSFWorkbook) {
				filename = filename + "x";
			}

			FileOutputStream out = new FileOutputStream(filename);
			wb.write(out);
			out.close();

		}
	}*/
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
